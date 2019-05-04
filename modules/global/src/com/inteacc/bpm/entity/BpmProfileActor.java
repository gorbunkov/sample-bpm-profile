package com.inteacc.bpm.entity;

import com.haulmont.bpm.entity.ProcRole;
import com.haulmont.cuba.core.entity.StandardEntity;
import com.haulmont.cuba.security.entity.Role;
import com.haulmont.cuba.security.entity.User;

import javax.persistence.*;
import java.math.BigDecimal;

@Table(name = "BP_BPM_PROFILE_ACTOR")
@Entity(name = "bp_BpmProfileActor")
public class BpmProfileActor extends StandardEntity {
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "PROC_ROLE_ID")
    protected ProcRole procRole;

    @Column(name = "DELEGATION_TARGET_GROUP")
    protected Integer delegationTargetGroup = DelegationTargetGroup.ROLES.getId();

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "USER_ID")
    protected User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "SEC_ROLE_ID")
    protected Role secRole;

    @Column(name = "LIMIT_AMOUNT")
    protected BigDecimal limitAmount;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "BPM_PROFILE_ID")
    protected BpmProfile bpmProfile;

    public BpmProfile getBpmProfile() {
        return bpmProfile;
    }

    public void setBpmProfile(BpmProfile bpmProfile) {
        this.bpmProfile = bpmProfile;
    }

    public DelegationTargetGroup getDelegationTargetGroup() {
        return delegationTargetGroup == null ? null : DelegationTargetGroup.fromId(delegationTargetGroup);
    }

    public void setDelegationTargetGroup(DelegationTargetGroup delegationTargetGroup) {
        this.delegationTargetGroup = delegationTargetGroup == null ? null : delegationTargetGroup.getId();
    }

    public BigDecimal getLimitAmount() {
        return limitAmount;
    }

    public void setLimitAmount(BigDecimal limitAmount) {
        this.limitAmount = limitAmount;
    }

    public Role getSecRole() {
        return secRole;
    }

    public void setSecRole(Role secRole) {
        this.secRole = secRole;
    }

    public ProcRole getProcRole() {
        return procRole;
    }

    public void setProcRole(ProcRole procRole) {
        this.procRole = procRole;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}