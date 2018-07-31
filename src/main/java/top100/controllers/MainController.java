package top100.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import top100.models.Company;
import top100.models.User;
import top100.repository.CompanyRepository;
import top100.repository.UserRepository;

import java.util.List;

@RequestMapping("/api")
@RestController
public class MainController {

    private UserRepository userRepository;
    private CompanyRepository companyRepository;

    private String userLoggedIn = "player";

    @Autowired
    public MainController(UserRepository userRepository, CompanyRepository companyRepository) {
        this.companyRepository = companyRepository;
        this.userRepository = userRepository;
    }

    @RequestMapping("/")
    public String login() {
        return "This is the login/register page";
    }

    @RequestMapping("/users/friends/{username}")
    public List<User> getUserFriends(@PathVariable String username) {
        return userRepository.findAll();
    }

    @RequestMapping("/market/companies")
    public List<Company> getMarketCompanies() {
        return companyRepository.findAll();
    }

    @RequestMapping("/users/player/{username}")
    public User getUserData() {
        return getUser(userLoggedIn);
    }

    @RequestMapping("/users/player/bank")
    public double getUserBank() {
        return userRepository.findByUsername(userLoggedIn).getBank();
    }

    public User getUser(String username) {
        User user = userRepository.findByUsername(userLoggedIn);
        return user;
    }

    public void addUser() {
        User user = new User();
        user.setUsername(userLoggedIn);
        user.setPassword("password");
        user.setAddress("Address");
        user.setBank(500);
        user.setEmail("jackallcock@yahoo.co.uk");
//        user.setCreateDate(new Timestamp(System.currentTimeMillis()));

        userRepository.saveAndFlush(user);
    }





}