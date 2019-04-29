package com.inteacc.bpm.core.eventlistener;

import com.google.common.base.Strings;
import com.haulmont.cuba.core.global.AppBeans;
import com.inteacc.bpm.service.ProcessNotificationService;
import org.activiti.engine.delegate.event.ActivitiEntityEvent;
import org.activiti.engine.delegate.event.ActivitiEvent;
import org.activiti.engine.delegate.event.ActivitiEventListener;
import org.activiti.engine.impl.persistence.entity.TaskEntity;

import java.util.UUID;

/**
 * The event listener that sends a notification configured in the {@link com.inteacc.bpm.entity.BpmProfile} each time the process reaches the
 * UserTask
 */
public class NotificationActivitiEventListener implements ActivitiEventListener {

    @Override
    public void onEvent(ActivitiEvent event) {
        switch (event.getType()) {
            case TASK_ASSIGNED:
                TaskEntity task = (TaskEntity) ((ActivitiEntityEvent) event).getEntity();
                String assignee = task.getAssignee();
                String entityName = (String) task.getVariable("entityName");
                UUID entityId = (UUID) task.getVariable("entityId");
                if (!Strings.isNullOrEmpty(entityName) && entityId != null) {
                    ProcessNotificationService processNotificationService = AppBeans.get(ProcessNotificationService.class);
                    processNotificationService.sendNotificationByBpmProfile(entityName,
                            entityId,
                            task.getTaskDefinitionKey(),
                            UUID.fromString(assignee));
                }
                break;
        }
    }

    @Override
    public boolean isFailOnException() {
        return false;
    }
}
