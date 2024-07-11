package com.assessment.bank.serverapp.models.dto.requests;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class RegistrationRequest {
    @NotBlank
    private String fullName;

    @NotBlank
    private String address;

    @NotBlank
    private String placeOfBirth;

    @NotBlank
    private String identificationNumber;

    @NotBlank
    private String dateOfBirth;

    @NotBlank
    private String phoneNumber;

    @NotBlank
    private String username;

    @NotBlank
    private String password;
}
