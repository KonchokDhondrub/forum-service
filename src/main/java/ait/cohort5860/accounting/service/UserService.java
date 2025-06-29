package ait.cohort5860.accounting.service;

import ait.cohort5860.accounting.dto.*;
import ait.cohort5860.accounting.model.Role;

import java.util.List;

public interface UserService {

    UserDto registerUser(RegisterUserDto user);

    UserDto userLogin(String authorization);

    UserDto deleteUser(String login);

    UserDto updateUser( String login, UpdateUserDto updateUserDto);

    UserDto addRoleToUser(String login, Role[] role);

    UserDto deleteRoleFromUser(String login, Role[] roles);

    void changePassword(String newPassword);

    UserDto getUserByLogin(String login);
}
