package com.subhan.os.controller;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.subhan.os.dto.TransactionRequest;
import com.subhan.os.dto.TransactionResponse;
import com.subhan.os.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/order")
public class OrderController {

    @Autowired
    private OrderService service;

    @PostMapping("/bookOrder")
    public TransactionResponse bookOrder(@RequestBody TransactionRequest request) throws JsonProcessingException {
        return service.saveOrder(request);
    }
    @GetMapping
    public String testAPI()   {
        return "ORDER SERVICE..............";
    }
}
