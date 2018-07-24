package top100.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import top100.models.MarketRow;
import top100.models.Trade;
import top100.service.MarketInterface;
import top100.service.TradeInterface;

import java.util.List;

@RequestMapping("/api")
@RestController
public class MarketController {

    private TradeInterface tradeInterface;
    private MarketInterface marketInterface;

    private String userLoggedIn = "player";

    @Autowired
    public MarketController(TradeInterface tradeInterface, MarketInterface marketInterface) {
        this.tradeInterface = tradeInterface;
        this.marketInterface = marketInterface;
    }

    // Create a market of all the shares
    // People can see all companies on the market
    // Shows how many shares each has left
    // Shows the current price (the price is the estimation price) of the shares
    // Market will initially sell the shares at the initial price (RRP)
    // When all are sold out, you then need to buy from other players for whatever price you are willing to pay // they are willing to sell
    // When the end of trading window celebration happens, perform caluclations on all stocks, then update the actual worth of stocks
    // The actual worth will be based on the calculation (unless a special event massively changes it).
    // The person who made the most PROFIT/HAS THE HIGHEST NET WORTH, will win the prize.

    @RequestMapping("/market/all")
    public List<MarketRow> getMarket() {
        return marketInterface.getMarket(userLoggedIn);
    }

    @RequestMapping("/market/finalise")
    public void finaliseMarket() {

    }

    @RequestMapping("/market/trades/all")
    public List<Trade> getMarketOfTrades() {
        return tradeInterface.getMarketOfTrades();
    }

    @RequestMapping("/market/trades/purchase/new")
    public void createPurchaseTrade() {
        this.tradeInterface.createBuyingTrade("player", 1 ,1, 1);
    }

    @RequestMapping("/market/trades/sell/new")
    public void createSellingTrade() {
        this.tradeInterface.createSellingTrade(1,1,1);
    }

}