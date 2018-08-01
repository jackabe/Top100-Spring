package top100.controllers;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;
import top100.models.Company;
import top100.models.MarketRow;
import top100.models.Trade;
import top100.repository.CompanyRepository;
import top100.service.MarketInterface;
import top100.service.TradeInterface;

import java.util.Arrays;
import java.util.List;

@RequestMapping("/api")
@RestController
public class MarketController {

    private TradeInterface tradeInterface;
    private MarketInterface marketInterface;
    private CompanyRepository companyRepository;

    private String userLoggedIn = "player";

    @Autowired
    public MarketController(TradeInterface tradeInterface, MarketInterface marketInterface, CompanyRepository companyRepository) {
        this.tradeInterface = tradeInterface;
        this.marketInterface = marketInterface;
        this.companyRepository = companyRepository;
    }

    // Create a market of all the shares
    // People can see all companies on the market
    // Shows how many shares each has left
    // Shows the current price (the price is the estimation price) of the shares
    // Market will initially sell the shares at the initial price (RRP)
    // When all are sold out, you then need to buy from other players for whatever price you are willing to pay // they are willing to sell
    // When the end of trading window celebration happens, perform caluclations on all stocks, then update the actual worth of stocks
    // The actual worth will be based on the calculation (unless a special event massively changes it).
    // The person who made the most PROFIT/HAS THE HIGHEST NET WORTH, will win the prize.

    @RequestMapping("/market/all")
    public List<MarketRow> getMarket() {
        return marketInterface.getMarket(userLoggedIn);
    }

    @RequestMapping("/market/finalise")
    public void finaliseMarket() {
    }

    @RequestMapping("/market/trades/all")
    public List<Trade> getMarketOfTrades() {
        return tradeInterface.getMarketOfTrades();
    }

    @RequestMapping("/market/trades/purchase/new")
    public void createPurchaseTrade() {
        this.tradeInterface.createBuyingTrade("player", 1 ,1, 1);
    }

    @RequestMapping("/market/trades/sell/new")
    public void createSellingTrade() {
        this.tradeInterface.createSellingTrade(1,1,1);
    }

    @RequestMapping("/market/calucations/run")
    public void performMarketCalculations() {

        List<Company> companies = marketInterface.getMarketForCalculations();

        for (Company company : companies) {
            company.setTransactions(null);
        }

        Gson gsonBuilder = new GsonBuilder().create();

        String jsonFromJavaArrayList = gsonBuilder.toJson(companies);

        final String request = "http://127.0.0.1:5000/api/flask/market/calculate";

        RestTemplate restTemplate = new RestTemplate();

        HttpHeaders headers = new HttpHeaders();
        headers.set("Accept", MediaType.APPLICATION_JSON_VALUE);

        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(request)
                .queryParam("company-data", jsonFromJavaArrayList);

        HttpEntity<?> entity = new HttpEntity<>(headers);

        HttpEntity<String> response = restTemplate.exchange(
                builder.toUriString(),
                HttpMethod.GET,
                entity,
                String.class);

        String data = response.getBody();
        String trimmedData = data.substring(1, data.length()-2).replaceAll("\\\\","");
        List<String> companyData = Arrays.asList(trimmedData.split("\\s*,\\s*"));

        System.out.println(companyData);

        for (String item : companyData) {
            JSONObject jsonObject = new JSONObject(item);
            for(int i = 0; i<jsonObject.names().length(); i++){
                int companyId = Integer.parseInt(jsonObject.names().getString(i));
                double companyRevenue = Double.parseDouble(jsonObject.get(jsonObject.names().getString(i)).toString());
                Company company = companyRepository.findById(companyId);
                company.setRevenue(companyRevenue);
                company.setSharePrice(companyRevenue / company.getSharesAvailable());
                companyRepository.saveAndFlush(company);
            }
        }
    }

}