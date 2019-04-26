package com.inteacc.bpm.entity;

import javax.persistence.Entity;
import com.haulmont.cuba.core.entity.annotation.Extends;
import javax.persistence.Column;
import com.haulmont.cuba.security.entity.Role;

@Extends(Role.class)
@Entity(name = "bp$ExtRole")
public class ExtRole extends Role {
    private static final long serialVersionUID = 5078238839293763510L;

    @Column(name = "TENANT_ID")
    protected String tenantId;

    public void setTenantId(String tenantId) {
        this.tenantId = tenantId;
    }

    public String getTenantId() {
        return tenantId;
    }


}