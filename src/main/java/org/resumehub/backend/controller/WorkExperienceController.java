package org.resumehub.backend.controller;

import lombok.AllArgsConstructor;
import org.resumehub.backend.dto.WorkExperienceDto;
import org.resumehub.backend.service.WorkExperienceService;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin("*")
@AllArgsConstructor
@RestController
@RequestMapping("/api/v1/workExperience/")
public class WorkExperienceController {

    private final WorkExperienceService workExperienceService;

    @GetMapping
    public ResponseEntity<List<WorkExperienceDto>> getAllWorkExperience() {
        return ResponseEntity.ok(workExperienceService.getAllWorkExperience());
    }

    @GetMapping("{id}")
    ResponseEntity<WorkExperienceDto> getWorkExperienceById(@Validated @PathVariable("id") String workExperienceId) {
        return ResponseEntity.ok(workExperienceService.getWorkExperienceById(workExperienceId));
    }

    @PostMapping
    public ResponseEntity<WorkExperienceDto> saveWorkExperience(@Validated @RequestBody WorkExperienceDto workExperience) {
        return ResponseEntity.ok(workExperienceService.saveWorkExperience(workExperience));
    }

    @PutMapping("{id}")
    public ResponseEntity<WorkExperienceDto> updateWorkExperience(@Validated @PathVariable("id") String workExperienceId,
                                                                  @RequestBody WorkExperienceDto updatedWorkExperience) {
        return ResponseEntity.ok(workExperienceService.updateWorkExperience(workExperienceId, updatedWorkExperience));
    }

    @DeleteMapping("{id}")
    public ResponseEntity<String> deleteWorkExperience(@Validated @PathVariable("id") String workExperienceId) {
        workExperienceService.deleteWorkExperience(workExperienceId);
        return ResponseEntity.ok("Work Experience deleted successful");
    }
}
