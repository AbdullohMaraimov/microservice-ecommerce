package com.idealist.ecommerce.order;

import com.idealist.ecommerce.customer.CustomerClient;
import com.idealist.ecommerce.customer.CustomerResponse;
import com.idealist.ecommerce.exception.BusinessException;
import com.idealist.ecommerce.kafka.OrderConfirmation;
import com.idealist.ecommerce.kafka.OrderProducer;
import com.idealist.ecommerce.orderline.OrderLineRequest;
import com.idealist.ecommerce.orderline.OrderLineService;
import com.idealist.ecommerce.payment.PaymentClient;
import com.idealist.ecommerce.payment.PaymentRequest;
import com.idealist.ecommerce.product.ProductClient;
import com.idealist.ecommerce.product.PurchaseRequest;
import com.idealist.ecommerce.product.PurchaseResponse;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository repository;
    private final CustomerClient customerClient;
    private final ProductClient productClient;
    private final OrderMapper mapper;
    private final OrderLineService orderLineService;
    private final OrderProducer orderProducer;
    private final PaymentClient paymentClient;

    public Integer createOrder(OrderRequest request) {
        CustomerResponse customerResponse = customerClient.findById(request.customerId())
                .orElseThrow(() -> new BusinessException("Cannot create order:: No customer exists with id %s"
                        .formatted(request.customerId())));

        List<PurchaseResponse> purchaseResponses = productClient.purchaseProducts(request.products());

        var order = repository.save(mapper.toOrder(request));

        for (PurchaseRequest purchaseRequest : request.products()) {
            orderLineService.saveOrderLine(
                    new OrderLineRequest(
                            null,
                            order.getId(),
                            purchaseRequest.productId(),
                            purchaseRequest.quantity()
                    )
            );
        }

        PaymentRequest paymentRequest = new PaymentRequest(
                request.amount(),
                request.paymentMethod(),
                order.getId(),
                order.getReference(),
                customerResponse
        );
        paymentClient.requestOrderPayment(paymentRequest);

        orderProducer.sendOrderConfirmation(
                new OrderConfirmation(
                        request.reference(),
                        request.amount(),
                        request.paymentMethod(),
                        customerResponse,
                        purchaseResponses
                )
        );

        return order.getId();
    }

    public List<OrderResponse> findAll() {
        return repository.findAll().stream().map(mapper::toOrderResponse).toList();
    }

    public OrderResponse findById(Integer orderId) {
        return mapper.toOrderResponse(
                repository.findById(orderId)
                        .orElseThrow(() -> new EntityNotFoundException("Order with id %d not found".formatted(orderId)))
        );
    }

}
