package com.inteacc.bpm.service;

import com.haulmont.bpm.entity.ProcInstance;
import com.haulmont.bpm.entity.ProcRole;
import com.haulmont.bpm.service.BpmEntitiesService;
import com.haulmont.bpm.service.ProcessRuntimeService;
import com.haulmont.cuba.core.entity.Entity;
import com.haulmont.cuba.core.global.DataManager;
import com.haulmont.cuba.security.entity.Role;
import com.haulmont.cuba.security.entity.User;
import com.inteacc.bpm.entity.BpmProfile;
import com.inteacc.bpm.entity.BpmProfileActor;
import com.inteacc.bpm.entity.DelegationTargetGroup;
import org.slf4j.Logger;
import org.springframework.stereotype.Service;

import javax.annotation.Nullable;
import javax.inject.Inject;
import java.math.BigDecimal;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service(BpmProfileService.NAME)
public class BpmProfileServiceBean implements BpmProfileService {

    @Inject
    protected DataManager dataManager;

    @Inject
    protected Logger log;

    @Inject
    protected ProcessRuntimeService processRuntimeService;

    @Inject
    protected BpmEntitiesService bpmEntitiesService;

    @Override
    public ProcInstance startProcessUsingBpmProfile(Entity entity) {
        BpmProfile bpmProfile = findProfileByEntityName(entity.getMetaClass().getName());
        if (bpmProfile == null) {
            log.info("BpmProfile for entity {} not found", entity.getMetaClass().getName());
            return null;
        }
        ProcInstance procInstance = buildProcInstanceByProfile(bpmProfile, entity);
        return processRuntimeService.startProcess(procInstance, null, new HashMap<>());
    }

    private ProcInstance buildProcInstanceByProfile(BpmProfile bpmProfile, Entity entity) {
        BigDecimal limitAmountValue = entity.getValue(bpmProfile.getLimitAmountFieldName());
        if (limitAmountValue == null) limitAmountValue = new BigDecimal(0);
        BpmEntitiesService.ProcInstanceDetails procInstanceDetails = new BpmEntitiesService.ProcInstanceDetails(bpmProfile.getProcDefinition().getCode())
                .setEntity(entity);
        Map<ProcRole, List<BpmProfileActor>> profileRolesByProcRoleMap = bpmProfile.getBpmProfileActors().stream()
                .collect(Collectors.groupingBy(BpmProfileActor::getProcRole, Collectors.toList()));
        for (Map.Entry<ProcRole, List<BpmProfileActor>> entry : profileRolesByProcRoleMap.entrySet()) {
            ProcRole procRole = entry.getKey();
            List<BpmProfileActor> profileActors = entry.getValue();
            profileActors.sort(Comparator.comparing(BpmProfileActor::getLimitAmount,
                    Comparator.nullsFirst(Comparator.naturalOrder())));
            //todo what if there is no user with a proper limit?
            for (BpmProfileActor profileActor : profileActors) {
                //if the limit from the entity is less or equal to the limit of profile actor
                if (profileActor.getLimitAmount() == null || limitAmountValue.compareTo(profileActor.getLimitAmount()) <= 0) {
                    if (profileActor.getDelegationTargetGroup() == DelegationTargetGroup.USERS) {
                        procInstanceDetails.addProcActor(procRole, profileActor.getUser());
                    } else if (profileActor.getDelegationTargetGroup() == DelegationTargetGroup.ROLES) {
                        List<User> usersBySecRole = findUsersBySecRole(profileActor.getSecRole());
                        usersBySecRole.forEach(user -> procInstanceDetails.addProcActor(procRole, user));
                    }
                    break;
                }
            }
        }
        return bpmEntitiesService.createProcInstance(procInstanceDetails);
    }

    @Nullable
    private BpmProfile findProfileByEntityName(String entityName) {
        List<BpmProfile> profiles = dataManager.load(BpmProfile.class)
                .query("select p from bp$BpmProfile p where p.entityName = :entityName")
                .parameter("entityName", entityName)
                .view("bpmProfile-edit")
                .list();
        return !profiles.isEmpty() ? profiles.get(0) : null;
    }

    private List<User> findUsersBySecRole(Role role) {
        return dataManager.load(User.class)
                .query("select u from sec$User u join u.userRoles ur where ur.role.id = :role")
                .parameter("role", role)
                .list();
    }
}