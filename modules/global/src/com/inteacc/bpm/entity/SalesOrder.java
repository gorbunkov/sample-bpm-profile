package com.inteacc.bpm.entity;

import javax.persistence.Entity;
import javax.persistence.Table;
import java.math.BigDecimal;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import com.haulmont.cuba.core.entity.StandardEntity;
import com.haulmont.chile.core.annotations.NamePattern;

@NamePattern("%s|docNo")
@Table(name = "BP_SALES_ORDER")
@Entity(name = "bp_SalesOrder")
public class SalesOrder extends StandardEntity {
    private static final long serialVersionUID = 373270466826645186L;

    @Column(name = "DOC_NO")
    protected Integer docNo;

    @Temporal(TemporalType.DATE)
    @Column(name = "DOC_DATE")
    protected Date docDate;

    @Column(name = "CUSTOMER")
    protected String customer;

    @Column(name = "AMOUNT")
    protected BigDecimal amount;

    @Column(name = "PROCESS_STATE")
    protected Integer processState;

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

    public void setCustomer(String customer) {
        this.customer = customer;
    }

    public String getCustomer() {
        return customer;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setProcessState(ProcessState processState) {
        this.processState = processState == null ? null : processState.getId();
    }

    public ProcessState getProcessState() {
        return processState == null ? null : ProcessState.fromId(processState);
    }


}