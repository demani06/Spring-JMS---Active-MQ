package com.deepak.kafkaproducerconsumerexample.controller;

import com.deepak.kafkaproducerconsumerexample.kafka.Sender.Sender;
import com.deepak.kafkaproducerconsumerexample.model.Customer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PublishMessageController {

    @Autowired
    Sender sender;

    @GetMapping("/all")
    public String publishCustomerAsJson(){
        Customer customer1 = new Customer(1, "Deepak", "Emani");
        sender.send(customer1);
        return "Published";
    }
}
