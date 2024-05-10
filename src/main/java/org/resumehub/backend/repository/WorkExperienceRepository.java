package org.resumehub.backend.repository;

import org.resumehub.backend.entity.WorkExperience;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface WorkExperienceRepository extends MongoRepository<WorkExperience, String> {
}
