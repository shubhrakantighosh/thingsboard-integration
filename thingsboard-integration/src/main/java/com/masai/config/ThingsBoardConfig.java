package com.masai.config;


import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.client.WebClient;
import java.util.List;

@Configuration
public class ThingsBoardConfig {

    @Value("${thingsboard.url}")
    private String thingsBoardUrl;

    @Value("${thingsboard.username}")
    private String username;

    @Value("${thingsboard.password}")
    private String password;

    @Bean
    public WebClient thingsBoardWebClient() {
        return WebClient.builder()
                .baseUrl(thingsBoardUrl)
                .defaultHeaders(headers -> {
                    headers.setBasicAuth(username, password);
                    headers.setContentType(MediaType.APPLICATION_JSON);
                    headers.setAccept(List.of(MediaType.APPLICATION_JSON));
                })
                .build();
    }
}