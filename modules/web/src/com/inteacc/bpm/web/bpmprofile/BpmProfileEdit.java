package com.inteacc.bpm.web.bpmprofile;

import com.haulmont.cuba.core.global.Metadata;
import com.haulmont.cuba.gui.components.AbstractEditor;
import com.haulmont.cuba.gui.components.Component;
import com.haulmont.cuba.gui.components.GroupBoxLayout;
import com.haulmont.cuba.gui.components.Table;
import com.haulmont.cuba.gui.data.CollectionDatasource;
import com.haulmont.cuba.gui.data.Datasource;
import com.inteacc.bpm.entity.*;

import javax.inject.Inject;
import java.util.UUID;

public class BpmProfileEdit extends AbstractEditor<BpmProfile> {

    @Inject
    private Datasource<BpmProfile> bpmProfileDs;
    @Inject
    private CollectionDatasource<BpmProfileRole, UUID> bpmProfileRoleDs;
    @Inject
    private CollectionDatasource<BpmProfileTask, UUID> bpmProfileTaskDs;
    @Inject
    private CollectionDatasource<BpmProfileUser, UUID> bpmProfileUserDs;
    @Inject
    private GroupBoxLayout bpmProfileUserBox;
    @Inject
    private GroupBoxLayout bpmProfileRoleBox;
    @Inject
    private Table<BpmProfileRole> bpmProfileRoleTable;
    @Inject
    private Table<BpmProfileUser> bpmProfileUserTable;
    @Inject
    private Metadata metadata;

    @Override
    protected void postInit() {
        bpmProfileDs.addItemPropertyChangeListener(e -> {
            if("delegationTargetGroup".equals(e.getProperty())){
                showHideComponents();
            }else if("adHocUserSelection".equals(e.getProperty())){
                if(getItem().getAdHocUserSelection()==true){
                    bpmProfileUserBox.setVisible(false);
                    bpmProfileRoleBox.setVisible(false);
                }else{
                    showHideComponents();
                }
            }else if ("procModel".equals(e.getProperty())){
                //procRolesDs.refresh();

            }
        });
    }


    @Override
    protected void initNewItem(BpmProfile item) {
        item.setDelegationTargetGroup(DelegationTargetGroup.ROLES);
    }

    @Override
    public void ready() {

        if(getItem().getDelegationTargetGroup()!=null){
            showHideComponents();

        }

    }


    private void showHideComponents(){
        if(getItem().getDelegationTargetGroup().equals(DelegationTargetGroup.ROLES)){
            bpmProfileUserBox.setVisible(false);
            bpmProfileRoleBox.setVisible(true);
        }else{
            bpmProfileRoleBox.setVisible(false);
            bpmProfileUserBox.setVisible(true);
        }
    }

    public void onAddRole(Component source) {

        if(okToCreate()) {
            BpmProfileRole authorityRole =  metadata.create(BpmProfileRole.class);
            authorityRole.setBpmProfile(getItem());

            bpmProfileRoleDs.addItem(authorityRole);
            bpmProfileRoleTable.scrollTo(authorityRole);
            bpmProfileRoleTable.setSelected(authorityRole);
            //bpmProfileRoleTable.requestFocus(authorityRole, "userRole");
            bpmProfileRoleTable.requestFocus(authorityRole, "role");
        }else{
            showNotification("Please update the required data before this action", NotificationType.HUMANIZED);
        }
    }

    public void onAddUser(Component source) {
        if(okToCreate()) {
            BpmProfileUser authorityRole =  metadata.create(BpmProfileUser.class);
            authorityRole.setBpmProfile(getItem());

            bpmProfileUserDs.addItem(authorityRole);
            bpmProfileUserTable.scrollTo(authorityRole);
            bpmProfileUserTable.setSelected(authorityRole);
            bpmProfileUserTable.requestFocus(authorityRole, "user");
        }else{
            showNotification("Please update the required data before this action", NotificationType.HUMANIZED);
        }
    }


    private boolean okToCreate(){
        boolean ok = true;
        if(getItem().getProcDefinition()==null){

            ok=false;
            showNotification("Please select the Process Model first", NotificationType.HUMANIZED);
        }

        return ok;
    }
}