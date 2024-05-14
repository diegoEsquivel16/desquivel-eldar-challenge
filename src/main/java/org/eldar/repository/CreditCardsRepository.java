package org.eldar.repository;

import org.eldar.exception.CreditCardRepositoryException;
import org.eldar.model.CreditCard;

import java.util.HashMap;
import java.util.Map;

public class CreditCardsRepository {

    Map<String, CreditCard> creditCards = new HashMap<>();

    public void saveCreditCard(CreditCard creditCard) {
        creditCards.put(creditCard.getNumber(), creditCard);
    }

    public CreditCard getCreditCard(String number) throws CreditCardRepositoryException {
        CreditCard creditCard = creditCards.get(number);
        if (creditCard == null) throw new CreditCardRepositoryException("Could not find credit card with number: " + number);
        return creditCard;
    }
}
