package top100.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import top100.models.Company;
import top100.repository.CompanyRepository;

import java.util.List;

/**
 * Created by chohab on 23/07/2018.
 */
@Service
public class MarketHandler implements MarketInterface {

    private CompanyRepository companyRepository;

    @Autowired
    public MarketHandler(CompanyRepository companyRepository) {
        this.companyRepository = companyRepository;
    }

    public List<Company> getMarket() {
        return companyRepository.findAll();
    }

}
