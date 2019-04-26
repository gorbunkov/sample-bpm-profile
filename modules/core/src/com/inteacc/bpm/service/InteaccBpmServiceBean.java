package com.inteacc.bpm.service;

import com.haulmont.addon.emailtemplates.builder.EmailTemplateBuilder;
import com.haulmont.addon.emailtemplates.core.EmailTemplatesAPI;
import com.haulmont.addon.emailtemplates.entity.EmailTemplate;
import com.haulmont.addon.emailtemplates.exceptions.ReportParameterTypeChangedException;
import com.haulmont.addon.emailtemplates.exceptions.TemplateNotFoundException;
import com.haulmont.cuba.core.Persistence;
import com.haulmont.cuba.core.global.EmailException;
import com.haulmont.cuba.core.global.GlobalConfig;
import com.haulmont.cuba.security.entity.User;
import com.haulmont.reports.entity.Report;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.UUID;

@Service(InteaccBpmService.NAME)
public class InteaccBpmServiceBean implements InteaccBpmService {

    // create logger
    private Logger log = LoggerFactory.getLogger(InteaccBpmServiceBean.class);

    @Inject
    private Persistence persistence;
    @Inject
    EmailTemplatesAPI emailTemplatesAPI;
    @Inject
    GlobalConfig globalConfig;


    public void sendNotificationToRecepient(EmailTemplate emailTemplate, Boolean sendAttachment, String entityName, UUID entityId, User receiverUser){

        String linkString = globalConfig.getWebAppUrl()+"/open?screen="+entityName+".edit&item="+entityName+"-"+entityId;

        Report report = null;

        try {

            EmailTemplateBuilder builder = emailTemplatesAPI.buildFromTemplate(emailTemplate)
                    .setTo(receiverUser.getEmail())
                    .setSubject(emailTemplate.getSubject())
                    .setBodyParameter("receiver", receiverUser.getFirstName())
                    .setBodyParameter("linkParameter", linkString);

            if (sendAttachment) {
                builder.addAttachmentReport(report);
            }
            builder.sendEmail();

        } catch (TemplateNotFoundException e) {
            e.printStackTrace();
            log.error("Template is not found");

        } catch (ReportParameterTypeChangedException e) {
            e.printStackTrace();
            log.error("Parameter type change exception");

        } catch (EmailException e) {
            e.printStackTrace();

            log.error("Email not valid");
        }

    }

}