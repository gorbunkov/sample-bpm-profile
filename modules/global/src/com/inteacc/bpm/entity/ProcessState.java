package com.inteacc.bpm.entity;

import com.haulmont.chile.core.datatypes.impl.EnumClass;

import javax.annotation.Nullable;


public enum ProcessState implements EnumClass<Integer> {

    NEW(10),
    IN_APPROVAL(20),
    APPROVED(30),
    REJECTED(40),
    ON_CORRECTION(50);

    private Integer id;

    ProcessState(Integer value) {
        this.id = value;
    }

    public Integer getId() {
        return id;
    }

    @Nullable
    public static ProcessState fromId(Integer id) {
        for (ProcessState at : ProcessState.values()) {
            if (at.getId().equals(id)) {
                return at;
            }
        }
        return null;
    }
}