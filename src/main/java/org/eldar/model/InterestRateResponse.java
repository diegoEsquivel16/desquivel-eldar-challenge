package org.eldar.model;

public class InterestRateResponse {

    private Double interestRate;

    public InterestRateResponse(Double interestRate) {
        this.interestRate = interestRate;
    }

    public Double getInterestRate() {
        return interestRate;
    }

    public void setInterestRate(Double interestRate) {
        this.interestRate = interestRate;
    }
}
