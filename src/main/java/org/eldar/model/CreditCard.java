package org.eldar.model;

import org.eldar.util.DateUtil;

import java.util.Date;
import java.util.Objects;

public class CreditCard {

    private final Brand brand;
    private final String number;
    private final CardHolder cardHolder;
    private final Date expirationDate;

    public CreditCard(Brand brand, String number, CardHolder cardHolder, Date expirationDate) {
        this.brand = brand;
        this.number = number;
        this.cardHolder = cardHolder;
        this.expirationDate = expirationDate;
    }

    public Brand getBrand() {
        return brand;
    }

    public String getNumber() {
        return number;
    }

    public String getLastNumbers() {
        return number.substring(number.length() - 4);
    }

    public CardHolder getCardHolder() {
        return cardHolder;
    }

    public Date getExpirationDate() {
        return expirationDate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CreditCard that = (CreditCard) o;
        return getBrand() == that.getBrand()
                && Objects.equals(getNumber(), that.getNumber())
                && Objects.equals(getCardHolder(), that.getCardHolder())
                && Objects.equals(DateUtil.getStringDate(getExpirationDate()), DateUtil.getStringDate(that.getExpirationDate()));
    }

    @Override
    public int hashCode() {
        return Objects.hash(getBrand(), getNumber(), getCardHolder(), getExpirationDate());
    }
}
