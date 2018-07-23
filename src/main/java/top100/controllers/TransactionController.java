package top100.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import top100.models.Company;
import top100.models.Transaction;
import top100.repository.CompanyRepository;
import top100.repository.TransactionRepository;
import top100.repository.UserRepository;

import java.util.List;

@RequestMapping("/api")
@RestController
public class TransactionController {

    private UserRepository userRepository;
    private CompanyRepository companyRepository;
    private TransactionRepository transactionRepository;

    private String userLoggedIn = "player";

    @Autowired
    public TransactionController(UserRepository userRepository, CompanyRepository companyRepository, TransactionRepository transactionRepository) {
        this.companyRepository = companyRepository;
        this.userRepository = userRepository;
        this.transactionRepository = transactionRepository;
    }

    @RequestMapping("/transactions/player/{username}")
    public List<Transaction> findTransactionsByUsername(@PathVariable String username) {
        return transactionRepository.findAllByUser(userRepository.findByUsername(userLoggedIn));
    }

    @RequestMapping("/transactions/player/new/{username}")
    public void addNewTransaction(@PathVariable String username) {

        // Check company shares number
        // Temp value of 1
        Company company = companyRepository.findById(1);

        int numberOfSharesPlayerWants = 500;

        if (company.getSharesAvailable() >= numberOfSharesPlayerWants) {
            Transaction transaction = new Transaction();
            transaction.setUser(userRepository.findByUsername(username));
            transaction.setCompany(companyRepository.findById(1));
            transaction.setAmount(500);
            transaction.setPrice(12);
            transactionRepository.saveAndFlush(transaction);
        }
        else {
//            return "There are not enough shares available to perform this transaction"
        }
    }

    @RequestMapping("/transactions/player/delete/{id}")
    public void deleteTransaction(@PathVariable int id) {
        transactionRepository.deleteTransactionById(id);
    }

}