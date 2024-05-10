package org.resumehub.backend.entity;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Document(collection = "workExperience")
public class WorkExperience {

    @Id
    private String id;
    private String companyName;
    private String position;
    private String startDate;
    private String endDate;
    private String city;
    private String province;
    private String country;
    private List<String> responsibilities;
}
