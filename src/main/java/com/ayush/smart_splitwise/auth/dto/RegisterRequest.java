package com.ayush.smart_splitwise.auth.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class RegisterRequest {
    @NotBlank
    private String firstName;

    @NotBlank
    private String lastName;

    @Email
    @NotBlank
    private String email;

    @Size(min = 8)
    private String password;

    @NotBlank
    private String confirmPassword;

    @Pattern(regexp = "^[0-9]{10}$")
    private String phoneNumber;

}
