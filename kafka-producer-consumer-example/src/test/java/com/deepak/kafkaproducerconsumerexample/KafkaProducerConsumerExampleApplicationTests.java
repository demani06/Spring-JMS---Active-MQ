package com.deepak.kafkaproducerconsumerexample;

import com.deepak.kafkaproducerconsumerexample.kafka.Sender.CustomerJSONSender;
import com.deepak.kafkaproducerconsumerexample.kafka.consumer.CustomerJSONListener;
import com.deepak.kafkaproducerconsumerexample.model.Customer;
import org.assertj.core.api.Java6Assertions;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.config.KafkaListenerEndpointRegistry;
import org.springframework.kafka.listener.MessageListenerContainer;
import org.springframework.kafka.test.context.EmbeddedKafka;
import org.springframework.kafka.test.rule.KafkaEmbedded;
import org.springframework.kafka.test.utils.ContainerTestUtils;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.concurrent.TimeUnit;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@EnableKafka
@RunWith(SpringRunner.class)
@SpringBootTest
@EmbeddedKafka(topics = "customer.topic", brokerProperties = {"listeners=PLAINTEXT://localhost:9092"})
public class KafkaProducerConsumerExampleApplicationTests {

    @Autowired
    KafkaEmbedded kafkaEmbedded;

	@Autowired
	private CustomerJSONSender customerJSONSender;

	@Autowired
	private CustomerJSONListener customerJSONListener;

	@Autowired
	private KafkaListenerEndpointRegistry kafkaListenerEndpointRegistry;


	@Before
	public void setUp() throws Exception {
		// wait until the partitions are assigned
		for (MessageListenerContainer messageListenerContainer : kafkaListenerEndpointRegistry
				.getListenerContainers()) {
			ContainerTestUtils.waitForAssignment(messageListenerContainer,
					kafkaEmbedded.getPartitionsPerTopic());
		}
	}


	@Test
	public void testRecieve() throws Exception {
		Customer customer = new Customer(3L, "Tom", "Hanks");

		customerJSONSender.send(customer);


		customerJSONListener.getLatch().await(1000, TimeUnit.MILLISECONDS);
		assertThat(customerJSONListener.getLatch().getCount()).isEqualTo(0);

	}
}
