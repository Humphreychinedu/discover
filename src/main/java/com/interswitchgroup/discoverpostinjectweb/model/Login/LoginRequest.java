package com.interswitchgroup.discoverpostinjectweb.model.Login;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

public class LoginRequest {

    @NotBlank(message = "Email must not be blank")
    @Email
    @Pattern(regexp = ".+@.+\\..+", message = "Please provide a valid email address")
    private String email;

    @NotBlank
    private String password;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
