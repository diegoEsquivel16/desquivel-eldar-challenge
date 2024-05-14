package org.eldar.model;

import org.eldar.util.DateUtil;

public enum Brand {
    VISA {
        @Override
        public Double calculateInterestRate() {
            String year = String.valueOf(DateUtil.getCurrentYear());
            Double lastTwoDigitsOfYear = Double.valueOf(year.substring(2, 4));
            Double month = Double.valueOf(DateUtil.getCurrentMonth());
            return lastTwoDigitsOfYear / month;
        }
    }, NARA {
        @Override
        public Double calculateInterestRate() {
            Double day = Double.valueOf(DateUtil.getCurrentDay());
            return day * 0.5;
        }
    }, AMEX {
        @Override
        public Double calculateInterestRate() {
            Double month = Double.valueOf(DateUtil.getCurrentMonth());
            return month * 0.1;
        }
    };

    public abstract Double calculateInterestRate();
}
