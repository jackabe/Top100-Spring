package top100.models;

/**
 * Created by chohab on 24/07/2018.
 */
public class TransactionForm {

    private int companyId;
    private int numberOfSharesToBuy;
    private double pricePerShare;

    public TransactionForm(int companyId, int numberOfSharesToBuy, double pricePerShare) {
        this.companyId = companyId;
        this.numberOfSharesToBuy = numberOfSharesToBuy;
        this.pricePerShare = pricePerShare;
    }

    public TransactionForm() {
    }

    public int getCompanyId() {
        return companyId;
    }

    public void setCompanyId(int companyId) {
        this.companyId = companyId;
    }

    public int getNumberOfSharesToBuy() {
        return numberOfSharesToBuy;
    }

    public void setNumberOfSharesToBuy(int numberOfSharesToBuy) {
        this.numberOfSharesToBuy = numberOfSharesToBuy;
    }

    public double getPricePerShare() {
        return pricePerShare;
    }

    public void setPricePerShare(double pricePerShare) {
        this.pricePerShare = pricePerShare;
    }
}
