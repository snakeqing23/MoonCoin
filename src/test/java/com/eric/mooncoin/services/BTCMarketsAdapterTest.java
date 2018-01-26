package com.eric.mooncoin.services;

import com.eric.mooncoin.CryptoCurrency;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class BTCMarketsAdapterTest {
    @Autowired
    private BTCMarketsAdapter btcMarketsAdapter;

    @Test
    public void testGetMarketTick() throws Exception {
        String marketTick = btcMarketsAdapter.getMarketTick(CryptoCurrency.BTC, CryptoCurrency.AUD);
        System.out.println("################################");
        System.out.println(marketTick);
    }

}