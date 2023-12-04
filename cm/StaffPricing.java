package cm;

import java.math.BigDecimal;

public class StaffPricing extends PricingStrategy {
    @Override
    BigDecimal modify(BigDecimal rate) {
        return BigDecimal.valueOf(Math.min(rate.intValue(), 10));
    }
}
