# BPM Profile Sample Project

## Main project classes

* [BpmProfileService.java](modules/global/src/com/inteacc/bpm/service/BpmProfileService.java) - is used for starting the process instance using the BPM Profile.

* [ProcessNotificationService.java](modules/global/src/com/inteacc/bpm/service/ProcessNotificationService.java) - is used for sending a notification based on the configuration from the BPM Profile. 

* [ProcessNotificationBpmnParseHandler.java](modules/core/src/com/inteacc/bpm/core/parsehandler/ProcessNotificationBpmnParseHandler.java) - the Activiti [ParseHandler](https://www.activiti.org/5.x/userguide/#_hooking_into_process_parsing) that adds the NotificationActivitiEventListener into all process definitions.

* [NotificationActivitiEventListener.java](modules/core/src/com/inteacc/bpm/core/eventlistener/NotificationActivitiEventListener.java) - the Activiti [event listener](https://www.activiti.org/5.x/userguide/#eventDispatcher) that is invoked when a process task is assigned to the user. This listener sends a notification using the `ProcessNotificationService`.

* [AppLifecycleBean.java](modules/core/src/com/inteacc/bpm/core/AppLifecycleBean.java) - CUBA lifecycle event listener that registers a `ProcessNotificationBpmnParseHandler` when the application context is started.

* [ProcButtonsFragment.java](modules/web/src/com/inteacc/bpm/web/procactions/ProcButtonsFragment.java)- the fragment that displays process action buttons (start process button, complete task buttons).

## Using the Email Template

In the email template that is used automatically from the process, you may use the `user` parameter that will contain a user loaded with the _local view.

Also you may use the `entity` parameter. This entity is loaded with the `process-notification` view. For example, if you start the process for the `SalesOrder` entity, then the `entity` parameter will contain a SalesOrder instance loaded with the `process-notification` view (or just `_local` if there is no `process-notification` view). For example, you may user the `${entity.docNo}` expression to print the order number in the email body.

The `editorLink` parameter will hold a URL for entity editor. 

## ProcButtonsFragment

The `ProcButtonsFragment` works similar to the `ProcActionsFragment` of the BPM add-on. 

In the screen XML descriptor add the fragment:

```xml
<fragment id="procButtonsFragment" screen="bp_ProcButtonsFragment" width="400px"/>
```

In the editor controller inject the fragment and invoke its `initLayout(Entity entity)` method:

```java
@UiController("bp_SalesOrder.edit")
@UiDescriptor("sales-order-edit.xml")
@EditedEntityContainer("salesOrderDc")
@LoadDataBeforeShow
public class SalesOrderEdit extends StandardEditor<SalesOrder> {

    @Inject
    protected ProcButtonsFragment procButtonsFragment;

    @Subscribe
    protected void onBeforeShow(BeforeShowEvent event) {
        procButtonsFragment.initLayout(getEditedEntity());
    }
}
```

The initLayout() method will find the proper `BpmProfile`, analyze if there is an active process instance related to the edited entity and display proper buttons. Also the logic in the frame make the editor read-only if there is an active process and there are no process tasks for the current user. 