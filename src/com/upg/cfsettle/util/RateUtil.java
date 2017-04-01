package com.upg.cfsettle.util;

import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 * Created by zuo on 2017/4/1.
 */
public class RateUtil {

    /**
     * ���ʳ�100
     * @param rate
     * @return
     */
    public static BigDecimal rateToPercent(BigDecimal rate) {
        if (rate == null) {
            return BigDecimal.ZERO;
        }
        BigDecimal percent = new BigDecimal(100.00);
        return rate.divide(percent, 5, RoundingMode.HALF_UP);
    }

    /**
     * ���ʳ�100
     * @param rate
     * @return
     */
    public static BigDecimal percentToRate(BigDecimal rate) {
        if (rate == null) {
            return BigDecimal.ZERO;
        }
        BigDecimal percent = new BigDecimal(100.00);
        return rate.multiply(percent);
    }


}
