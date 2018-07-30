package top100.models;

import lombok.Data;

/**
 * Created by chohab on 24/07/2018.
 */
@Data
public class MarketRow {

    private boolean hasShares;
    private Transaction transaction;
    private Company company;
    private int companyId;

    public MarketRow(boolean hasShares, Transaction transaction, int companyId) {
        this.hasShares = hasShares;
        this.transaction = transaction;
        this.companyId = companyId;
    }

    public MarketRow(boolean hasShares, Company company, int companyId) {
        this.hasShares = hasShares;
        this.company = company;
        this.companyId = companyId;
    }
}
