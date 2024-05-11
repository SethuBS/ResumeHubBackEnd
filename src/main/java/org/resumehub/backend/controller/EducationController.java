package org.resumehub.backend.controller;

import lombok.AllArgsConstructor;
import org.resumehub.backend.dto.EducationDto;
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
    public ResponseEntity<List<EducationDto>> getAllEducation() {
        return ResponseEntity.ok(educationService.getAllEducation());
    }

    @GetMapping("{id}")
    public ResponseEntity<EducationDto> getEducationById(@Validated @PathVariable("id") String educationId) {
        return ResponseEntity.ok(educationService.getEducationById(educationId));
    }

    @PostMapping
    public ResponseEntity<EducationDto> createEducation(@Validated @RequestBody EducationDto newEducation) {
        return ResponseEntity.ok(educationService.createEducation(newEducation));
    }

    @PutMapping("{id}")
    public ResponseEntity<EducationDto> updateEducation(@Validated @PathVariable("id")
                                                        @RequestBody String educationId, EducationDto updatedEducation) {
        return ResponseEntity.ok(educationService.updateEducation(educationId, updatedEducation));
    }

    @DeleteMapping("{id}")
    ResponseEntity<String> deleteEducation(@Validated @PathVariable("id") String educationId) {
        educationService.deleteEducation(educationId);
        return ResponseEntity.ok("Education deleted successful");
    }
}
