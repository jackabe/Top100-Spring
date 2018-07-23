package top100.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import top100.models.Trade;
import top100.models.Transaction;
import top100.models.User;
import top100.repository.TradeRepository;
import top100.repository.TransactionRepository;
import top100.repository.UserRepository;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by chohab on 23/07/2018.
 */
@Service
public class TradeHandler implements TradeInterface {

    // Need to update from trades in database
    private ArrayList<Trade> buyingTradeList;
    private ArrayList<Trade> sellingTradeList;
    private TradeRepository tradeRepository;
    private UserRepository userRepository;
    private TransactionRepository transactionRepository;


    @Autowired
    public TradeHandler(TradeRepository tradeRepository, UserRepository userRepository, TransactionRepository transactionRepository) {
        this.tradeRepository = tradeRepository;
        this.userRepository = userRepository;
        this.transactionRepository = transactionRepository;
    }

    public void createSellingTrade(int transactionId, int sharesToSell, int sharePrice) {
        Transaction transaction = transactionRepository.findById(transactionId);
        Trade trade = new Trade();
        trade.setSellingPlayer(transaction.getUser());
        trade.setType("Sell");
        trade.setStatus("Pending");
        // No buyer at this time
        trade.setTransaction(transaction);
        tradeRepository.saveAndFlush(trade);
    }

    public void createBuyingTrade(String buyer, int transactionId, int sharesToBuy, int sharePrice) {
        User buyingPlayer = userRepository.findByUsername(buyer);
        Transaction transaction = transactionRepository.findById(transactionId);
        Trade trade = new Trade();
        trade.setBuyingPlayer(buyingPlayer);
        trade.setType("Buy");
        trade.setStatus("Pending");
        trade.setTransaction(transaction);
        tradeRepository.saveAndFlush(trade);
    }

    public void createCounterOffer(int tradeId, int sharesToBuy, int sharePrice) {
        Trade trade = tradeRepository.findById(tradeId);
        trade.setStatus("Countered");
        trade.setAmount(sharesToBuy);
        trade.setSharePrice(sharePrice);
        tradeRepository.saveAndFlush(trade);
    }

    // Once someone has posted a buy trade, inform those who owns shares that someone is looking to sell
    // Now a seller can view the trade and either accept/decline/counter

    public void completeTrade(int id) {
        Trade trade = tradeRepository.findById(id);
        Transaction transaction = trade.getTransaction();
        // Change the owner of that transaction to the buyer - they now own it
        transaction.setUser(trade.getBuyingPlayer());
        // Update bank accounts for seller and buyer
        User buyer = trade.getBuyingPlayer();
        User seller = trade.getSellingPlayer();
        double tradeTotalAmount = trade.getAmount()*trade.getSharePrice();
        buyer.setBank(buyer.getBank() - tradeTotalAmount);
        seller.setBank(seller.getBank() + tradeTotalAmount);
        // Update status
        trade.setStatus("Complete");
        userRepository.saveAndFlush(buyer);
        userRepository.saveAndFlush(seller);
    }

    public List<Trade> getMarketOfTrades() {
        return tradeRepository.findAll();
    }


    public void removeTrade(int id) {

    }


}
