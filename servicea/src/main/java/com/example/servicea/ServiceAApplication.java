package com.example.servicea;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@SpringBootApplication
public class ServiceAApplication {

    public static void main(String[] args) {
        SpringApplication.run(ServiceAApplication.class, args);
    }
}

@RestController
class ServiceAController {

    private final RestTemplate restTemplate;
    private static final Logger logger = LogManager.getLogger(ServiceAController.class);

    @Autowired
    public ServiceAController(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @GetMapping("/data")
    
    public String getData() {
        logger.info("Fetching data from Service B");
        String serviceBResponse = restTemplate.getForObject("http://serviceb.myapp1.svc.cluster.local:8081/info", String.class);
        logger.debug("Received response from Service B: {}", serviceBResponse);
        return "Data from Service B: " + serviceBResponse;
    }
    
    
}
