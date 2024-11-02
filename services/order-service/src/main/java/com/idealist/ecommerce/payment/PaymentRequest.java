package com.idealist.ecommerce.payment;

import com.idealist.ecommerce.customer.CustomerResponse;
import com.idealist.ecommerce.order.PaymentMethod;

import java.math.BigDecimal;

public record PaymentRequest(
        BigDecimal amount,
        PaymentMethod paymentMethod,
        Integer orderId,
        String orderReference,
        CustomerResponse customer
) {
}
