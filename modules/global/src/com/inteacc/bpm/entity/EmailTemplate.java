package com.inteacc.bpm.entity;

import javax.persistence.Entity;
import javax.persistence.Column;
import com.haulmont.addon.emailtemplates.entity.JsonEmailTemplate;
import com.haulmont.chile.core.annotations.NamePattern;
import com.haulmont.cuba.core.entity.annotation.Extends;

@Extends(JsonEmailTemplate.class)
@NamePattern("%s (%s)|name,code")
@Entity(name = "bp$EmailTemplate")
public class EmailTemplate extends JsonEmailTemplate {
    private static final long serialVersionUID = 7766121214177921910L;

    @Column(name = "TENANT_ID")
    protected String tenantId;

    public void setTenantId(String tenantId) {
        this.tenantId = tenantId;
    }

    public String getTenantId() {
        return tenantId;
    }


}