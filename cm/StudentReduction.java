package cm;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class StudentReduction extends ReductionStrategy {
    @Override
    BigDecimal modify(BigDecimal rate) {
        if(rate.compareTo(new BigDecimal("5.50")) > 0) {
            return rate = BigDecimal.valueOf(rate.intValue() * .33).setScale(2, RoundingMode.HALF_UP);
        } else {
            return rate;
        }
    }
}
