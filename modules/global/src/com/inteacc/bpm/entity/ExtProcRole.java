package com.inteacc.bpm.entity;

import javax.persistence.Entity;
import com.haulmont.cuba.core.entity.annotation.Extends;
import javax.persistence.Column;
import com.haulmont.bpm.entity.ProcRole;

@Extends(ProcRole.class)
@Entity(name = "bp$ExtProcRole")
public class ExtProcRole extends ProcRole {
    private static final long serialVersionUID = -7419176262674302923L;

    @Column(name = "TENANT_ID")
    protected String tenantId;

    public void setTenantId(String tenantId) {
        this.tenantId = tenantId;
    }

    public String getTenantId() {
        return tenantId;
    }


}