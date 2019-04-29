package com.inteacc.bpm.web.bpmprofile;

import com.google.common.base.Strings;
import com.haulmont.bpm.entity.ProcDefinition;
import com.haulmont.bpm.service.ProcessRepositoryService;
import com.haulmont.chile.core.model.MetaClass;
import com.haulmont.chile.core.model.MetaProperty;
import com.haulmont.cuba.core.global.Metadata;
import com.haulmont.cuba.gui.components.*;
import com.haulmont.cuba.gui.data.CollectionDatasource;
import com.haulmont.cuba.gui.data.Datasource;
import com.haulmont.cuba.gui.data.impl.CollectionPropertyDatasourceImpl;
import com.haulmont.cuba.gui.xml.layout.ComponentsFactory;
import com.haulmont.cuba.security.entity.Role;
import com.haulmont.cuba.security.entity.User;
import com.inteacc.bpm.entity.*;
import com.inteacc.bpm.service.BpmnXmlParsingService;

import javax.inject.Inject;
import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

public class BpmProfileEdit extends AbstractEditor<BpmProfile> {

    @Inject
    protected ComponentsFactory componentsFactory;

    @Inject
    private Datasource<BpmProfile> bpmProfileDs;

    @Inject
    protected CollectionDatasource<BpmProfileActor, UUID> bpmProfileActorsDs;

    @Inject
    protected Table<BpmProfileActor> bpmProfileActorsTable;

    @Inject
    protected CollectionDatasource<User, UUID> usersDs;

    @Inject
    protected CollectionDatasource<ExtRole, UUID> extRolesDs;

    @Inject
    protected FieldGroup fieldGroup;

    @Inject
    private Metadata metadata;

    @Inject
    protected LookupField entityNameLookup;

    @Inject
    protected LookupField limitAmountFieldNameLookup;

    @Inject
    protected CollectionDatasource<BpmProfileNotification, UUID> bpmProfileNotificationsDs;

    @Inject
    protected Table<BpmProfileNotification> bpmProfileNotificationsTable;

    @Inject
    protected BpmnXmlParsingService bpmnXmlParsingService;

    @Inject
    protected ProcessRepositoryService processRepositoryService;

    protected List<String> userTaskIds = new ArrayList<>();

    @Override
    public void ready() {
        //todo add commit validation
        initBpmProfileActorsTable();
        initEntityNameLookup();
        initLimitAmountFieldNameLookup();
        initBpmProfileNotificationsTable();
    }

    private void initBpmProfileActorsTable() {
        bpmProfileActorsTable.addGeneratedColumn("secRole", entity -> {
            if (entity.getDelegationTargetGroup() == DelegationTargetGroup.ROLES) {
                LookupField lookupField = componentsFactory.createComponent(LookupField.class);
                lookupField.setWidth("100%");
                lookupField.setOptionsDatasource(extRolesDs);
                lookupField.setValue(entity.getSecRole());
                lookupField.addValueChangeListener(e -> {
                    entity.setSecRole((Role) e.getValue());
                });
                return lookupField;
            } else {
                return null;
            }
        });

        bpmProfileActorsTable.addGeneratedColumn("user", entity -> {
            if (entity.getDelegationTargetGroup() == DelegationTargetGroup.USERS) {
                LookupField lookupField = componentsFactory.createComponent(LookupField.class);
                lookupField.setWidth("100%");
                lookupField.setOptionsDatasource(usersDs);
                lookupField.setValue(entity.getUser());
                lookupField.addValueChangeListener(e -> {
                    entity.setUser((User) e.getValue());
                });
                return lookupField;
            } else {
                return null;
            }
        });

        bpmProfileActorsDs.addItemPropertyChangeListener(e -> {
            if ("delegationTargetGroup".equals(e.getProperty())) {
                bpmProfileActorsDs.refresh();
            }
        });
    }

    private void initBpmProfileNotificationsTable() {
        reloadUserTaskIds(getItem().getProcDefinition());
        bpmProfileNotificationsTable.addGeneratedColumn("taskId", entity -> {
            LookupField lookupField = componentsFactory.createComponent(LookupField.class);
            lookupField.setWidth("100%");
            lookupField.setOptionsList(userTaskIds);
            lookupField.setValue(entity.getTaskId());
            lookupField.addValueChangeListener(e -> {
                entity.setTaskId((String) e.getValue());
            });
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

        bpmProfileDs.addItemPropertyChangeListener(e -> {
            switch (e.getProperty()) {
                case "entityName":
                    //refresh properties list when another entity is selected
                    initLimitAmountFieldNameLookup();
                    break;
                case "procDefinition":
                    //clean notifications when the new ProcDefinition is selected
                    reloadUserTaskIds((ProcDefinition) e.getValue());
                    Collection<UUID> itemIds = bpmProfileNotificationsDs.getItemIds();
                    for (UUID itemId : itemIds) {
                        BpmProfileNotification item = bpmProfileNotificationsDs.getItem(itemId);
                        bpmProfileNotificationsDs.removeItem(item);
                    }
                    break;
            }
            if ("entityName".equals(e.getProperty())) {
                //refresh properties list when another entity is selected
                initLimitAmountFieldNameLookup();
            }
        });
    }

    private void reloadUserTaskIds(ProcDefinition procDefinition) {
        if (procDefinition == null) {
            userTaskIds = new ArrayList<>();
        } else {
            userTaskIds = bpmnXmlParsingService.getUserTaskIds(processRepositoryService.getProcessDefinitionXml(procDefinition.getActId()));
        }
    }

    private void initLimitAmountFieldNameLookup() {
        String entityName = getItem().getEntityName();
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

    public void onAddProfileActor(Component source) {
        if (okToCreate()) {
            BpmProfileActor bpmProfileActor = metadata.create(BpmProfileActor.class);
            bpmProfileActor.setBpmProfile(getItem());
            bpmProfileActorsDs.addItem(bpmProfileActor);
            bpmProfileActorsTable.scrollTo(bpmProfileActor);
            bpmProfileActorsTable.setSelected(bpmProfileActor);
        } else {
            showNotification("Please update the required data before this action", NotificationType.HUMANIZED);
        }
    }

    public void onAddProfileNotification() {
        BpmProfileNotification bpmProfileNotification = metadata.create(BpmProfileNotification.class);
        bpmProfileNotification.setBpmProfile(getItem());
        bpmProfileNotificationsDs.addItem(bpmProfileNotification);
    }

    private boolean okToCreate() {
        boolean ok = true;
        if (getItem().getProcDefinition() == null) {
            ok = false;
            showNotification("Please select the ProcessDefinition first", NotificationType.HUMANIZED);
        }
        return ok;
    }
}