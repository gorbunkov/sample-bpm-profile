package com.inteacc.bpm.entity;

import javax.persistence.Entity;
import javax.persistence.Column;
import com.haulmont.bpm.entity.ProcTask;
import com.haulmont.chile.core.annotations.NamePattern;

@NamePattern("%s|name")
@Entity(name = "bp$ExtProcTask")
public class ExtProcTask extends ProcTask {
    private static final long serialVersionUID = 3770550816748731745L;

    @Column(name = "TENANT_ID")
    protected String tenantId;

    public void setTenantId(String tenantId) {
        this.tenantId = tenantId;
    }

    public String getTenantId() {
        return tenantId;
    }


}