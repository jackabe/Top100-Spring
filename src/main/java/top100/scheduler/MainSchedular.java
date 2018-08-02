package top100.scheduler;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;
import top100.models.Company;
import top100.models.CompanyPrice;
import top100.models.Transaction;
import top100.models.TransactionDTO;
import top100.repository.CompanyRepository;
import top100.service.MarketInterface;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Transactional
@Component
public class MainSchedular {

    private MarketInterface marketInterface;
    private CompanyRepository companyRepository;

    @Autowired
    public MainSchedular(MarketInterface marketInterface, CompanyRepository companyRepository) {
        this.marketInterface = marketInterface;
        this.companyRepository = companyRepository;
    }

    @Scheduled(fixedRate = 50000)
    public void updateMarketPrices() {

        List<Company> companies = marketInterface.getMarketForCalculations();
        List<CompanyPrice> companyDataToSend = new ArrayList<>();

        for (Company company : companies) {
            List<Transaction> transactions = company.getTransactions();
            List<TransactionDTO> transactionDTOs = new ArrayList<>();
            for (Transaction transaction : transactions) {
                TransactionDTO transactionDTO = new TransactionDTO(transaction.getInitialPrice(), transaction.getPrice());
                transactionDTOs.add(transactionDTO);
            }
            CompanyPrice companyPrice = new CompanyPrice(company.getId(), transactionDTOs, company.getSharePrice());
            companyDataToSend.add(companyPrice);
        }

        Gson gsonBuilder = new GsonBuilder().create();

        String jsonFromJavaArrayList = gsonBuilder.toJson(companyDataToSend);

        final String request = "http://127.0.0.1:5000/api/flask/market/prices/update";

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

        for (String item : companyData) {
            JSONObject jsonObject = new JSONObject(item);
            for(int i = 0; i<jsonObject.names().length(); i++){
                int companyId = Integer.parseInt(jsonObject.names().getString(i));
                String priceChange = jsonObject.get(jsonObject.names().getString(i)).toString();
                Company company = companyRepository.findById(companyId);
                company.setPriceChange(priceChange);
                companyRepository.saveAndFlush(company);
            }
        }
    }
}

