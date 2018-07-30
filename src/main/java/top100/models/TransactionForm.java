package top100.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Created by chohab on 24/07/2018.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class TransactionForm {

    private int transactionId;
    private int companyId;
    private int numberOfSharesToBuy;
    private double pricePerShare;
    private String type;

}

