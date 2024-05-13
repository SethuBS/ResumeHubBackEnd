package org.resumehub.backend.controller;

import lombok.AllArgsConstructor;
import org.resumehub.backend.dto.EducationDTO;
import org.resumehub.backend.service.EducationService;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin("*")
@AllArgsConstructor
@RestController
@RequestMapping("/api/v1/education/")
public class EducationController {

    private final EducationService educationService;

    @GetMapping
    public ResponseEntity<List<EducationDTO>> getAllEducation() {
        return ResponseEntity.ok(educationService.getAllEducation());
    }

    @GetMapping("{id}")
    public ResponseEntity<EducationDTO> getEducationById(@Validated @PathVariable("id") String educationId) {
        return ResponseEntity.ok(educationService.getEducationById(educationId));
    }

    @PostMapping
    public ResponseEntity<EducationDTO> createEducation(@Validated @RequestBody EducationDTO newEducation) {
        return ResponseEntity.ok(educationService.createEducation(newEducation));
    }

    @PutMapping("{id}")
    public ResponseEntity<EducationDTO> updateEducation(@Validated @PathVariable("id")
                                                        @RequestBody String educationId, EducationDTO updatedEducation) {
        return ResponseEntity.ok(educationService.updateEducation(educationId, updatedEducation));
    }

    @DeleteMapping("{id}")
    ResponseEntity<String> deleteEducation(@Validated @PathVariable("id") String educationId) {
        educationService.deleteEducation(educationId);
        return ResponseEntity.ok("Education deleted successful");
    }
}
