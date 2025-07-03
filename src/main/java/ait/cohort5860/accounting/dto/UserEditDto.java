package ait.cohort5860.accounting.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;

@Getter
public class UserEditDto {
    @Size(min = 1, max = 20, message = "First name must be between 1 and 20 characters")
    private String firstName;

    @Size(min = 1, max = 20, message = "Last name must be between 1 and 20 characters")
    private String lastName;
}
