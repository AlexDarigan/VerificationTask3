package cm;

import java.math.BigDecimal;

public abstract class PricingStrategy {

    abstract BigDecimal modify(BigDecimal standardRate);
}
