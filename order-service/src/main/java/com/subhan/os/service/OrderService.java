package com.subhan.os.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.subhan.os.dto.Payment;
import com.subhan.os.dto.TransactionRequest;
import com.subhan.os.dto.TransactionResponse;
import com.subhan.os.entity.Order;
import com.subhan.os.repository.OrderRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class OrderService {

    Logger logger = LoggerFactory.getLogger(OrderService.class);
    @Autowired
    private OrderRepository repository;
    @Autowired
  //  @Lazy
    private RestTemplate template;

    //   @Value("${microservice.payment-service.endpoints.endpoint.uri}")
    //   private String ENDPOINT_URL;
    private String ENDPOINT_URL = "http://localhost:8082/payment/doPayment";

    public TransactionResponse saveOrder(TransactionRequest request) throws JsonProcessingException {
        String response = "";
        Order order = request.getOrder();
        Payment payment = request.getPayment();
        payment.setOrderId(order.getId());
        payment.setAmount(order.getPrice());
        //rest call
        logger.info("Order-Service Request : " + new ObjectMapper().writeValueAsString(request));
        Payment paymentResponse = template.postForObject(ENDPOINT_URL, payment, Payment.class);

        response = paymentResponse.getPaymentStatus().equals("success") ? "payment processing successful and order placed" : "there is a failure in payment api , order added to cart";
        logger.info("Order Service getting Response from Payment-Service : " + new ObjectMapper().writeValueAsString(response));
        repository.save(order);
        return new TransactionResponse(order, paymentResponse.getAmount(), paymentResponse.getTransactionId(), response);
    }
}
