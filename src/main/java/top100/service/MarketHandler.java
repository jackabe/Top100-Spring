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
                row = new MarketRow(true, transaction, company.getId());
            }
            else {
                row = new MarketRow(false, company, company.getId());
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
            transaction.setAmount(transaction.getAmount() + transactionForm.getNumberOfSharesToBuy());
        }
        else {
            transaction = new Transaction();
            transaction.setUser(user);
            transaction.setCompany(company);
            transaction.setAmount(transactionForm.getNumberOfSharesToBuy());
            transaction.setPrice(transactionForm.getPricePerShare());
            transaction.setInitialPrice(transactionForm.getPricePerShare());
        }

        if (transactionForm.getNumberOfSharesToBuy() > company.getSharesAvailable()) {
            String error = "Not enough shares to buy";
        }
        else {
            user.setBank(user.getBank() - (transactionForm.getPricePerShare()*transactionForm.getNumberOfSharesToBuy()));
            userRepository.saveAndFlush(user);
            transactionRepository.saveAndFlush(transaction);
        }

        // Whatever happens we now need to remove shares from company on market
        company.setSharesAvailable(company.getSharesAvailable() - transactionForm.getNumberOfSharesToBuy());
        companyRepository.saveAndFlush(company);
    }

    @Override
    public void sellTransaction(TransactionForm transactionForm) {
        String username = "player";
        String buyingUser = "buyer";
        User seller = userRepository.findByUsername(username);
        User buyer = userRepository.findByUsername(buyingUser);
        Company company = companyRepository.findById(transactionForm.getCompanyId());
        Transaction transaction = transactionRepository.findByUserAndCompany(seller, company);
        Transaction buyerTransaction = transactionRepository.findByUserAndCompany(buyer, company);

        // If seller is selling less shares then they own - keep sellers transaction, modify amount, and create new for buyer
        if (transactionForm.getNumberOfSharesToBuy() < transaction.getAmount()) {
            // If buyer already owns a transaction, do not create a new one, update existing both
            if (buyerTransaction != null) {
                buyerTransaction.setAmount(buyerTransaction.getAmount() + transactionForm.getNumberOfSharesToBuy());
                transaction.setAmount(transaction.getAmount()-transactionForm.getNumberOfSharesToBuy());
                transactionRepository.saveAndFlush(buyerTransaction);
                transactionRepository.saveAndFlush(transaction);
            }
            // Else create new transaction for buyer
            else {
                buyerTransaction = new Transaction();
                buyerTransaction.setUser(buyer);
                buyerTransaction.setCompany(company);
                buyerTransaction.setAmount(transactionForm.getNumberOfSharesToBuy());
                buyerTransaction.setPrice(transactionForm.getPricePerShare());
                buyerTransaction.setInitialPrice(transaction.getInitialPrice());
                // Update sellers transaction amount
                transaction.setAmount(transaction.getAmount()-transactionForm.getNumberOfSharesToBuy());
                transactionRepository.saveAndFlush(buyerTransaction);
                transactionRepository.saveAndFlush(transaction);
            }
        }

        // If seller is selling all, change the owner
        else {
            // If buyer already owns a transaction, do not change owner, delete transaction and update amount
            if (buyerTransaction != null) {
                transactionRepository.delete(transaction);
                buyerTransaction.setAmount(buyerTransaction.getAmount()+transactionForm.getNumberOfSharesToBuy());
                transactionRepository.saveAndFlush(buyerTransaction);
            }
            // Else change owner to buyer
            else {
                transaction.setUser(buyer);
                transactionRepository.saveAndFlush(transaction);
            }
        }

        // Whatever happens above, we do not change the company shares as its just a switch
        seller.setBank(seller.getBank() + (transactionForm.getPricePerShare()*transactionForm.getNumberOfSharesToBuy()));
        userRepository.saveAndFlush(seller);

        // Update buyers bank

    }

    @Override
    public void deleteTransaction(int transactionId) {

    }

    @Override
    public Transaction findById(int id) {
        return transactionRepository.findById(id);
    }

    @Override
    public List<Company> getMarketForCalculations() {
        return companyRepository.findAll();
    }

}
