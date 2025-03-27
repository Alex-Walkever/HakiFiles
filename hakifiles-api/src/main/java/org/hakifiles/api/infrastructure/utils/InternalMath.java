package org.hakifiles.api.infrastructure.utils;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class InternalMath {
    public static double round(double num, int places) {
        BigDecimal b = new BigDecimal(Double.toString(num));
        b = b.setScale(places, RoundingMode.HALF_UP);
        return b.doubleValue();
    }
}
