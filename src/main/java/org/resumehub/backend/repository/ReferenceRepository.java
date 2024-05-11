package org.resumehub.backend.repository;

import org.resumehub.backend.entity.Reference;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface ReferenceRepository extends MongoRepository<Reference, String> {
}
