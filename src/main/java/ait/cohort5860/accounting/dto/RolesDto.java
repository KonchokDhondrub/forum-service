package ait.cohort5860.accounting.dto;

import lombok.*;

import java.util.Set;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RolesDto {
    private String login;

    @Singular
    private Set<String> roles;
}