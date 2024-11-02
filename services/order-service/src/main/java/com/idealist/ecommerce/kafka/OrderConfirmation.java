package com.idealist.ecommerce.kafka;

import com.idealist.ecommerce.customer.CustomerResponse;
import com.idealist.ecommerce.order.PaymentMethod;
import com.idealist.ecommerce.product.PurchaseResponse;

import java.math.BigDecimal;
import java.util.List;

public record OrderConfirmation(
        String orderReference,
        BigDecimal totalAmount,
        PaymentMethod paymentMethod,
        CustomerResponse customer,
        List<PurchaseResponse> products
) {
}
