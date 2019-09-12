package org.opensrp.service;

import org.apache.commons.lang3.StringUtils;
import org.opensrp.domain.PractitionerRole;
import org.opensrp.repository.PractitionerRoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PractitionerRoleService {

    private PractitionerRoleRepository practitionerRoleRepository;

    @Autowired
    public void setPractitionerRoleRepository(PractitionerRoleRepository practitionerRoleRepository) {
        this.practitionerRoleRepository = practitionerRoleRepository;
    }

    public PractitionerRoleRepository getPractitionerRoleRepository() {
        return practitionerRoleRepository;
    }

    public PractitionerRole getPractitionerRole(String identifier) {
        return StringUtils.isBlank(identifier) ? null : getPractitionerRoleRepository().get(identifier);
    }

    public List<PractitionerRole> getAllPractitionerRoles() {
        return  getPractitionerRoleRepository().getAll();
    }

    public void addOrUpdatePractitionerRole(PractitionerRole practitionerRole) {
        if (StringUtils.isBlank(practitionerRole.getIdentifier())) {
            throw new IllegalArgumentException("Identifier not specified");
        }

        if (getPractitionerRoleRepository().get(practitionerRole.getIdentifier()) != null) {
            getPractitionerRoleRepository().update(practitionerRole);
        } else {
            getPractitionerRoleRepository().add(practitionerRole);
        }
    }

    public void deletePractitionerRole(PractitionerRole practitionerRole) {
        if (StringUtils.isBlank(practitionerRole.getIdentifier())) {
            throw new IllegalArgumentException("Identifier not specified");
        }

        getPractitionerRoleRepository().safeRemove(practitionerRole);
    }

    public List<PractitionerRole> getRolesForPractitioner(String practitionerIdentifier) {
        if (StringUtils.isBlank(practitionerIdentifier)) {
            throw new IllegalArgumentException("Identifier not specified");
        }

        return getPractitionerRoleRepository().getRolesForPractitioner(practitionerIdentifier);
    }

}