package com.inteacc.bpm.entity;

import com.haulmont.addon.emailtemplates.entity.EmailTemplate;
import com.haulmont.cuba.core.entity.StandardEntity;

import javax.persistence.*;

@Table(name = "BP_BPM_PROFILE_NOTIFICATION")
@Entity(name = "bp$BpmProfileNotification")
public class BpmProfileNotification extends StandardEntity {
    @Column(name = "TASK_ID")
    protected String taskId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "EMAIL_TEMPLATE_ID")
    protected EmailTemplate emailTemplate;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "BPM_PROFILE_ID")
    protected BpmProfile bpmProfile;

    public BpmProfile getBpmProfile() {
        return bpmProfile;
    }

    public void setBpmProfile(BpmProfile bpmProfile) {
        this.bpmProfile = bpmProfile;
    }

    public EmailTemplate getEmailTemplate() {
        return emailTemplate;
    }

    public void setEmailTemplate(EmailTemplate emailTemplate) {
        this.emailTemplate = emailTemplate;
    }

    public String getTaskId() {
        return taskId;
    }

    public void setTaskId(String taskId) {
        this.taskId = taskId;
    }
}