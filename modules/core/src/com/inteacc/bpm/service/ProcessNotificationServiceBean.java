package com.inteacc.bpm.service;

import com.google.common.base.Strings;
import com.haulmont.addon.emailtemplates.core.EmailTemplatesAPI;
import com.haulmont.addon.emailtemplates.dto.ExtendedEmailInfo;
import com.haulmont.addon.emailtemplates.entity.EmailTemplate;
import com.haulmont.addon.emailtemplates.exceptions.ReportParameterTypeChangedException;
import com.haulmont.addon.emailtemplates.exceptions.TemplateNotFoundException;
import com.haulmont.addon.emailtemplates.service.EmailService;
import com.haulmont.chile.core.model.MetaClass;
import com.haulmont.cuba.core.entity.Entity;
import com.haulmont.cuba.core.global.*;
import com.haulmont.cuba.security.entity.User;
import com.inteacc.bpm.entity.BpmProfileNotification;
import org.slf4j.Logger;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Service(ProcessNotificationService.NAME)
public class ProcessNotificationServiceBean implements ProcessNotificationService {

    private static final String PROCESS_NOTIFICATION_VIEW_NAME = "process-notification";

    @Inject
    protected DataManager dataManager;

    @Inject
    protected Metadata metadata;

    @Inject
    protected EmailTemplatesAPI emailTemplatesAPI;

    @Inject
    protected EmailService emailService;

    @Inject
    protected ViewRepository viewRepository;

    @Inject
    protected Logger log;

    @Override
    public void sendNotificationByBpmProfile(String entityName, UUID entityId, String processTaskKey, UUID userId) {
        List<BpmProfileNotification> notifications = dataManager.load(BpmProfileNotification.class)
                .query("select n from bp$BpmProfileNotification n where n.bpmProfile.entityName = :entityName and n.taskId = :taskId")
                .parameter("entityName", entityName)
                .parameter("taskId", processTaskKey)
                .view("bpmProfileNotification-process-send")
                .list();
        if (!notifications.isEmpty()) {
            BpmProfileNotification notification = notifications.get(0);
            EmailTemplate emailTemplate = notification.getEmailTemplate();
            if (emailTemplate != null) {
                MetaClass metaClass = metadata.getClassNN(entityName);
                View processNotificationView = viewRepository.findView(metaClass, PROCESS_NOTIFICATION_VIEW_NAME);
                Entity entity = dataManager.load(metaClass.getJavaClass())
                        .id(entityId)
                        .view(processNotificationView != null ? processNotificationView : viewRepository.getView(metaClass, View.LOCAL))
                        .one();

                User user = dataManager.load(User.class)
                        .id(userId)
                        .view(View.LOCAL)
                        .one();
                if (Strings.isNullOrEmpty(user.getEmail())) {
                    log.warn("User {} doesn't have a email. A notification will not be sent.", user.getLogin());
                    return;
                }
                Map<String, Object> emailTemplateParams = new HashMap<>();
                emailTemplateParams.put("entity", entity);
                emailTemplateParams.put("user", user);
                ExtendedEmailInfo emailInfo = null;
                try {
                    EmailTemplate modifiedEmailTemplate = emailTemplatesAPI.buildFromTemplate(emailTemplate.getCode())
                            .addTo(user.getEmail())
                            .build();
                    emailInfo = emailTemplatesAPI.generateEmail(modifiedEmailTemplate, emailTemplateParams);
                    emailService.sendEmailAsync(emailInfo);
                } catch (TemplateNotFoundException | ReportParameterTypeChangedException e) {
                    log.error("Error on sending the process notification", e);
                }
            }
        }
    }
}