package com.idealist.ecommerce.customer;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/customer")
@RequiredArgsConstructor
public class CustomerController {
    private final CustomerService service;

    @PostMapping // create
    public ResponseEntity<String> createCustomer(@RequestBody @Valid CustomerRequest request) {
        return ResponseEntity.ok(service.createCustomer(request));
    }

    @PutMapping // update
    public ResponseEntity<Void> updateCustomer(@RequestBody @Valid CustomerRequest request) {
        service.updateCustomer(request);
        return ResponseEntity.accepted().build();
    }

    @GetMapping // findall
    public ResponseEntity<List<CustomerResponse>> findAll() {
        return ResponseEntity.ok(service.findAll());
    }

    @GetMapping("/exists/{customerId}") // check is customer with given id exists
    public ResponseEntity<Boolean> existsById(@PathVariable String customerId) {
        return ResponseEntity.ok(service.existsById(customerId));
    }

    @GetMapping("/{customerId}") // get customer by id
    public ResponseEntity<CustomerResponse> findById(@PathVariable String customerId) {
        return ResponseEntity.ok(service.findById(customerId));
    }

    @DeleteMapping("/{customerId}") // delete customer by id
    public ResponseEntity<Void> deleteById(@PathVariable String customerId) {
        service.deleteCustomer(customerId);
        return ResponseEntity.accepted().build();
    }
}
