package com.idealist.ecommerce.customer;

import org.springframework.stereotype.Component;

@Component
public class CustomerMapper {

    public Customer toEntity(CustomerRequest request) {
        if (request == null) return null;
        return Customer.builder()
                .id(request.id())
                .firstName(request.firstName())
                .lastName(request.lastName())
                .email(request.email())
                .address(request.address())
                .build();
    }

    public Customer toUpdatedEntity(Customer customer, CustomerRequest request) {
        customer.setAddress(request.address());
        customer.setFirstName(request.firstName());
        customer.setLastName(request.lastName());
        customer.setEmail(request.email());
        return customer;
    }

    public CustomerResponse toResponse(Customer e) {
        return new CustomerResponse(
                e.getId(),
                e.getFirstName(),
                e.getLastName(),
                e.getEmail(),
                e.getAddress()
        );
    }
}
