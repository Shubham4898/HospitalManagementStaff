package com.hms.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@RequiredArgsConstructor
@Getter @Setter
public class LoginRequest {
    @NotNull @NotEmpty(message = "userName can't be empty")
    private String userName;
    @NotNull @NotEmpty(message = "Please enter a valid password")
    private String password;
}
