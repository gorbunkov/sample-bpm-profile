package com.inteacc.bpm.entity;

import javax.persistence.Entity;
import javax.persistence.Table;
import com.haulmont.cuba.core.entity.annotation.Lookup;
import com.haulmont.cuba.core.entity.annotation.LookupType;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import com.haulmont.cuba.core.entity.StandardEntity;
import com.haulmont.chile.core.annotations.NamePattern;
import java.math.BigDecimal;
import javax.persistence.Column;
import com.haulmont.cuba.security.entity.User;

@NamePattern("%s|user")
@Table(name = "BP_BPM_PROFILE_USER")
@Entity(name = "bp$BpmProfileUser")
public class BpmProfileUser extends StandardEntity {
    private static final long serialVersionUID = 2564262857882981936L;

    @Lookup(type = LookupType.DROPDOWN)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "EXT_PROC_ROLE_ID")
    protected ExtProcRole extProcRole;

    @Column(name = "LIMIT_AMOUNT")
    protected BigDecimal limitAmount;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "USER_ID")
    protected User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "BPM_PROFILE_ID")
    protected BpmProfile bpmProfile;

    public void setUser(User user) {
        this.user = user;
    }

    public User getUser() {
        return user;
    }


    public void setBpmProfile(BpmProfile bpmProfile) {
        this.bpmProfile = bpmProfile;
    }

    public BpmProfile getBpmProfile() {
        return bpmProfile;
    }


    public void setExtProcRole(ExtProcRole extProcRole) {
        this.extProcRole = extProcRole;
    }

    public ExtProcRole getExtProcRole() {
        return extProcRole;
    }

    public void setLimitAmount(BigDecimal limitAmount) {
        this.limitAmount = limitAmount;
    }

    public BigDecimal getLimitAmount() {
        return limitAmount;
    }



}