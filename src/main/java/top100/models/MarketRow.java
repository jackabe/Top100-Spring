package top100.models;

/**
 * Created by chohab on 24/07/2018.
 */
public class MarketRow {

    private boolean hasShares;
    private Transaction transaction;
    private Company company;

    public MarketRow(boolean hasShares, Transaction transaction) {
        this.hasShares = hasShares;
        this.transaction = transaction;
    }

    public MarketRow(boolean hasShares, Company company) {
        this.hasShares = hasShares;
        this.company = company;
    }

    public boolean isHasShares() {
        return hasShares;
    }

    public void setHasShares(boolean hasShares) {
        this.hasShares = hasShares;
    }

    public Transaction getTransaction() {
        return transaction;
    }

    public void setTransaction(Transaction transaction) {
        this.transaction = transaction;
    }

    public Company getCompany() {
        return company;
    }

    public void setCompany(Company company) {
        this.company = company;
    }
}
