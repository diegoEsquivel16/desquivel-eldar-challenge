package org.eldar.util;

import org.eldar.model.Brand;
import org.eldar.model.CardHolder;
import org.eldar.model.CreditCard;

import java.util.Calendar;
import java.util.Date;

public class CreditCardCreator {

    public static CreditCard createCreditCard(Brand brand, String number, String firstName, String lastName,
                                              Integer expirationMonth, Integer expirationYear) {
        CardHolder cardHolder = new CardHolder(firstName, lastName);
        Date expirationDate = new Calendar.Builder()
                .set(Calendar.YEAR, expirationYear)
                .set(Calendar.MONTH, expirationMonth)
                .build().getTime();
        return new CreditCard(brand, number, cardHolder, expirationDate);
    }
}
