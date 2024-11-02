package com.idealist.ecommerce.product;

import com.idealist.ecommerce.exception.ProductPurchaseException;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository repository;
    private final ProductMapper mapper;

    public Integer createProduct(@Valid ProductRequest request) {
        Product product = mapper.toEntity(request);
        return repository.save(product).getId();
    }

    public List<ProductPurchaseResponse> purchaseProduct(List<ProductPurchaseRequest> request) {
        List<Integer> productIds = request.stream()
                .map(ProductPurchaseRequest::productId).toList();
        List<Product> storedProducts = repository.findAllByIdInOrderById(productIds);
        if (productIds.size() != storedProducts.size()) {
            throw new ProductPurchaseException("One or more products does not exist");
        }
        List<ProductPurchaseRequest> requestProductIds = request.stream()
                .sorted(Comparator.comparing(ProductPurchaseRequest::productId)).toList();

        List<ProductPurchaseResponse> purchasedProducts = new ArrayList<>();
        for (int i = 0; i < storedProducts.size(); i++) {
            Product product = storedProducts.get(i);
            ProductPurchaseRequest productPurchaseRequest = requestProductIds.get(i);
            if (product.getAvailableQuantity() < productPurchaseRequest.quantity()) {
                throw new ProductPurchaseException("Insufficient stock quantity for the product with id %s".formatted(product.getId()));
            }
            product.setAvailableQuantity(product.getAvailableQuantity() - productPurchaseRequest.quantity());
            repository.save(product);
            purchasedProducts.add(mapper.toProductPurchaseResponse(product, productPurchaseRequest.quantity()));
        }
        return purchasedProducts;
    }

    public ProductResponse findById(Integer productId) {
        return repository.findById(productId)
                .map(mapper::toResponse)
                .orElseThrow(() -> new EntityNotFoundException("Product with id %s not found".formatted(productId)));
    }

    public List<ProductResponse> findAll() {
        return repository.findAll().stream().map(mapper::toResponse).toList();
    }
}
