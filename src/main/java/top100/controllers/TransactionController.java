package top100.controllers;

import org.apache.tomcat.util.net.openssl.ciphers.Authentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import top100.models.Transaction;
import top100.models.TransactionForm;
import top100.repository.CompanyRepository;
import top100.repository.TransactionRepository;
import top100.repository.UserRepository;
import top100.service.MarketInterface;

import java.util.List;

@RequestMapping("/api")
@RestController
public class TransactionController {

    private UserRepository userRepository;
    private CompanyRepository companyRepository;
    private TransactionRepository transactionRepository;
    private MarketInterface marketInterface;

    private String userLoggedIn = "player";

    @Autowired
    public TransactionController(UserRepository userRepository, CompanyRepository companyRepository, TransactionRepository transactionRepository, MarketInterface marketInterface) {
        this.companyRepository = companyRepository;
        this.userRepository = userRepository;
        this.transactionRepository = transactionRepository;
        this.marketInterface = marketInterface;
    }

    @RequestMapping("/transactions/player/{username}")
    public List<Transaction> findTransactionsByUsername(@PathVariable String username) {
        return transactionRepository.findAllByUser(userRepository.findByUsername(userLoggedIn));
    }

    @RequestMapping("/transactions/transaction/{id}")
    public Transaction findTransactionsByUsername(@PathVariable int id) {
        return marketInterface.findById(id);
    }

    @RequestMapping(value = "/transactions/player/new", method = RequestMethod.POST)
    public void addNewTransaction(Authentication auth, TransactionForm transactionForm, BindingResult bindingResult, RedirectAttributes attrs,
                                  @RequestParam("companyId") int companyId, @RequestParam("sharePrice") double sharePrice,
                                  @RequestParam("amount") int amount) {
        // Form will have transaction data, e.g. Company, amountToBuy, Price etc

        transactionForm.setCompanyId(companyId);
        transactionForm.setNumberOfSharesToBuy(amount);
        transactionForm.setPricePerShare(sharePrice);
        marketInterface.addNewTransaction(transactionForm);
    }

    @RequestMapping("/transactions/player/delete/{id}")
    public void deleteTransaction(@PathVariable int id) {
        transactionRepository.deleteTransactionById(id);
    }

}