package org.zafu.spring_new.dto.request;

import java.time.LocalDate;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import org.zafu.spring_new.validator.DobConstraint;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserRequest {
    @Size(min = 5, message = "USERNAME_INVALID")
    String username;

    @Size(min = 8, max = 16, message = "PASSWORD_INVALID")
    String password;
    @Email(message = "INVALID_EMAIL")
    @NotBlank(message = "EMAIL_IS_REQUIRED")
    String email;
    String firstname;
    String lastname;

    @DobConstraint(min = 18, message = "DOB_INVALID")
    LocalDate dob;

    String city;
}
