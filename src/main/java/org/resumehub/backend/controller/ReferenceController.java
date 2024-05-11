package org.resumehub.backend.controller;

import lombok.AllArgsConstructor;
import org.resumehub.backend.dto.ReferenceDto;
import org.resumehub.backend.service.ReferenceService;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin("*")
@AllArgsConstructor
@RestController
@RequestMapping("/api/v1/reference/")
public class ReferenceController {

    private final ReferenceService referenceService;

    @GetMapping
    public ResponseEntity<List<ReferenceDto>> getAllReferences() {
        return ResponseEntity.ok(referenceService.getAllReferences());
    }

    @GetMapping("{id}")
    public ResponseEntity<ReferenceDto> getReferenceById(@Validated @PathVariable("id") String referenceId) {
        return ResponseEntity.ok(referenceService.getReferenceById(referenceId));
    }

    @PostMapping
    public ResponseEntity<ReferenceDto> createNewReference(@Validated @RequestBody ReferenceDto newReference) {
        return ResponseEntity.ok(referenceService.createNewReference(newReference));
    }

    @PutMapping("{id}")
    public ResponseEntity<ReferenceDto> updateReference(@Validated @PathVariable("id") String referenceId,
                                                        @RequestBody ReferenceDto updatedReference) {
        return ResponseEntity.ok(referenceService.updateReference(referenceId, updatedReference));
    }

    @DeleteMapping("{id}")
    public ResponseEntity<String> deleteReference(@Validated @PathVariable("id") String referenceId) {
        referenceService.deleteReference(referenceId);
        return ResponseEntity.ok("Reference deleted successful");
    }
}
