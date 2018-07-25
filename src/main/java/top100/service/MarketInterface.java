package top100.service;

import top100.models.MarketRow;
import top100.models.TransactionForm;

import java.util.List;

/**
 * Created by chohab on 22/07/2018.
 */
public interface MarketInterface {

    List<MarketRow> getMarket(String username);

    void addNewTransaction(TransactionForm transactionForm);

    void deleteTransaction(int transactionId);

}
