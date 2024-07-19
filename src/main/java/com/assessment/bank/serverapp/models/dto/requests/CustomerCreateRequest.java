package com.assessment.bank.serverapp.models.dto.requests;

import com.assessment.bank.serverapp.annotations.Unique;
import com.assessment.bank.serverapp.models.Customer;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class CustomerCreateRequest {

    @NotBlank(message = "Full Name cannot be blank!")
    @Size(max = 100, message = "Full Name must be 100 characters or fewer!")
    private String fullName;

    @NotBlank(message = "Address cannot be blank!")
    @Size(max = 100, message = "Address must be 100 characters or fewer!")
    private String address;

    @NotBlank(message = "Place Of Birth cannot be blank!")
    @Size(max = 60, message = "Place Of Birth must be 60 characters or fewer!")
    private String placeOfBirth;

    @NotBlank(message = "Identification Number cannot be blank!")
    @Size(min = 16, max = 16, message = "Identification Number must be 16 characters!")
    @Unique(fieldName = "identificationNumber", domainClass = Customer.class)
    private String identificationNumber;

    @Pattern(regexp = "\\d{4}-\\d{2}-\\d{2}", message = "Date must be in the format yyyy-MM-dd")
    private String dateOfBirth;

    @Size(max = 14, message = "Phone Number must be 14 characters or fewer!")
    private String phoneNumber;

}
