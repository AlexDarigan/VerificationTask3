package cm;

import java.math.BigDecimal;

public class ManagementPricing extends PricingStrategy {
    @Override
    BigDecimal modify(BigDecimal rate) {
        return BigDecimal.valueOf(Math.max(rate.intValue(), 5.50));
    }
}
