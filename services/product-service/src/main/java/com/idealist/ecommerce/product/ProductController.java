package com.idealist.ecommerce.product;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/products")
@RequiredArgsConstructor
public class ProductController {

    private final ProductService service;

    @PostMapping // create product
    public ResponseEntity<Integer> createProduct(@RequestBody @Valid ProductRequest request) {
        return ResponseEntity.ok(service.createProduct(request));
    }

    @PostMapping("/purchase") // purchase product of any amount
    public ResponseEntity<List<ProductPurchaseResponse>> purchaseProducts(@RequestBody List<ProductPurchaseRequest> request) {
        return ResponseEntity.ok(service.purchaseProduct(request));
    }

    @GetMapping("/{productId}") // get by id
    public ResponseEntity<ProductResponse> findById(@PathVariable Integer productId) {
        return ResponseEntity.ok(service.findById(productId));
    }

    @GetMapping // get all
    public ResponseEntity<List<ProductResponse>> findAll() {
        return ResponseEntity.ok(service.findAll());
    }

}
