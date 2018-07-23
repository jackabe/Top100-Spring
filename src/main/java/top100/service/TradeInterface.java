package top100.service;

/**
 * Created by chohab on 22/07/2018.
 */
public interface TradeInterface {

    // A player wants to buy a share/s and so will put a request in to one of the players who own the shares to trade
    // Player can make quick transaction if buying share already on market
    // A player may want to sell a share/s so put their shares on the market
    // Completing a trade will change the ownership, change balances etc

    // Needs to have buying player ID and transaction ID
    public void createSellingTrade();

    public void createBuyingTrade();

    public void completeSellingTrade();

    public void completeBuyingTrade();


}
