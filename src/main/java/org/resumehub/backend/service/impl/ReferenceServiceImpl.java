package org.resumehub.backend.service.impl;

import lombok.AllArgsConstructor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.resumehub.backend.dto.ReferenceDto;
import org.resumehub.backend.exception.ResourceNotFoundException;
import org.resumehub.backend.map.Mapper;
import org.resumehub.backend.repository.ReferenceRepository;
import org.resumehub.backend.service.ReferenceService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@AllArgsConstructor
@Service
public class ReferenceServiceImpl implements ReferenceService {

    private static final Logger logger = LogManager.getLogger(ReferenceServiceImpl.class);

    private final ReferenceRepository referenceRepository;

    @Override
    public List<ReferenceDto> getAllReferences() {
        var listOfReferences = referenceRepository.findAll()
                .stream().map(Mapper::mapToDto)
                .collect(Collectors.toList());
        logger.info("List of references {}", listOfReferences);
        return listOfReferences;
    }

    @Override
    public ReferenceDto getReferenceById(String referenceId) {
        var oneRecordOfReference = referenceRepository.findById(referenceId)
                .orElseThrow(() -> new ResourceNotFoundException("Reference with given id: " + referenceId + " does not exist"));
        logger.info("One record of reference: {}", oneRecordOfReference);
        return Mapper.mapToDto(oneRecordOfReference);
    }

    @Override
    public ReferenceDto createNewReference(ReferenceDto newReference) {
        var referenceToCreate = Mapper.mapToEntity(newReference);
        var createdReference = referenceRepository.save(referenceToCreate);
        logger.info("Saved record of reference: {}", createdReference);
        return Mapper.mapToDto(createdReference);
    }

    @Override
    public ReferenceDto updateReference(String referenceId, ReferenceDto updatedReference) {
        var existingRecordOfReference = referenceRepository.findById(referenceId)
                .orElseThrow(() -> new ResourceNotFoundException("Reference with given id: " + referenceId + " does not exist"));

        existingRecordOfReference.setUserId(updatedReference.getUserId());
        existingRecordOfReference.setName(updatedReference.getName());
        existingRecordOfReference.setCompany(updatedReference.getCompany());
        existingRecordOfReference.setEmail(updatedReference.getEmail());
        existingRecordOfReference.setMobile(updatedReference.getMobile());
        referenceRepository.save(existingRecordOfReference);
        logger.info("Updated record of reference: {}", existingRecordOfReference);

        return Mapper.mapToDto(existingRecordOfReference);
    }

    @Override
    public void deleteReference(String referenceId) {
        referenceRepository.findById(referenceId)
                .orElseThrow(() -> new ResourceNotFoundException("Reference with given id: " + referenceId + " does not exist"));
        logger.info("Deleted record of reference: {}", referenceRepository.findById(referenceId));
        referenceRepository.deleteById(referenceId);
    }
}
