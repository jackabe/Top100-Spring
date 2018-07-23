package top100.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import top100.repository.CompanyRepository;
import top100.repository.UserRepository;

@RequestMapping("/api")
@RestController
public class MarketController {

    private UserRepository userRepository;
    private CompanyRepository companyRepository;

    private String userLoggedIn = "player";

    @Autowired
    public MarketController(UserRepository userRepository, CompanyRepository companyRepository) {
        this.companyRepository = companyRepository;
        this.userRepository = userRepository;
    }

    // Create a market of all the shares
    // People can see all companies on the market
    // Shows how many shares each has left
    // Shows the current price (the price is the estimation price) of the shares
    // Market will initially sell the shares at the initial price (RRP)
    // When all are sold out, you then need to buy from other players for whatever price you are willing to pay // they are willing to sell
    // When the end of trading window celebration happens, perform caluclations on all stocks, then update the actual worth of stocks
    // The actual worth will be based on the permutations (unless a special even massively changes it).
    // The person who made the most PROFIT, will win the prize.

    @RequestMapping("/market/finalise")
    public void finaliseMarket() {


    }
}