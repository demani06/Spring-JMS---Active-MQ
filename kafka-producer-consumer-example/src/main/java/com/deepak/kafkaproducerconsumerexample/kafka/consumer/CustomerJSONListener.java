package com.deepak.kafkaproducerconsumerexample.kafka.consumer;

import com.deepak.kafkaproducerconsumerexample.model.Customer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import java.util.concurrent.CountDownLatch;

@Service
public class CustomerJSONListener {

    private static final Logger LOG = LoggerFactory.getLogger(CustomerJSONListener.class);

    @KafkaListener(topics = "${app.topic.example}")
    public void processMessage(Customer customer) {
        System.out.println("received content = " + customer);
        latch.countDown();
    }

    private CountDownLatch latch = new CountDownLatch(1);

    public CountDownLatch getLatch() {
        return latch;
    }

   /* public void receive(@Payload Customer data,
                        @Headers MessageHeaders headers) {

        LOG.info("received data='{}'", data);
        System.out.println("recieved data in listener =>"+data);
        headers.keySet().forEach(key -> {
            LOG.info("{}: {}", key, headers.get(key));
            System.out.println("key="+ key );
            System.out.println("value="+ headers.get(key));

        });
    }*/
}
