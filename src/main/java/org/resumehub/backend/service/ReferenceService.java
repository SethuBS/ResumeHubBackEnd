package org.resumehub.backend.service;

import org.resumehub.backend.dto.ReferenceDTO;

import java.util.List;

public interface ReferenceService {
    List<ReferenceDTO> getAllReferences();

    ReferenceDTO getReferenceById(String referenceId);

    ReferenceDTO createNewReference(ReferenceDTO newReference);

    ReferenceDTO updateReference(String referenceId, ReferenceDTO updatedReference);

    void deleteReference(String referenceId);
}
