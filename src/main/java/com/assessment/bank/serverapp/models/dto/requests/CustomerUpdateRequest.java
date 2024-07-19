package com.assessment.bank.serverapp.models.dto.requests;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class CustomerUpdateRequest {

    @Size(max = 100, message = "Full Name must be 100 characters or fewer!")
    private String fullName;

    @Size(max = 100, message = "Address must be 100 characters or fewer!")
    private String address;

    @Size(max = 60, message = "Place Of Birth must be 60 characters or fewer!")
    private String placeOfBirth;

    @Size(min = 16, max = 16, message = "Identification Number must be 16 characters!")
    private String identificationNumber;

    @Pattern(regexp = "\\d{4}-\\d{2}-\\d{2}", message = "Date must be in the format yyyy-MM-dd")
    private String dateOfBirth;

    @Size(max = 14, message = "Phone Number must be 14 characters or fewer!")
    private String phoneNumber;

    @Size(min = 3, message = "Username at least 3 character")
    private String username;

    @Size(min = 8, message = "Password at least 8 character")
    private String password;
}
