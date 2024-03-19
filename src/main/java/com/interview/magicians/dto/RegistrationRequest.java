package com.interview.magicians.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;

@Data
@AllArgsConstructor
public class RegistrationRequest {
    @NotBlank
    private final String username;
    @NotBlank
    private final String password;
}
