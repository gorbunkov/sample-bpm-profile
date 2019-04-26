package com.inteacc.bpm.entity;

import javax.persistence.Entity;
import javax.persistence.Table;
import com.haulmont.cuba.core.entity.annotation.Lookup;
import com.haulmont.cuba.core.entity.annotation.LookupType;
import java.math.BigDecimal;
import javax.persistence.Column;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import com.haulmont.cuba.core.entity.StandardEntity;
import com.haulmont.chile.core.annotations.NamePattern;

@NamePattern("%s|procRole")
@Table(name = "BP_BPM_PROFILE_ROLE")
@Entity(name = "bp$BpmProfileRole")
public class BpmProfileRole extends StandardEntity {
    private static final long serialVersionUID = 5328754209070068117L;

    @Lookup(type = LookupType.DROPDOWN)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ROLE_ID")
    protected ExtRole role;

    @Lookup(type = LookupType.DROPDOWN)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "PROC_ROLE_ID")
    protected ExtProcRole procRole;

    @Column(name = "LIMIT_AMOUNT")
    protected BigDecimal limitAmount;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "BPM_PROFILE_ID")
    protected BpmProfile bpmProfile;

    public void setBpmProfile(BpmProfile bpmProfile) {
        this.bpmProfile = bpmProfile;
    }

    public BpmProfile getBpmProfile() {
        return bpmProfile;
    }


    public void setRole(ExtRole role) {
        this.role = role;
    }

    public ExtRole getRole() {
        return role;
    }

    public void setProcRole(ExtProcRole procRole) {
        this.procRole = procRole;
    }

    public ExtProcRole getProcRole() {
        return procRole;
    }

    public void setLimitAmount(BigDecimal limitAmount) {
        this.limitAmount = limitAmount;
    }

    public BigDecimal getLimitAmount() {
        return limitAmount;
    }


}