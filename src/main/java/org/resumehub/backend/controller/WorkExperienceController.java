package org.resumehub.backend.controller;

import lombok.AllArgsConstructor;
import org.resumehub.backend.dto.WorkExperienceDTO;
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
    public ResponseEntity<List<WorkExperienceDTO>> getAllWorkExperience(@RequestHeader("Authorization") String jwt) {
        return ResponseEntity.ok(workExperienceService.getAllWorkExperience());
    }

    @GetMapping("{id}")
    ResponseEntity<WorkExperienceDTO> getWorkExperienceById(@Validated @RequestHeader("Authorization") String jwt,
                                                            @PathVariable("id") String workExperienceId) {
        return ResponseEntity.ok(workExperienceService.getWorkExperienceById(workExperienceId));
    }

    @PostMapping
    public ResponseEntity<WorkExperienceDTO> saveWorkExperience(@Validated @RequestHeader("Authorization") String jwt,
                                                                @RequestBody WorkExperienceDTO workExperience) {
        return ResponseEntity.ok(workExperienceService.saveWorkExperience(workExperience));
    }

    @PutMapping("{id}")
    public ResponseEntity<WorkExperienceDTO> updateWorkExperience(@Validated @RequestHeader("Authorization") String jwt,
                                                                  @PathVariable("id") String workExperienceId,
                                                                  @RequestBody WorkExperienceDTO updatedWorkExperience) {
        return ResponseEntity.ok(workExperienceService.updateWorkExperience(workExperienceId, updatedWorkExperience));
    }

    @DeleteMapping("{id}")
    public ResponseEntity<String> deleteWorkExperience(@Validated @RequestHeader("Authorization") String jwt,
                                                       @PathVariable("id") String workExperienceId) {
        workExperienceService.deleteWorkExperience(workExperienceId);
        return ResponseEntity.ok("Work Experience deleted successful");
    }
}
