package top100.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import top100.models.Company;
import top100.repository.CompanyRepository;
import top100.repository.TransactionRepository;
import top100.repository.UserRepository;

/**
 * Created by chohab on 22/07/2018.
 */
@RequestMapping("/api")
@RestController
public class CompanyController {

    private UserRepository userRepository;
    private CompanyRepository companyRepository;
    private TransactionRepository transactionRepository;

    private String userLoggedIn = "player";

    @Autowired
    public CompanyController(UserRepository userRepository, CompanyRepository companyRepository, TransactionRepository transactionRepository) {
        this.companyRepository = companyRepository;
        this.userRepository = userRepository;
        this.transactionRepository = transactionRepository;
    }

    @RequestMapping("/companies/shares/total/{id}")
    public int getCompanyAvailableShares(@PathVariable int id) {

        // Check company shares number
        // Temp value of 1
        Company company = companyRepository.findById(id);
        return company.getSharesAvailable();


    }

    @RequestMapping("/companies/company/{id}")
    public Company getCompany(@PathVariable int id) {
        return companyRepository.findById(id);
    }
}
