package cm;

import java.math.BigDecimal;

public abstract class ReductionStrategy {

    abstract BigDecimal modify(BigDecimal standardRate);
}
