package io.boros.flightright.member.controller;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;
import io.boros.flightright.utils.ValidationGroup;
import lombok.AllArgsConstructor;
import lombok.Value;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;
import javax.validation.constraints.Size;
import java.net.URL;
import java.time.Instant;

@Value
@AllArgsConstructor
@JacksonXmlRootElement(localName = "Member")
public class MemberDTO {

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    String id;

    @Size(min = 1, max = 255, groups = {ValidationGroup.Create.class, ValidationGroup.Update.class})
    @NotNull(groups = {ValidationGroup.Create.class, ValidationGroup.Update.class})
    String firstName;

    @Size(min = 1, max = 255, groups = {ValidationGroup.Create.class, ValidationGroup.Update.class})
    @NotNull(groups = {ValidationGroup.Create.class, ValidationGroup.Update.class})
    String lastName;


    @Size(min = 1, max = 32, groups = ValidationGroup.Create.class)
    @NotNull(groups = {ValidationGroup.Create.class, ValidationGroup.Update.class})
    String zipCode;

    @Past(groups = {ValidationGroup.Create.class, ValidationGroup.Update.class})
    @NotNull(groups = {ValidationGroup.Create.class, ValidationGroup.Update.class})
    Instant dateOfBirth;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    URL image;

    @JsonCreator
    MemberDTO(String firstName, String lastName, String zipCode, Instant dateOfBirth) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.zipCode = zipCode;
        this.dateOfBirth = dateOfBirth;

        this.id = null;
        this.image = null;
    }

}
