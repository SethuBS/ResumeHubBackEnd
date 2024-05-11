package org.resumehub.backend.entity;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Document(collection = "reference")
public class Reference {
    @Id
    private String id;
    private String userId;
    private String name;
    private String company;
    private String email;
    private String mobile;
}
