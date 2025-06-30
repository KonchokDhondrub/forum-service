package ait.cohort5860.accounting.service;

import ait.cohort5860.accounting.dto.*;

public interface UserAccountService {

    UserDto registerUser(UserRegisterDto user);

    UserDto getUser(String login);

    UserDto deleteUser(String login);

    UserDto updateUser( String login, UserEditDto updateUserDto);

    RolesDto changeRolesList(String login, String role, boolean isAddRole);

    void changePassword(String login, String newPassword);

    UserDto getUserByLogin(String login);
}
