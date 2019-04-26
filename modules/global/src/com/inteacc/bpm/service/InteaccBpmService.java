package com.inteacc.bpm.service;


import com.haulmont.addon.emailtemplates.entity.EmailTemplate;
import com.haulmont.cuba.security.entity.User;

import java.util.UUID;

public interface InteaccBpmService {
    String NAME = "bp_InteaccBpmService";

    public void sendNotificationToRecepient(EmailTemplate emailTemplate, Boolean sendAttachment, String entityName, UUID entityId, User receiverUser);

}