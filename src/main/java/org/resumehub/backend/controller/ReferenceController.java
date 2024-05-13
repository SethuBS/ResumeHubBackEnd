package org.resumehub.backend.controller;

import lombok.AllArgsConstructor;
import org.resumehub.backend.dto.ReferenceDTO;
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
    public ResponseEntity<List<ReferenceDTO>> getAllReferences() {
        return ResponseEntity.ok(referenceService.getAllReferences());
    }

    @GetMapping("{id}")
    public ResponseEntity<ReferenceDTO> getReferenceById(@Validated @PathVariable("id") String referenceId) {
        return ResponseEntity.ok(referenceService.getReferenceById(referenceId));
    }

    @PostMapping
    public ResponseEntity<ReferenceDTO> createNewReference(@Validated @RequestBody ReferenceDTO newReference) {
        return ResponseEntity.ok(referenceService.createNewReference(newReference));
    }

    @PutMapping("{id}")
    public ResponseEntity<ReferenceDTO> updateReference(@Validated @PathVariable("id") String referenceId,
                                                        @RequestBody ReferenceDTO updatedReference) {
        return ResponseEntity.ok(referenceService.updateReference(referenceId, updatedReference));
    }

    @DeleteMapping("{id}")
    public ResponseEntity<String> deleteReference(@Validated @PathVariable("id") String referenceId) {
        referenceService.deleteReference(referenceId);
        return ResponseEntity.ok("Reference deleted successful");
    }
}
