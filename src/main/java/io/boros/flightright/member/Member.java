package io.boros.flightright.member;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.net.URL;
import java.time.Instant;

@Document
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Member {

    @Id
    private String id;

    private String firstName;
    private String lastName;
    private Instant dateOfBirth;
    private String zipCode;
    private URL image;

}
