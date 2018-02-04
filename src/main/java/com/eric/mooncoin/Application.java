package com.eric.mooncoin;

import org.knowm.xchange.Exchange;
import org.knowm.xchange.ExchangeFactory;
import org.knowm.xchange.ExchangeSpecification;
import org.knowm.xchange.btcmarkets.BTCMarketsExchange;
import org.knowm.xchange.independentreserve.IndependentReserve;
import org.knowm.xchange.independentreserve.IndependentReserveExchange;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;


/**
 * Created by Ericqi on 21/01/2018.
 */
@SpringBootApplication
public class Application {

    @Value("${btc.markets.api.key}")
    private String btcMarketsApiKey;
    @Value("${btc.markets.private.key}")
    private String btcMarketsPrivateKey;
    @Value("${independent.reserve.api.key}")
    private String independentReserveApiKey;
    @Value("${independent.reserve.private.key}")
    private String independentReservePrivateKey;

    @Bean("btcMarketsExchange")
    public Exchange btcMarketsExchange() {
        ExchangeSpecification spec = new BTCMarketsExchange().getDefaultExchangeSpecification();
        spec.setUserName("audrey");
        spec.setApiKey(btcMarketsApiKey);
        spec.setSecretKey(btcMarketsPrivateKey);
        spec.setShouldLoadRemoteMetaData(false);
        spec.setMetaDataJsonFileOverride("btcmarkets-override.json");
        return ExchangeFactory.INSTANCE.createExchange(spec);
    }

    @Bean("independentReserveExchange")
    public Exchange independentReserveExchange() {
        ExchangeSpecification spec = new IndependentReserveExchange().getDefaultExchangeSpecification();
        spec.setUserName("audrey");
        spec.setApiKey(independentReserveApiKey);
        spec.setSecretKey(independentReservePrivateKey);
        return ExchangeFactory.INSTANCE.createExchange(spec);
    }

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
}
