package com.akhan.simpleservice;

import java.util.concurrent.atomic.AtomicLong;

import org.apache.activemq.ActiveMQConnectionFactory;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.server.ResponseStatusException;

import javax.jms.*;

@RestController
public class HttpController {

    private static final String restTemplateUrl = "http://localhost:8181/customer";
    private static final String restTemplateUrlPhone = "http://localhost:8181/customer/phone/";

    private final AtomicLong counter = new AtomicLong();
    private static final Logger logger = LogManager.getLogger(HttpController.class);

    @Autowired
    private RestTemplate restTemplate;


    @PostMapping(value="/customer", consumes = MediaType.APPLICATION_JSON_VALUE)
    public Consumer customer(@RequestBody Consumer customer) {

        logger.info("Entered customer API POST method : {} ");

       // RestTemplate restTemplate = new RestTemplate();

        logger.debug("using DAO endpoint customer API POST method : ", restTemplateUrl);

        Consumer consumer = restTemplate.postForObject(restTemplateUrl,customer,
                Consumer.class);
        logger.info("completed POST customer API call ");

        logger.info("Succesfully Created customer : {} " + customer.getFirstName());
        return consumer;
    }

    @RequestMapping("/customer/phone/{phone}")
    public Consumer find(@PathVariable(value = "phone") String phone)  {

        logger.info("Entered customer API GET method : /phone "  + phone);
        //RestTemplate restTemplate = new RestTemplate();

        Consumer consumer = restTemplate
                .getForObject(restTemplateUrlPhone  + phone, Consumer.class);

        logger.info("Succesfully found customer : {} " + phone);

        return consumer;
    }








    }
