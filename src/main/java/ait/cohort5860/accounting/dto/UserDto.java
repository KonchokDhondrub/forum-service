package ait.cohort5860.accounting.dto;

import ait.cohort5860.accounting.model.Role;
import lombok.*;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserDto {
    private String login;
    private String firstName;
    private String lastName;
    @Singular
    private Role[] roles;
}
