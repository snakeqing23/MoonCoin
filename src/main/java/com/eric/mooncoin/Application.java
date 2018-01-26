package com.eric.mooncoin;

import com.eric.mooncoin.services.BTCMarketsRequestInterceptor;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.web.client.RestTemplate;

import java.util.Collections;

/**
 * Created by Ericqi on 21/01/2018.
 */
@SpringBootApplication
public class Application {

    @Bean
    public BTCMarketsRequestInterceptor btcMarketsRequestInterceptor() {
        return new BTCMarketsRequestInterceptor();
    }

    @Bean
    public RestTemplate btcMarketsRestTemplate() {
        RestTemplate restTemplate = new RestTemplate();
        restTemplate.setInterceptors(Collections.<ClientHttpRequestInterceptor>singletonList(btcMarketsRequestInterceptor()));
        return restTemplate;
    }

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
}
