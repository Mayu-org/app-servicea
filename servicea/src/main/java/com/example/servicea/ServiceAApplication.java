package com.example.servicea;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
public class ServiceAApplication {
    public static void main(String[] args) {
        SpringApplication.run(ServiceAApplication.class, args);
    }
}

@RestController
class ServiceAController {
    private final RestTemplate restTemplate;
    
    @Value("${service-b.url}")
    private String serviceBUrl;

    public ServiceAController(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @GetMapping("/data")
    public String getData() {
        String serviceBResponse = restTemplate.getForObject(serviceBUrl + "/info", String.class);
        return "Data from Service B: " + serviceBResponse;
    }
}
