package com.inteacc.bpm.web.salesorder;

import com.haulmont.bpm.entity.ProcInstance;
import com.haulmont.cuba.gui.Notifications;
import com.haulmont.cuba.gui.components.Button;
import com.haulmont.cuba.gui.components.VBoxLayout;
import com.haulmont.cuba.gui.screen.*;
import com.inteacc.bpm.entity.SalesOrder;
import com.inteacc.bpm.service.BpmProfileService;

import javax.inject.Inject;

@UiController("bp_SalesOrder.edit")
@UiDescriptor("sales-order-edit.xml")
@EditedEntityContainer("salesOrderDc")
@LoadDataBeforeShow
public class SalesOrderEdit extends StandardEditor<SalesOrder> {

    @Inject
    protected BpmProfileService bpmProfileService;

    @Inject
    protected Notifications notifications;

    @Subscribe("startProcessBtn")
    protected void onStartProcessBtnClick(Button.ClickEvent event) {
        getScreenData().getDataContext().commit();
        ProcInstance procInstance = bpmProfileService.startProcessUsingBpmProfile(getEditedEntity());
        if (procInstance != null) {
            notifications.create(Notifications.NotificationType.HUMANIZED)
                    .withCaption("Process started")
                    .show();
        }
    }
}