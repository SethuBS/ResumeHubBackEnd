package org.resumehub.backend.repository;

import org.resumehub.backend.entity.Education;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface EducationRepository extends MongoRepository<Education, String> {
}
