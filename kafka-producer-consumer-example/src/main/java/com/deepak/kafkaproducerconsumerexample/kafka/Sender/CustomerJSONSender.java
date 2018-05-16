package com.deepak.kafkaproducerconsumerexample.kafka.Sender;

import com.deepak.kafkaproducerconsumerexample.model.Customer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Service;

@Service
public class CustomerJSONSender {

    private static final Logger LOG = LoggerFactory.getLogger(CustomerJSONSender.class);

    @Autowired
    private KafkaTemplate<String, Customer> kafkaTemplate;

    @Value("${app.topic.example}")
    private String topic;

    public void send(Customer data){
        System.out.println("~~~~~~~~~~~~in Send methood");
        LOG.info("sending data='{}' to topic='{}'", data, topic);

        Message<Customer> message = MessageBuilder
                .withPayload(data)
                .setHeader(KafkaHeaders.TOPIC, topic)
                .build();

        kafkaTemplate.send(message);
    }

}
