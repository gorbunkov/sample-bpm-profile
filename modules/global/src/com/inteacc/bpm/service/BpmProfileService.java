package com.inteacc.bpm.service;

import com.haulmont.bpm.entity.ProcInstance;
import com.haulmont.cuba.core.entity.Entity;
import com.inteacc.bpm.entity.BpmProfile;

import javax.annotation.Nullable;

public interface BpmProfileService {
    String NAME = "bp_BpmProcessService";

    /**
     * The method finds a {@link com.inteacc.bpm.entity.BpmProfile} related to the given entity type and if found it creates a {@link ProcInstance}
     * with a set of {@link com.haulmont.bpm.entity.ProcActor}. ProcActors are filled with users according to rules defined in the `BpmProfile`.
     * <p>
     * If no BpmProfile is found for hte entity type, then nothing happens.
     *
     * @return a ProcInstance object or null if BmProfile was not found and process wasn't started.
     */
    @Nullable
    ProcInstance startProcessUsingBpmProfile(Entity entity);

    @Nullable
    BpmProfile findBpmProfileByEntityName(String entityName);
}