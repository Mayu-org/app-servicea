package com.example.servicea;

import io.opentelemetry.api.OpenTelemetry;
import io.opentelemetry.api.trace.Span;
import io.opentelemetry.api.trace.Tracer;
import io.opentelemetry.context.Context;
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
    private final Tracer tracer;
    
    @Value("${service-b.url}")
    private String serviceBUrl;

    public ServiceAController(RestTemplate restTemplate, OpenTelemetry openTelemetry) {
        this.restTemplate = restTemplate;
        this.tracer = openTelemetry.getTracer(ServiceAController.class.getName());
    }

    @GetMapping("/data")
    public String getData() {
        Span span = tracer.spanBuilder("getData").startSpan();
        try (var scope = span.makeCurrent()) {
            String serviceBResponse = restTemplate.getForObject(serviceBUrl + "/info", String.class);
            return "Data from Service B: " + serviceBResponse;
        } finally {
            span.end();
        }
    }
}