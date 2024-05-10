package org.resumehub.backend.repository;

import org.resumehub.backend.entity.PersonalInformation;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface PersonalInformationRepository extends MongoRepository<PersonalInformation, String> {
    PersonalInformation findByEmail(String email);
}
