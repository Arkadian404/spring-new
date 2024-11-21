package org.zafu.spring_new.dto.request;

import java.time.LocalDate;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserProfileRequest {
    Long userId;
    String username;
    String firstname;
    String lastname;
    LocalDate dob;
    String city;
}
