package com.inteacc.bpm.web.salesorder;

import com.haulmont.cuba.gui.screen.LoadDataBeforeShow;
import com.haulmont.cuba.gui.screen.StandardLookup;
import com.haulmont.cuba.gui.screen.UiController;
import com.haulmont.cuba.gui.screen.UiDescriptor;
import com.inteacc.bpm.entity.SalesOrder;

@UiController("bp_SalesOrder.browse")
@UiDescriptor("sales-order-browse.xml")
@LoadDataBeforeShow
public class SalesOrderBrowse extends StandardLookup<SalesOrder> {
}