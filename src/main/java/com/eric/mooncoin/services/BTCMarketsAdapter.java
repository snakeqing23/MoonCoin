package com.eric.mooncoin.services;

import com.eric.mooncoin.CryptoCurrency;
import com.sun.deploy.net.HttpResponse;
import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.Collections;

/**
 * Created by Ericqi on 21/01/2018.
 */
@Service
public class BTCMarketsAdapter implements BTCMarketsConstants {
    private final RestTemplate restTemplate;

    @Autowired
    public BTCMarketsAdapter(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public String getMarketTick(CryptoCurrency currencyA, CryptoCurrency currencyB) throws Exception{
        ResponseEntity<String> responseEntity = restTemplate.getForEntity(
                String.join("", BASEURL, "/market/", currencyA.name(), "/", currencyB.name(), "/tick"),
                String.class
        );
        if (!responseEntity.getStatusCode().is2xxSuccessful()) {
            throw new RuntimeException(responseEntity.getStatusCode().getReasonPhrase());
        }
        return responseEntity.getBody();
    }

}
