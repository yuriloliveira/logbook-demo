package me.yuri.logback_demo.http;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestClient;

@Configuration
public class RestClientConfiguration {
    @Bean
    public RestClient restClient() {
        return RestClient.builder().baseUrl("http://localhost:8080").build();
    }
}
