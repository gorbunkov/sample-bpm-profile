package com.inteacc.bpm.entity;

import javax.persistence.Entity;
import javax.persistence.DiscriminatorValue;
import com.haulmont.cuba.core.entity.annotation.Extends;
import javax.persistence.Column;
import com.haulmont.bpm.entity.ProcModel;

@Extends(ProcModel.class)
@DiscriminatorValue("EXT")
@Entity(name = "bp$ExtProcModel")
public class ExtProcModel extends ProcModel {
    private static final long serialVersionUID = 8634107842607038608L;

    @Column(name = "TENANT_ID")
    protected String tenantId;

    public void setTenantId(String tenantId) {
        this.tenantId = tenantId;
    }

    public String getTenantId() {
        return tenantId;
    }


}