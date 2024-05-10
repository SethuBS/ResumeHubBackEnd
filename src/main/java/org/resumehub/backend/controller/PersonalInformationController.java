package org.resumehub.backend.controller;


import lombok.AllArgsConstructor;
import org.resumehub.backend.dto.PersonalInformationDto;
import org.resumehub.backend.service.PersonalInformationService;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin("*")
@AllArgsConstructor
@RestController
@RequestMapping("/api/v1/personalInfo")
public class PersonalInformationController {

    private final PersonalInformationService personalInformationService;

    @GetMapping
    public ResponseEntity<List<PersonalInformationDto>> getAllPersonalInformation() {
        return ResponseEntity.ok(personalInformationService.getAllPersonalInformation());
    }

    @PostMapping
    public ResponseEntity<PersonalInformationDto> addPersonalInformation(@Validated @RequestBody PersonalInformationDto personalInformation) {
        return ResponseEntity.ok(personalInformationService.savePersonalInformation(personalInformation));
    }

    @GetMapping("{id}")
    public ResponseEntity<PersonalInformationDto> getPersonalInformationById(@Validated @PathVariable("id") String personalInformationId) {
        return ResponseEntity.ok(personalInformationService.getPersonalInformationById(personalInformationId));
    }

    @PutMapping("{id}")
    public ResponseEntity<PersonalInformationDto> updatePersonalInformation(@Validated @PathVariable("id") String personalInformationId,
                                                                            @RequestBody PersonalInformationDto updatedPersonalInformation) {
        return ResponseEntity.ok(personalInformationService.updatePersonalInformation(personalInformationId, updatedPersonalInformation));
    }

    @DeleteMapping("{id}")
    public ResponseEntity<String> deletePersonalInformation(@Validated @PathVariable("id") String personalInformationId) {
        personalInformationService.deletePersonalInformation(personalInformationId);
        return ResponseEntity.ok("Personal Information deleted successful");
    }

}
