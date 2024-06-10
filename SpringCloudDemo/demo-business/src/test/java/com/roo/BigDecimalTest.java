package com.roo;


import com.roo.utils.CentConverter;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class BigDecimalTest {

    public static Long convertYuan2Cents(BigDecimal yuan) {
        if (yuan == null) {
            throw new IllegalArgumentException("Yuan cannot be null");
        }
        if (yuan.compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalArgumentException("Yuan cannot be negative");
        }
        return yuan.multiply(BigDecimal.valueOf(100)).setScale(0, RoundingMode.HALF_UP).longValueExact();
    }

    public static void T1() {
        System.out.println(convertYuan2Cents(new BigDecimal("424.42999999999995"))); // 42443
    }

    public static void T2() {
        System.out.println(CentConverter.convertCents2Yuan(42443L)); // // 424.43
    }

    public static void main(String[] args) {
        T2();
    }
}
