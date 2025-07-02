package ait.cohort5860.accounting.controller;

import ait.cohort5860.accounting.dto.*;
import ait.cohort5860.accounting.service.UserAccountService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.server.ResponseStatusException;

import java.security.Principal;

@RestController
@RequestMapping("/account")
@RequiredArgsConstructor
public class UserAccountController {
    private final UserAccountService userAccountService;

    @PostMapping("/register")
    public UserDto registerUser(@RequestBody @Valid UserRegisterDto user) {
        return userAccountService.registerUser(user);
    }

    @PostMapping("/login")
    public UserDto userLogin(Principal principal) {
        return userAccountService.getUser(principal.getName());
    }

    @DeleteMapping("/user/{login}")
    public UserDto deleteUser(@PathVariable String login) {
        if (login.isBlank()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "User name is required");
        }
        return userAccountService.deleteUser(login);
    }

    @PatchMapping("/user/{login}")
    public UserDto updateUser(@PathVariable String login, @RequestBody @Valid UserEditDto userEditDto) {
        return userAccountService.updateUser(login, userEditDto);
    }

    @PatchMapping("/user/{login}/role/{role}")
    public RolesDto addRoleToUser(@PathVariable String login, @PathVariable String role) {
        return userAccountService.changeRolesList(login, role, true);
    }

    @DeleteMapping("/user/{login}/role/{role}")
    public RolesDto deleteRoleFromUser(@PathVariable String login, @PathVariable String role) {
        return userAccountService.changeRolesList(login, role, false);
    }

    @PatchMapping("/password")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void changePassword(Principal principal, @RequestHeader("X-Password") String newPassword) {
        if (newPassword.isBlank()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Password is required");
        }
        if (newPassword.length() < 4 || newPassword.length() > 20) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Password must be between 4 and 20 characters");
        }
        userAccountService.changePassword(principal.getName(), newPassword);
    }

    @GetMapping("/user/{login}")
    public UserDto getUserByLogin(@PathVariable String login) {
        if (login.isBlank()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "User name is required");
        }
        return userAccountService.getUserByLogin(login);
    }
}
