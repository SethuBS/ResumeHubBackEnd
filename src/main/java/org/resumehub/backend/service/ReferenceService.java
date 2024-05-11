package org.resumehub.backend.service;

import org.resumehub.backend.dto.ReferenceDto;

import java.util.List;

public interface ReferenceService {
    List<ReferenceDto> getAllReferences();

    ReferenceDto getReferenceById(String referenceId);

    ReferenceDto createNewReference(ReferenceDto newReference);

    ReferenceDto updateReference(String referenceId, ReferenceDto updatedReference);

    void deleteReference(String referenceId);
}
