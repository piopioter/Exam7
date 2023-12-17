package com.example.personinfo.people.security;

public class CreateUserCommand {

    private String userName;
    private String email;
    private String password;
    private Role role;

    public CreateUserCommand() {
    }

    public CreateUserCommand(String userName, String email, String password, Role role) {
        this.userName = userName;
        this.email = email;
        this.password = password;
        this.role = role;
    }

    public String getUserName() {
        return userName;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public Role getRole() {
        return role;
    }
}
