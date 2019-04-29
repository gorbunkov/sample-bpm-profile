package com.inteacc.bpm.service;

import java.util.UUID;

public interface ProcessNotificationService {
    String NAME = "bp_ProcessNotificationService";

    /**
     * Method builds an email by the email template defined in the {@link com.inteacc.bpm.entity.BpmProfile} related to the entity with a given {@code
     * entityName}. The following process variables may be used in the email templates:
     * <ul>
     *     <li>entity - the entity. It will be reloaded with the {@code process-notification} or {@code _local} view</li>
     *     <li>user - the user object loaded with the {@code _local} view</li>
     * </ul>
     *
     * After the email is built, it will be put to the queue for asynchronous email sending.
     */
    void sendNotificationByBpmProfile(String entityName, UUID entityId, String processTaskKey, UUID userId);
}