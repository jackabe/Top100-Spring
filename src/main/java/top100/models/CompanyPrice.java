package top100.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * Created by chohab on 01/08/2018.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CompanyPrice {

    private int companyId;
    private List<TransactionDTO> transactions;
    private double sharePrice;

}
