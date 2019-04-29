# BPM Profile Sample Project

## Main project classes

* [BpmProfileService.java](modules/global/src/com/inteacc/bpm/service/BpmProfileService.java) - is used for starting the process instance using the BPM Profile.

* [ProcessNotificationService.java](modules/global/src/com/inteacc/bpm/service/ProcessNotificationService.java) - is used for sending a notification based on the configuration from the BPM Profile. 

* [ProcessNotificationBpmnParseHandler.java](modules/core/src/com/inteacc/bpm/core/parsehandler/ProcessNotificationBpmnParseHandler.java) - the Activiti [ParseHandler](https://www.activiti.org/5.x/userguide/#_hooking_into_process_parsing) that adds the NotificationActivitiEventListener into all process definitions.

* [NotificationActivitiEventListener.java](modules/core/src/com/inteacc/bpm/core/eventlistener/NotificationActivitiEventListener.java) - the Activiti [event listener](https://www.activiti.org/5.x/userguide/#eventDispatcher) that is invoked when a process task is assigned to the user. This listener sends a notification using the `ProcessNotificationService`.

* [AppLifecycleBean.java](modules/core/src/com/inteacc/bpm/core/AppLifecycleBean.java) - CUBA lifecycle event listener that registers a `ProcessNotificationBpmnParseHandler` when the application context is started.

## Using the Email Template

In the email template that is used automatically from the process, you may use the `user` parameter that will contain a user loaded with the _local view.

Also you may use the `entity` parameter. This entity is loaded with the `process-notification` view. For example, if you start the process for the `SalesOrder` entity, then the `entity` parameter will contain a SalesOrder instance loaded with the `process-notification` view (or just `_local` if there is no `process-notification` view). For example, you may user the `${entity.docNo}` expression to print the order number in the email body. 