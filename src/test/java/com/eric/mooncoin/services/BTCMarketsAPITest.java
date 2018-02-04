package com.eric.mooncoin.services;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.knowm.xchange.Exchange;
import org.knowm.xchange.btcmarkets.dto.trade.BTCMarketsTradeHistory;
import org.knowm.xchange.btcmarkets.service.BTCMarketsTradeService;
import org.knowm.xchange.currency.Currency;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.dto.marketdata.OrderBook;
import org.knowm.xchange.dto.marketdata.Ticker;
import org.knowm.xchange.dto.trade.LimitOrder;
import org.knowm.xchange.dto.trade.UserTrade;
import org.knowm.xchange.dto.trade.UserTrades;
import org.knowm.xchange.service.trade.TradeService;
import org.knowm.xchange.service.trade.params.TradeHistoryParams;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class BTCMarketsAPITest {
    @Autowired
    Exchange btcMarketsExchange;
    @Autowired @Qualifier("independentReserveExchange")
    Exchange independentReserveExchange;

    @Test
    public void testReportMarketData() throws Exception {
        Ticker ticker = btcMarketsExchange.getMarketDataService().getTicker(CurrencyPair.BTC_AUD);
        System.out.println("###################");
        System.out.println(ticker);
    }

    @Test
    public void testOrderBook() throws Exception {
        OrderBook orderBook = btcMarketsExchange.getMarketDataService().getOrderBook(CurrencyPair.BTC_AUD);
        print("order book");
        print("asks");
        orderBook.getAsks()
                .stream()
                .forEach(ask -> {
                    print(ask);
                });
        print("bids");
        orderBook.getBids()
                .stream()
                .forEach(bid -> {
                    print(bid);
                });
    }

    @Test
    public void testOrderService() throws Exception {
        TradeService tradeService = btcMarketsExchange.getTradeService();
        BTCMarketsTradeService.HistoryParams tradeHistoryParams = (BTCMarketsTradeService.HistoryParams)tradeService.createTradeHistoryParams();
        tradeHistoryParams.setStartId("0");
        tradeHistoryParams.setCurrencyPair(new CurrencyPair(Currency.XRP, Currency.AUD));
        UserTrades tradeHistory = tradeService.getTradeHistory(tradeHistoryParams);
        List<UserTrade> userTrades = tradeHistory.getUserTrades();
        userTrades.forEach(this::print);
    }

    @Test
    public void testPlaceOrder() throws Exception {
        // find the first bid, add 0.01 AUD
        OrderBook orderBook = btcMarketsExchange.getMarketDataService().getOrderBook(CurrencyPair.ETH_AUD);
        LimitOrder firstBid = orderBook.getBids().stream().findFirst().get();
        print("First bid:");
        print(firstBid);
        TradeService tradeService = btcMarketsExchange.getTradeService();
        LimitOrder order = LimitOrder.Builder
                .from(firstBid)
                .originalAmount(BigDecimal.valueOf(0.0001))
                .limitPrice(firstBid.getLimitPrice().add(BigDecimal.valueOf(0.01d)))
                .build();
        print("Placing order ");
        print(order);
        String response = tradeService.placeLimitOrder(order);
        print(response);

    }

    @Test
    public void testPlaceOrderIndependentReserve() throws Exception {
        // find the first bid, add 0.01 AUD
        OrderBook orderBook = independentReserveExchange.getMarketDataService().getOrderBook(CurrencyPair.ETH_AUD);
        LimitOrder firstBid = orderBook.getBids().stream().findFirst().get();
        print("First bid:");
        print(firstBid);
        TradeService tradeService = independentReserveExchange.getTradeService();
        LimitOrder order = LimitOrder.Builder
                .from(firstBid)
                .originalAmount(BigDecimal.valueOf(0.01))
                .limitPrice(firstBid.getLimitPrice().add(BigDecimal.valueOf(0.01d)))
                .build();
        print("Placing order ");
        print(order);
        String response = tradeService.placeLimitOrder(order);
        print(response);
        tradeService.cancelOrder(response);

    }

    private void print(Object o) {
        System.out.println(o);
    }
}