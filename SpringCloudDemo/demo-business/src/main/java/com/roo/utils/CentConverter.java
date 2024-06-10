package com.roo.utils;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class CentConverter {
    public static Long convertYuan2Cents(BigDecimal yuan) {
        if (yuan == null) {
            throw new IllegalArgumentException("Yuan cannot be null");
        }
        if (yuan.compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalArgumentException("Yuan cannot be negative");
        }
        return yuan.multiply(BigDecimal.valueOf(100)).setScale(0, RoundingMode.HALF_UP).longValueExact();

    }

    public static BigDecimal convertCents2Yuan(Long cents) {
        if (cents == null) {
            throw new IllegalArgumentException("Cents cannot be null");
        }
        if (cents < 0) {
            throw new IllegalArgumentException("Cents cannot be negative");
        }
        return new BigDecimal(cents).divide(new BigDecimal(100));
    }
}
