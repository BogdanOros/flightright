package io.boros.flightright.member;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Null;
import javax.validation.constraints.Past;
import javax.validation.constraints.Size;
import java.net.URL;
import java.time.Instant;

import static io.boros.flightright.utils.ValidationGroup.Create;
import static io.boros.flightright.utils.ValidationGroup.Update;

@Document("members")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Member {

    @Id
    @Null(groups = Create.class)
    private String id;

    @Size(min = 1, max = 255, groups = {Create.class, Update.class})
    @NotNull(groups = Create.class)
    private String firstName;

    @Size(min = 1, max = 255, groups = {Create.class, Update.class})
    @NotNull(groups = Create.class)
    private String lastName;

    @Past(groups = {Create.class, Update.class})
    @NotNull(groups = Create.class)
    private Instant dateOfBirth;

    @Size(min = 1, max = 32, groups = Create.class)
    @NotNull(groups = Create.class)
    private String zipCode;

    private URL image;

}
