package com.inteacc.bpm.entity;

import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import com.haulmont.cuba.core.entity.StandardEntity;
import com.haulmont.chile.core.annotations.NamePattern;

@NamePattern("%s|docNo")
@Table(name = "BP_PURCHASE_ORDER")
@Entity(name = "bp$PurchaseOrder")
public class PurchaseOrder extends StandardEntity {
    private static final long serialVersionUID = 7994175952585727603L;

    @Column(name = "DOC_NO")
    protected Integer docNo;

    @Temporal(TemporalType.DATE)
    @Column(name = "DOC_DATE")
    protected Date docDate;

    @Column(name = "DESCRIPTION")
    protected String description;

    @Column(name = "AMOUNT")
    protected String amount;

    @Column(name = "PROCESS_STATE")
    protected Integer processState;

    public void setProcessState(ProcessState processState) {
        this.processState = processState == null ? null : processState.getId();
    }

    public ProcessState getProcessState() {
        return processState == null ? null : ProcessState.fromId(processState);
    }


    public void setDocNo(Integer docNo) {
        this.docNo = docNo;
    }

    public Integer getDocNo() {
        return docNo;
    }

    public void setDocDate(Date docDate) {
        this.docDate = docDate;
    }

    public Date getDocDate() {
        return docDate;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getAmount() {
        return amount;
    }


}