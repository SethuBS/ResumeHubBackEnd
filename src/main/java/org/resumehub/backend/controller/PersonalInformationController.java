package org.resumehub.backend.controller;


import lombok.AllArgsConstructor;
import org.resumehub.backend.dto.PersonalInformationDTO;
import org.resumehub.backend.service.PersonalInformationService;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin("*")
@AllArgsConstructor
@RestController
@RequestMapping("/api/v1/personalInfo/")
public class PersonalInformationController {

    private final PersonalInformationService personalInformationService;

    @GetMapping
    public ResponseEntity<List<PersonalInformationDTO>> getAllPersonalInformation(@RequestHeader("authorization") String jwt) {
        return ResponseEntity.ok(personalInformationService.getAllPersonalInformation());
    }

    @PostMapping
    public ResponseEntity<PersonalInformationDTO> addPersonalInformation(@Validated @RequestHeader("authorization") String jwt,
                                                                         @RequestBody PersonalInformationDTO personalInformation) {
        return ResponseEntity.ok(personalInformationService.savePersonalInformation(personalInformation));
    }

    @GetMapping("{id}")
    public ResponseEntity<PersonalInformationDTO> getPersonalInformationById(@Validated @RequestHeader("authorization") String jwt,
                                                                             @PathVariable("id") String personalInformationId) {
        return ResponseEntity.ok(personalInformationService.getPersonalInformationById(personalInformationId));
    }

    @PutMapping("{id}")
    public ResponseEntity<PersonalInformationDTO> updatePersonalInformation(@Validated @RequestHeader("authorization") String jwt,
                                                                            @PathVariable("id") String personalInformationId,
                                                                            @RequestBody PersonalInformationDTO updatedPersonalInformation) {
        return ResponseEntity.ok(personalInformationService.updatePersonalInformation(personalInformationId, updatedPersonalInformation));
    }

    @DeleteMapping("{id}")
    public ResponseEntity<String> deletePersonalInformation(@Validated @RequestHeader("authorization") String jwt,
                                                            @PathVariable("id") String personalInformationId) {
        personalInformationService.deletePersonalInformation(personalInformationId);
        return ResponseEntity.ok("Personal Information deleted successful");
    }

}
