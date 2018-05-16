package com.deepak.kafkaproducerconsumerexample.controller;

import com.deepak.kafkaproducerconsumerexample.kafka.Sender.CustomerJSONSender;
import com.deepak.kafkaproducerconsumerexample.model.Customer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.function.Consumer;

@RestController
public class PublishMessageController {

    @Autowired
    CustomerJSONSender sender;

    @Autowired
    private RestTemplate restTemplate;

    @GetMapping("/all")
    public String publishCustomerAsJson(){
        Customer customer1 = new Customer(1L, "Deepak", "Emani");
        sender.send(customer1);
        return "Published";
    }

    @GetMapping("/publishALL")
    public String publishCustomersList(){
        String url = "http://localhost:9999/api/customers/";
     /*   System.out.println("URL" + url);
        Collection<Customer> customerCollection = (Collection<Customer>)restTemplate.getForObject(url, Collection .class);
        System.out.println("RESPONSE= " + customerCollection);

        customerCollection.forEach(new Consumer<Customer>() {
            @Override
            public void accept(Customer customer) {
                System.out.println("customer --"+customer.getFirstName());
                sender.send(customer);
            }
        });
        */
        //Customer[] myModelClasses = restTemplate.postForObject(url,null, Customer[].class);


        ParameterizedTypeReference<List<Customer>> typeRef = new ParameterizedTypeReference<List<Customer>>() {
        };
        ResponseEntity<List<Customer>> responseEntity = restTemplate.exchange(url, HttpMethod.GET, null, typeRef);

        List<Customer> customerCollection = responseEntity.getBody();

        System.out.println(customerCollection);

        customerCollection.forEach(new Consumer<Customer>() {
            @Override
            public void accept(Customer customer) {
                //System.out.println("customer --"+customer.getFirstName());
                sender.send(customer);
            }
        });



        return "Published all Customers to Topic";
    }
}
