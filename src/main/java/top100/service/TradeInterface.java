package top100.service;

import top100.models.Trade;

import java.util.List;

/**
 * Created by chohab on 22/07/2018.
 */
public interface TradeInterface {

    // A player wants to buy a share/s and so will put a request in to one of the players who own the shares to trade
    // Player can make quick transaction if buying share already on market
    // A player may want to sell a share/s so put their shares on the market
    // Completing a trade will change the ownership, change balances etc

    // Needs to have buying player ID and transaction ID
    void createSellingTrade(int transactionId, int sharesToSell, int sharePrice);

    void createBuyingTrade(String buyer, int transactionId, int sharesToSell, int sharePrice);

    void completeTrade(int id);

    void createCounterOffer(int tradeId, int sharesToBuy, int sharePrice);

    List<Trade> getMarketOfTrades();

    void removeTrade(int id);


}
