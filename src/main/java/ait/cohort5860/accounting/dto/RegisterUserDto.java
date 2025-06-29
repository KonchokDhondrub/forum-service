package ait.cohort5860.accounting.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class RegisterUserDto {
    private String login;
    private String password;
    private String firstName;
    private String lastName;
}