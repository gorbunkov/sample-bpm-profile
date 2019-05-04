package com.inteacc.bpm.web.bpmprofile;

import com.google.common.base.Strings;
import com.haulmont.bpm.entity.ProcDefinition;
import com.haulmont.bpm.entity.ProcRole;
import com.haulmont.bpm.service.ProcessRepositoryService;
import com.haulmont.chile.core.model.MetaClass;
import com.haulmont.chile.core.model.MetaProperty;
import com.haulmont.cuba.core.global.Metadata;
import com.haulmont.cuba.gui.Notifications;
import com.haulmont.cuba.gui.UiComponents;
import com.haulmont.cuba.gui.components.Action;
import com.haulmont.cuba.gui.components.LookupField;
import com.haulmont.cuba.gui.components.Table;
import com.haulmont.cuba.gui.components.data.options.ContainerOptions;
import com.haulmont.cuba.gui.model.*;
import com.haulmont.cuba.gui.screen.*;
import com.haulmont.cuba.security.entity.Role;
import com.haulmont.cuba.security.entity.User;
import com.inteacc.bpm.entity.BpmProfile;
import com.inteacc.bpm.entity.BpmProfileActor;
import com.inteacc.bpm.entity.BpmProfileNotification;
import com.inteacc.bpm.entity.DelegationTargetGroup;
import com.inteacc.bpm.service.BpmnXmlParsingService;

import javax.inject.Inject;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@UiController("bp_BpmProfile.edit")
@UiDescriptor("bpm-profile-edit.xml")
@EditedEntityContainer("bpmProfileDc")
public class BpmProfileEdit extends StandardEditor<BpmProfile> {

    @Inject
    protected UiComponents uiComponents;

    @Inject
    protected Table<BpmProfileActor> bpmProfileActorsTable;

    @Inject
    protected CollectionContainer<Role> rolesDc;

    @Inject
    protected CollectionContainer<User> usersDc;
    @Inject
    protected Notifications notifications;

    @Inject
    private Metadata metadata;

    @Inject
    protected LookupField<String> entityNameLookup;

    @Inject
    protected LookupField<String> limitAmountFieldNameLookup;

    @Inject
    protected Table<BpmProfileNotification> bpmProfileNotificationsTable;

    @Inject
    protected BpmnXmlParsingService bpmnXmlParsingService;

    @Inject
    protected ProcessRepositoryService processRepositoryService;

    @Inject
    protected CollectionPropertyContainer<BpmProfileActor> bpmProfileActorsDc;

    @Inject
    protected CollectionPropertyContainer<BpmProfileNotification> bpmProfileNotificationsDc;

    @Inject
    protected CollectionLoader<ProcRole> procRolesDl;

    @Inject
    protected DataContext dataContext;

    @Inject
    protected InstanceContainer<BpmProfile> bpmProfileDc;

    protected List<String> userTaskIds = new ArrayList<>();

    @Subscribe
    protected void onBeforeShow(BeforeShowEvent event) {
        procRolesDl.setParameter("procDefinition", getEditedEntity().getProcDefinition());
        getScreenData().loadAll();
        initBpmProfileActorsTable();
        initEntityNameLookup();
        initLimitAmountFieldNameLookup();
        initBpmProfileNotificationsTable();
    }

    private void initBpmProfileActorsTable() {
        bpmProfileActorsTable.addGeneratedColumn("secRole", entity -> {
            if (entity.getDelegationTargetGroup() == DelegationTargetGroup.ROLES) {
                LookupField<Role> lookupField = uiComponents.create(LookupField.of(Role.class));
                lookupField.setWidth("100%");
                lookupField.setOptions(new ContainerOptions<>(rolesDc));
                lookupField.setValue(entity.getSecRole());
                lookupField.addValueChangeListener(e -> entity.setSecRole(e.getValue()));
                return lookupField;
            } else {
                return null;
            }
        });

        bpmProfileActorsTable.addGeneratedColumn("user", entity -> {
            if (entity.getDelegationTargetGroup() == DelegationTargetGroup.USERS) {
                LookupField<User> lookupField = uiComponents.create(LookupField.of(User.class));
                lookupField.setWidth("100%");
                lookupField.setOptions(new ContainerOptions<>(usersDc));
                lookupField.setValue(entity.getUser());
                lookupField.addValueChangeListener(e -> entity.setUser(e.getValue()));
                return lookupField;
            } else {
                return null;
            }
        });
    }

    private void initBpmProfileNotificationsTable() {
        reloadUserTaskIds(bpmProfileDc.getItem().getProcDefinition());
        bpmProfileNotificationsTable.addGeneratedColumn("taskId", entity -> {
            LookupField<String> lookupField = uiComponents.create(LookupField.TYPE_STRING);
            lookupField.setWidth("100%");
            lookupField.setOptionsList(userTaskIds);
            lookupField.setValue(entity.getTaskId());
            lookupField.addValueChangeListener(e -> entity.setTaskId(e.getValue()));
            return lookupField;
        });
    }

    private void initEntityNameLookup() {
        List<String> entityNameList = metadata.getTools().getAllPersistentMetaClasses().stream()
                .filter(metaClass -> !metadata.getTools().isSystemLevel(metaClass))
                .map(MetaClass::getName)
                .sorted()
                .collect(Collectors.toList());
        entityNameLookup.setOptionsList(entityNameList);
    }

    @Subscribe(id = "bpmProfileDc", target = Target.DATA_CONTAINER)
    protected void onBpmProfileDcItemPropertyChange(InstanceContainer.ItemPropertyChangeEvent<BpmProfile> event) {
        switch (event.getProperty()) {
            case "entityName":
                //refresh properties list when another entity is selected
                initLimitAmountFieldNameLookup();
                break;
            case "procDefinition":
                //clean notifications when the new ProcDefinition is selected
                reloadUserTaskIds((ProcDefinition) event.getValue());
                reloadProcRolesDc((ProcDefinition) event.getValue());

                //todo
//                bpmProfileActorsDc.getMutableItems().clear();
//                bpmProfileNotificationsDc.getMutableItems().clear();
                break;
        }
    }

    @Subscribe(id = "bpmProfileActorsDc", target = Target.DATA_CONTAINER)
    protected void onBpmProfileActorsDcItemPropertyChange(InstanceContainer.ItemPropertyChangeEvent<BpmProfileActor> event) {
        if ("delegationTargetGroup".equals(event.getProperty())) {
            bpmProfileActorsTable.repaint();
        }
    }

    protected void reloadProcRolesDc(ProcDefinition procDefinition) {
        procRolesDl.setParameter("procDefinition", procDefinition);
        procRolesDl.load();
    }

    private void reloadUserTaskIds(ProcDefinition procDefinition) {
        if (procDefinition == null) {
            userTaskIds = new ArrayList<>();
        } else {
            userTaskIds = bpmnXmlParsingService.getUserTaskIds(processRepositoryService.getProcessDefinitionXml(procDefinition.getActId()));
        }
    }

    private void initLimitAmountFieldNameLookup() {
        String entityName = bpmProfileDc.getItem().getEntityName();
        if (!Strings.isNullOrEmpty(entityName)) {
            MetaClass metaClass = metadata.getClassNN(entityName);
            List<String> bigDecimalPropertyNames = metaClass.getProperties().stream()
                    .filter(metaProperty -> BigDecimal.class.equals(metaProperty.getJavaType()))
                    .map(MetaProperty::getName)
                    .sorted()
                    .collect(Collectors.toList());
            limitAmountFieldNameLookup.setOptionsList(bigDecimalPropertyNames);
        } else {
            limitAmountFieldNameLookup.setOptionsList(new ArrayList<>());
        }
    }

    @Subscribe("bpmProfileActorsTable.create")
    protected void onBpmProfileActorsTableCreate(Action.ActionPerformedEvent event) {
        if (bpmProfileDc.getItem().getProcDefinition() != null) {
            BpmProfileActor bpmProfileActor = dataContext.merge(metadata.create(BpmProfileActor.class));
            bpmProfileActor.setBpmProfile(bpmProfileDc.getItem());
            bpmProfileActorsDc.getMutableItems().add(bpmProfileActor);
            bpmProfileActorsTable.scrollTo(bpmProfileActor);
            bpmProfileActorsTable.setSelected(bpmProfileActor);
        } else {
            notifications.create(Notifications.NotificationType.WARNING)
                    .withCaption("Please select the ProcessDefinition first")
                    .show();
        }
    }

    @Subscribe("bpmProfileNotificationsTable.create")
    protected void onBpmProfileNotificationsTableCreate(Action.ActionPerformedEvent event) {
        if (bpmProfileDc.getItem().getProcDefinition() != null) {
            BpmProfileNotification bpmProfileNotification = dataContext.merge(metadata.create(BpmProfileNotification.class));
            bpmProfileNotification.setBpmProfile(bpmProfileDc.getItem());
            bpmProfileNotificationsDc.getMutableItems().add(bpmProfileNotification);
        } else {
            notifications.create(Notifications.NotificationType.WARNING)
                    .withCaption("Please select the ProcessDefinition first")
                    .show();
        }
    }


}