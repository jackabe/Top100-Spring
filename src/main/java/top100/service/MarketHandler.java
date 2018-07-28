package top100.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import top100.models.*;
import top100.repository.CompanyRepository;
import top100.repository.TransactionRepository;
import top100.repository.UserRepository;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by chohab on 23/07/2018.
 */
@Service
public class MarketHandler implements MarketInterface {

    private CompanyRepository companyRepository;
    private TransactionRepository transactionRepository;
    private UserRepository userRepository;

    @Autowired
    public MarketHandler(CompanyRepository companyRepository, TransactionRepository transactionRepository, UserRepository userRepository) {
        this.companyRepository = companyRepository;
        this.transactionRepository = transactionRepository;
        this.userRepository = userRepository;

    }

    @Override
    public List<MarketRow> getMarket(String username) {
        List<Company> marketList = companyRepository.findAll();
        User user = userRepository.findByUsername(username);
        List<MarketRow> rows = new ArrayList<>();

        for (Company company: marketList) {
            Transaction transaction = transactionRepository.findByUserAndCompany(user, company);
            MarketRow row;
            if (transaction != null) {
                row = new MarketRow(true, transaction);
            }
            else {
                row = new MarketRow(false, company);
            }
            rows.add(row);
        }

        return rows;
    }

    @Override
    public void addNewTransaction(TransactionForm transactionForm) {
        String username = "player";
        User user = userRepository.findByUsername(username);
        Company company = companyRepository.findById(transactionForm.getCompanyId());
        Transaction transaction = transactionRepository.findByUserAndCompany(user, company);
        if (transaction != null) {
            transaction.setAmount(transactionForm.getNumberOfSharesToBuy());
        }
        else {
            transaction = new Transaction();
            transaction.setUser(user);
            transaction.setCompany(company);
            transaction.setAmount(transactionForm.getNumberOfSharesToBuy());
            transaction.setPrice(transactionForm.getPricePerShare());
        }

        if (transactionForm.getNumberOfSharesToBuy() > company.getSharesAvailable()) {
            String error = "Not enough shares to buy";
        }
        else {
            transactionRepository.saveAndFlush(transaction);
        }
    }

    @Override
    public void deleteTransaction(int transactionId) {

    }

    @Override
    public Transaction findById(int id) {
        return transactionRepository.findById(id);
    }

}
