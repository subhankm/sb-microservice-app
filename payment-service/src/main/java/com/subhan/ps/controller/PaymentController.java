package com.subhan.ps.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.subhan.ps.entity.Payment;
import com.subhan.ps.service.PaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/payment")
public class PaymentController {

    @Autowired
    private PaymentService service;

    @PostMapping("/doPayment")
    public Payment doPayment(@RequestBody Payment payment) throws JsonProcessingException {
        return service.doPayment(payment);
    }

    @GetMapping("/{orderId}")
    public Payment findPaymentHistoryByOrderId(@PathVariable int orderId) throws JsonProcessingException {
        return service.findPaymentHistoryByOrderId(orderId);
    }

    @GetMapping
    public String testAPI(){
        return "PAYMENT SERVICE............";
    }
}
