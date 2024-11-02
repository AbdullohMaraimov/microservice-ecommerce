package com.idealist.ecommerce.customer;

import com.idealist.ecommerce.exception.CustomerNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CustomerService {

    private final CustomerRepository repository;
    private final CustomerMapper mapper;

    public String createCustomer(CustomerRequest request) {
        var customer = repository.save(mapper.toEntity(request));
        return customer.getId();
    }

    public void updateCustomer(CustomerRequest request) {
        var customer = repository.findById(request.id())
                .orElseThrow(() -> new CustomerNotFoundException("Customer not found with id: %s".formatted(request.id())));
        Customer newCustomer = mapper.toUpdatedEntity(customer, request);
        repository.save(newCustomer);
    }

    public List<CustomerResponse> findAll() {
        return repository.findAll().stream().map(mapper::toResponse).toList();
    }

    public Boolean existsById(String customerId) {
        return repository.findById(customerId).isPresent();
    }

    public CustomerResponse findById(String customerId) {
        return mapper.toResponse(repository.findById(customerId)
                .orElseThrow(() -> new CustomerNotFoundException("Customer with id %s not found".formatted(customerId))));
    }

    public void deleteCustomer(String customerId) {
        repository.deleteById(customerId);
    }
}
