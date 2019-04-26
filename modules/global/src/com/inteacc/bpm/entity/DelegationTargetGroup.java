package com.inteacc.bpm.entity;

import com.haulmont.chile.core.datatypes.impl.EnumClass;

import javax.annotation.Nullable;


public enum DelegationTargetGroup implements EnumClass<Integer> {

    ROLES(10),
    USERS(20);

    private Integer id;

    DelegationTargetGroup(Integer value) {
        this.id = value;
    }

    public Integer getId() {
        return id;
    }

    @Nullable
    public static DelegationTargetGroup fromId(Integer id) {
        for (DelegationTargetGroup at : DelegationTargetGroup.values()) {
            if (at.getId().equals(id)) {
                return at;
            }
        }
        return null;
    }
}