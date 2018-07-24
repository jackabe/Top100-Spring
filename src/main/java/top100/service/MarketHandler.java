package top100.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import top100.models.Company;
import top100.models.MarketRow;
import top100.models.Transaction;
import top100.models.User;
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

    public List<MarketRow> getMarket(String username) {
        List<Company> marketList = companyRepository.findAll();
        User user = userRepository.findByUsername(username);
        List<MarketRow> rows = new ArrayList<>();

        for (Company company: marketList) {
            Transaction transaction = transactionRepository.findByUserAndCompany(user, company);
            boolean hasShares = false;
            if (transaction != null) {
                hasShares = true;
            }
            MarketRow row = new MarketRow(hasShares, transaction);
            rows.add(row);
        }

        return rows;
    }

}
