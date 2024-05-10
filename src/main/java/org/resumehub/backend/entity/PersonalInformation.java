package org.resumehub.backend.entity;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Document(collection = "personalInformation")
public class PersonalInformation {
    @Id
    private String id;
    private String fullName;
    private String position;
    private String company;
    private String city;
    private String province;
    private String country;
    private String mobile;
    private String email;
    private String linkedIn;
}
