package com.idealist.paymentservice.payment;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;

public record Customer(
        String id,
        @NotNull(message = "First name is required")
        String firstName,
        @NotNull(message = "Last name is required")
        String lastName,
        @NotNull(message = "Email is required")
        @Email(message = "Customer email is invalid")
        String email
) {
}
