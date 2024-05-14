package org.eldar.service;

import org.eldar.exception.CreditCardRepositoryException;
import org.eldar.exception.InvalidOperationCause;
import org.eldar.exception.InvalidOperationException;
import org.eldar.model.Brand;
import org.eldar.model.CreditCard;
import org.eldar.repository.CreditCardsRepository;
import org.eldar.util.CreditCardCreator;
import org.eldar.util.DateUtil;

import java.util.Calendar;

public class CreditCardService {

    private final Integer MAX_OPERATION_VALUE = 1000;
    private final Double MIN_INTEREST_RATE = 0.3;
    private final Double MAX_INTEREST_RATE = 5.0;

    private final CreditCardsRepository repository;

    public CreditCardService(CreditCardsRepository repository) {
        this.repository = repository;
    }

    public void saveNewCreditCard(Brand brand, String number, String firstName, String lastName,
                                  Integer expirationMonth, Integer expirationYear){
        CreditCard newCreditCard = CreditCardCreator.createCreditCard(brand, number, firstName, lastName, expirationMonth, expirationYear);

        repository.saveCreditCard(newCreditCard);
    }

    public CreditCard getCreditCard(String number) throws CreditCardRepositoryException {
        return repository.getCreditCard(number);
    }

    public String validateAndPrintOperation(Integer operationValue){
        if(validOperation(operationValue)){
            return "The operation with value "+operationValue+" is valid!";
        }
        return "The operation with value "+operationValue+" is not valid! (Out of range)";
    }

    private void validateOperation(Integer operationValue) throws InvalidOperationException {
        if(!validOperation(operationValue)){
            throw new InvalidOperationException(InvalidOperationCause.OUT_OF_RANGE_OPERATION);
        }
    }

    private Boolean validOperation(Integer operationValue) {
        return operationValue > 0 && operationValue < MAX_OPERATION_VALUE;
    }

    public String validateAndPrintCreditCard(CreditCard creditCard) {
        if (this.creditCardIsExpired(creditCard)){
            return "The Credit Card "+creditCard.getBrand()+" " + creditCard.getLastNumbers() + " is not valid to operate! (Expired Credit Card)";
        }
        return "The Credit Card "+creditCard.getBrand()+" " + creditCard.getLastNumbers() + " is valid to operate!";
    }

    private Boolean creditCardIsExpired(CreditCard creditCard) {
        return creditCard.getExpirationDate().before(Calendar.getInstance().getTime());
    }

    public String compareCreditCard(CreditCard creditCard1, CreditCard creditCard2) {
        if (creditCard1.equals(creditCard2)) {
            return "The Credit Cards are equals! (Brand: "+creditCard1.getBrand()+"; Last numbers: "+creditCard1.getLastNumbers()+")";
        }
        return "The Credit Cards are different! (Credit Card 1: "+printCreditCardInformation(creditCard1)+"; Credit Card 2: "+printCreditCardInformation(creditCard2)+")";
    }

    public String getAndPrintCreditCardInformation(String creditCardNumber) {
        try {
            CreditCard creditCard = this.getCreditCard(creditCardNumber);
            return printCreditCardInformation(creditCard);
        } catch (CreditCardRepositoryException ccre) {
            return "Could not get any Credit Card information from number: "+creditCardNumber+"!";
        }
    }

    private String printCreditCardInformation(CreditCard creditCard) {
        return "CreditCard{" +
                "brand = " + creditCard.getBrand() +
                ", number = '" + creditCard.getNumber() + '\'' +
                ", cardHolder = \"" + creditCard.getCardHolder().getFullName() + '\"' +
                ", expirationDate = \"" + DateUtil.getStringDate(creditCard.getExpirationDate()) + '\"' +
                '}';
    }

    public Double calculateInterestRateForOperation(Integer operationValue, Brand brand) throws InvalidOperationException {
        this.validateOperation(operationValue);
        Double brandInterestRate = brand.calculateInterestRate();

        return Double.min(MAX_INTEREST_RATE, Double.max(MIN_INTEREST_RATE, brandInterestRate));
    }
}
