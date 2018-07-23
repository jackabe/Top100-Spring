//package top100.service;
//
//import top100.models.Trade;
//import top100.models.Transaction;
//import top100.models.User;
//
//import java.util.ArrayList;
//
///**
// * Created by chohab on 23/07/2018.
// */
//public class TradeHandler implements TradeInterface {
//
//    // Need to update from trades in database
//    private ArrayList<Trade> buyingTradeList;
//    private ArrayList<Trade> sellingTradeList;
//    private User user;
//    private Transaction transaction;
//
//    public TradeHandler() {
//        user = new User();
//        transaction = new Transaction();
//        buyingTradeList = new ArrayList<>();
//        sellingTradeList = new ArrayList<>();
//    }
//
//    public void createSellingTrade() {
//        Trade trade = new Trade();
//        trade.setSellingPlayer(user);
//        // No buyer at this time
//        trade.setBuyingPlayer(user);
//        trade.setTransaction(transaction);
//        sellingTradeList.add(trade);
//    }
//
//    public void createBuyingTrade() {
//        Trade trade = new Trade();
//        trade.setBuyingPlayer(user);
//        // No seller at this time
//        trade.setSellingPlayer(user);
//        trade.setTransaction(transaction);
//        buyingTradeList.add(trade);
//
//    }
//
//    public void completeBuyingTrade() {
//
//
//    }
//
//    public void completeSellingTrade() {
//
//    }
//
//}
