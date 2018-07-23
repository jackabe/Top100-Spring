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

    public void createSellingTrade() {
        User user = userRepository.findByUsername("player");
        Transaction transaction = transactionRepository.findAll().get(0);
        Trade trade = new Trade();
        trade.setSellingPlayer(user);
        // No buyer at this time
        trade.setBuyingPlayer(user);
        trade.setTransaction(transaction);
        tradeRepository.saveAndFlush(trade);
    }

    public void createBuyingTrade() {
        User user = userRepository.findByUsername("player");
        Transaction transaction = transactionRepository.findAll().get(0);
        Trade trade = new Trade();
        trade.setBuyingPlayer(user);
        // No seller at this time
        trade.setSellingPlayer(user);
        trade.setTransaction(transaction);
        tradeRepository.saveAndFlush(trade);
    }

    public void completeBuyingTrade() {


    }

    public void completeSellingTrade() {

    }

    public List<Trade> getMarketOfTrades() {
        return tradeRepository.findAll();
    }


    public void removeTrade() {

    }


}
