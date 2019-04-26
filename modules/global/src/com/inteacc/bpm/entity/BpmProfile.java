package com.inteacc.bpm.entity;

import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.UUID;
import javax.persistence.Column;
import com.haulmont.cuba.core.entity.StandardEntity;
import com.haulmont.chile.core.annotations.NamePattern;
import com.haulmont.bpm.entity.ProcDefinition;
import com.haulmont.cuba.core.entity.annotation.Lookup;
import com.haulmont.cuba.core.entity.annotation.LookupType;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import com.haulmont.chile.core.annotations.Composition;
import com.haulmont.cuba.core.entity.annotation.OnDelete;
import com.haulmont.cuba.core.global.DeletePolicy;
import java.util.List;
import javax.persistence.OneToMany;

@NamePattern("%s|description")
@Table(name = "BP_BPM_PROFILE")
@Entity(name = "bp$BpmProfile")
public class BpmProfile extends StandardEntity {
    private static final long serialVersionUID = -926644504110944239L;

    @Column(name = "DESCRIPTION")
    protected String description;

    @Lookup(type = LookupType.DROPDOWN)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "PROC_MODEL_ID")
    protected ExtProcModel procModel;

    @Column(name = "AD_HOC_USER_SELECTION")
    protected Boolean adHocUserSelection;

    @Column(name = "ENTITY_NAME")
    protected String entityName;

    @Lookup(type = LookupType.DROPDOWN, actions = {"lookup"})
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "PROC_DEFINITION_ID")
    protected ProcDefinition procDefinition;

    @Column(name = "DELEGATION_TARGET_GROUP")
    protected Integer delegationTargetGroup;

    @Composition
    @OnDelete(DeletePolicy.CASCADE)
    @OneToMany(mappedBy = "bpmProfile")
    protected List<BpmProfileRole> bpmProfileRole;

    @Composition
    @OnDelete(DeletePolicy.CASCADE)
    @OneToMany(mappedBy = "bpmProfile")
    protected List<BpmProfileTask> bpmProfileTask;

    @Composition
    @OnDelete(DeletePolicy.CASCADE)
    @OneToMany(mappedBy = "bpmProfile")
    protected List<BpmProfileUser> bpmProfileUser;

    public void setProcModel(ExtProcModel procModel) {
        this.procModel = procModel;
    }

    public ExtProcModel getProcModel() {
        return procModel;
    }


    public void setBpmProfileRole(List<BpmProfileRole> bpmProfileRole) {
        this.bpmProfileRole = bpmProfileRole;
    }

    public List<BpmProfileRole> getBpmProfileRole() {
        return bpmProfileRole;
    }

    public void setBpmProfileTask(List<BpmProfileTask> bpmProfileTask) {
        this.bpmProfileTask = bpmProfileTask;
    }

    public List<BpmProfileTask> getBpmProfileTask() {
        return bpmProfileTask;
    }

    public void setBpmProfileUser(List<BpmProfileUser> bpmProfileUser) {
        this.bpmProfileUser = bpmProfileUser;
    }

    public List<BpmProfileUser> getBpmProfileUser() {
        return bpmProfileUser;
    }


    public void setDelegationTargetGroup(DelegationTargetGroup delegationTargetGroup) {
        this.delegationTargetGroup = delegationTargetGroup == null ? null : delegationTargetGroup.getId();
    }

    public DelegationTargetGroup getDelegationTargetGroup() {
        return delegationTargetGroup == null ? null : DelegationTargetGroup.fromId(delegationTargetGroup);
    }


    public void setProcDefinition(ProcDefinition procDefinition) {
        this.procDefinition = procDefinition;
    }

    public ProcDefinition getProcDefinition() {
        return procDefinition;
    }


    public void setDescription(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

    public void setAdHocUserSelection(Boolean adHocUserSelection) {
        this.adHocUserSelection = adHocUserSelection;
    }

    public Boolean getAdHocUserSelection() {
        return adHocUserSelection;
    }

    public void setEntityName(String entityName) {
        this.entityName = entityName;
    }

    public String getEntityName() {
        return entityName;
    }


}