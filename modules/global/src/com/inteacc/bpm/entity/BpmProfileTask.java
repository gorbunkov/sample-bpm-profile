package com.inteacc.bpm.entity;

import javax.persistence.Entity;
import javax.persistence.Table;
import com.haulmont.cuba.core.entity.annotation.Lookup;
import com.haulmont.cuba.core.entity.annotation.LookupType;
import javax.persistence.Column;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import com.haulmont.addon.sdbmt.entity.StandardTenantEntity;
import com.haulmont.chile.core.annotations.NamePattern;
import java.math.BigDecimal;

@NamePattern("%s|procTask")
@Table(name = "BP_BPM_PROFILE_TASK")
@Entity(name = "bp$BpmProfileTask")
public class BpmProfileTask extends StandardTenantEntity {
    private static final long serialVersionUID = 414963767430342960L;

    @Lookup(type = LookupType.DROPDOWN)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "PROC_TASK_ID")
    protected ExtProcTask procTask;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "EMAIL_TEMPLATE_ID")
    protected EmailTemplate emailTemplate;

    @Column(name = "AMOUNT_FIELD_NAME")
    protected String amountFieldName;

    @Column(name = "SEND_LINK")
    protected Boolean sendLink;

    @Column(name = "SEND_DOC_ATTACHMENT")
    protected Boolean sendDocAttachment;

    @Column(name = "REMINDER_DAYS")
    protected BigDecimal reminderDays;

    @Column(name = "ESCALATION_DAYS")
    protected BigDecimal escalationDays;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "BPM_PROFILE_ID")
    protected BpmProfile bpmProfile;

    public void setReminderDays(BigDecimal reminderDays) {
        this.reminderDays = reminderDays;
    }

    public BigDecimal getReminderDays() {
        return reminderDays;
    }

    public void setEscalationDays(BigDecimal escalationDays) {
        this.escalationDays = escalationDays;
    }

    public BigDecimal getEscalationDays() {
        return escalationDays;
    }


    public void setBpmProfile(BpmProfile bpmProfile) {
        this.bpmProfile = bpmProfile;
    }

    public BpmProfile getBpmProfile() {
        return bpmProfile;
    }


    public void setProcTask(ExtProcTask procTask) {
        this.procTask = procTask;
    }

    public ExtProcTask getProcTask() {
        return procTask;
    }

    public void setEmailTemplate(EmailTemplate emailTemplate) {
        this.emailTemplate = emailTemplate;
    }

    public EmailTemplate getEmailTemplate() {
        return emailTemplate;
    }

    public void setAmountFieldName(String amountFieldName) {
        this.amountFieldName = amountFieldName;
    }

    public String getAmountFieldName() {
        return amountFieldName;
    }

    public void setSendLink(Boolean sendLink) {
        this.sendLink = sendLink;
    }

    public Boolean getSendLink() {
        return sendLink;
    }

    public void setSendDocAttachment(Boolean sendDocAttachment) {
        this.sendDocAttachment = sendDocAttachment;
    }

    public Boolean getSendDocAttachment() {
        return sendDocAttachment;
    }


}