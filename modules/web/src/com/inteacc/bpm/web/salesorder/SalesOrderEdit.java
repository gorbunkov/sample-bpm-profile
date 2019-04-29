package com.inteacc.bpm.web.salesorder;

import com.haulmont.bpm.entity.ProcInstance;
import com.haulmont.cuba.gui.components.AbstractEditor;
import com.inteacc.bpm.entity.SalesOrder;
import com.inteacc.bpm.service.BpmProfileService;

import javax.inject.Inject;

public class SalesOrderEdit extends AbstractEditor<SalesOrder> {

    @Inject
    protected BpmProfileService bpmProfileService;

    public void startProcess() {
        if (commit()) {
            ProcInstance procInstance = bpmProfileService.startProcessUsingBpmProfile(getItem());
            if (procInstance != null) {
                showNotification(getMessage("processStarted"));
            }
        }
    }
}