package com.inteacc.bpm.entity;

import com.haulmont.bpm.entity.ProcDefinition;
import com.haulmont.chile.core.annotations.Composition;
import com.haulmont.chile.core.annotations.NamePattern;
import com.haulmont.cuba.core.entity.StandardEntity;
import com.haulmont.cuba.core.entity.annotation.Lookup;
import com.haulmont.cuba.core.entity.annotation.LookupType;
import com.haulmont.cuba.core.entity.annotation.OnDelete;
import com.haulmont.cuba.core.global.DeletePolicy;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.List;

@NamePattern("%s|description")
@Table(name = "BP_BPM_PROFILE")
@Entity(name = "bp$BpmProfile")
public class BpmProfile extends StandardEntity {
    private static final long serialVersionUID = -926644504110944239L;

    @Column(name = "DESCRIPTION")
    protected String description;

    @Column(name = "AD_HOC_USER_SELECTION")
    protected Boolean adHocUserSelection;

    @NotNull
    @Column(name = "ENTITY_NAME", nullable = false)
    protected String entityName;

    @NotNull
    @Lookup(type = LookupType.DROPDOWN, actions = {"lookup"})
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "PROC_DEFINITION_ID")
    protected ProcDefinition procDefinition;

    @Composition
    @OnDelete(DeletePolicy.CASCADE)
    @OneToMany(mappedBy = "bpmProfile")
    protected List<BpmProfileActor> bpmProfileActors;

    @Column(name = "LIMIT_AMOUNT_FIELD_NAME")
    protected String limitAmountFieldName;

    @Composition
    @OnDelete(DeletePolicy.CASCADE)
    @OneToMany(mappedBy = "bpmProfile")
    protected List<BpmProfileNotification> bpmProfileNotifications;

    public List<BpmProfileNotification> getBpmProfileNotifications() {
        return bpmProfileNotifications;
    }

    public void setBpmProfileNotifications(List<BpmProfileNotification> bpmProfileNotifications) {
        this.bpmProfileNotifications = bpmProfileNotifications;
    }

    public String getLimitAmountFieldName() {
        return limitAmountFieldName;
    }

    public void setLimitAmountFieldName(String limitAmountFieldName) {
        this.limitAmountFieldName = limitAmountFieldName;
    }

    public List<BpmProfileActor> getBpmProfileActors() {
        return bpmProfileActors;
    }

    public void setBpmProfileActors(List<BpmProfileActor> bpmProfileActors) {
        this.bpmProfileActors = bpmProfileActors;
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