package ait.cohort5860.accounting.controller;

import ait.cohort5860.accounting.dto.*;
import ait.cohort5860.accounting.model.Role;
import ait.cohort5860.accounting.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @PostMapping("/register")
    public UserDto registerUser(@RequestBody RegisterUserDto user) {
        return userService.registerUser(user);
    }

    @PostMapping("/login")
    public UserDto userLogin(@RequestHeader("Authorization") String authorization) {
        return userService.userLogin(authorization);
    }

    @DeleteMapping("/user/{login}")
    public UserDto deleteUser(@PathVariable String login) {
        return userService.deleteUser(login);
    }

    @PatchMapping("/user/{login}")
    public UserDto updateUser(@PathVariable String login, @RequestBody UpdateUserDto updateUserDto) {
        return userService.updateUser(login, updateUserDto);
    }

    @PatchMapping("/user/{login}/role/{role}")
    public UserDto addRoleToUser(@PathVariable String login, @PathVariable("role") Role[] role) {
        return userService.addRoleToUser(login, role);
    }

    @DeleteMapping("/user/{login}/role/{role}")
    public UserDto deleteRoleFromUser(@PathVariable String login, @PathVariable Role[] role) {
        return userService.deleteRoleFromUser(login, role);
    }

    @PatchMapping("/password")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void changePassword(@RequestParam("X-Password") String newPassword) {
        userService.changePassword(newPassword);
    }

    @GetMapping("/user/{login}")
    public UserDto getUserByLogin(@PathVariable String login) {
        return userService.getUserByLogin(login);
    }
}
