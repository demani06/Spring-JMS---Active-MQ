package com.deepak.kafkaproducerconsumerexample;

import com.deepak.kafkaproducerconsumerexample.kafka.Sender.Sender;
import com.deepak.kafkaproducerconsumerexample.model.Customer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class KafkaProducerConsumerExampleApplication implements CommandLineRunner {

	public static void main(String[] args) {
		SpringApplication.run(KafkaProducerConsumerExampleApplication.class, args);
	}

	@Autowired
	private Sender sender;

	@Override
	public void run(String... args) throws Exception {
		Customer customer = new Customer(2, "Sylvester", "Stallone");
		sender.send(customer);
	}
}
