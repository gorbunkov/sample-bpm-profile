package com.inteacc.bpm.web.salesorder;

import com.haulmont.cuba.gui.screen.*;
import com.inteacc.bpm.entity.SalesOrder;
import com.inteacc.bpm.web.procactions.ProcButtonsFragment;

import javax.inject.Inject;

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