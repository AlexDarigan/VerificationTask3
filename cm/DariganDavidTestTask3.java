package cm;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;

// P is for Partition
public class DariganDavidTestTask3 {

    final int EQUAL_BIG_DECIMALS = 0;

    @Test
    public void StudentReductionAppliesOverMixedPeriods() {
        PricingStrategy pricing = new StudentPricing();
        BigDecimal normalRate = new BigDecimal(2);
        BigDecimal reducedRate = new BigDecimal(1);
        ArrayList<Period> normalPeriods = new ArrayList<>();
        ArrayList<Period> reducedPeriods = new ArrayList<>();
        normalPeriods.add(new Period(3, 6));
        normalPeriods.add(new Period(7, 10));
        reducedPeriods.add(new Period(0, 3));
        Period periodStay = new Period(0, 6);

        Rate rate = new Rate(pricing, normalRate, reducedRate, normalPeriods, reducedPeriods);
        BigDecimal expected = BigDecimal.valueOf(9 * .33).setScale(2, RoundingMode.HALF_UP);
        Assertions.assertEquals(expected, rate.calculate(periodStay));
    }


    @Test
    public void ManagementMustPay5_50MinEvenInReducedPeriods() {
        PricingStrategy pricing = new ManagementPricing();
        BigDecimal normalRate = new BigDecimal(4);
        BigDecimal reducedRate = new BigDecimal(2);
        ArrayList<Period> normalPeriods = new ArrayList<>();
        ArrayList<Period> reducedPeriods = new ArrayList<>();
        normalPeriods.add(new Period(5, 6));
        normalPeriods.add(new Period(7, 9));
        reducedPeriods.add(new Period(0, 3));
        Period periodStay = new Period(0, 2);

        Rate rate = new Rate(pricing, normalRate, reducedRate, normalPeriods, reducedPeriods);
        Assertions.assertEquals(BigDecimal.valueOf(5.50), rate.calculate(periodStay));
    }

    @Test
    public void HalfReductionForVisitorPast10() {
        PricingStrategy pricing = new VisitorPricing();
        BigDecimal normalRate = new BigDecimal(5);
        BigDecimal reducedRate = new BigDecimal(2);
        ArrayList<Period> normalPeriods = new ArrayList<>();
        ArrayList<Period> reducedPeriods = new ArrayList<>();
        normalPeriods.add(new Period(10, 18));
        Period stay = new Period(10, 13);
        Rate rate = new Rate(pricing, normalRate, reducedRate, normalPeriods, reducedPeriods);
        BigDecimal actual = rate.calculate(stay);
        BigDecimal expected = BigDecimal.valueOf(2.50);
        Assertions.assertEquals(expected, actual);
    }
    @Test
    public void VisitorFirst10IsFree() {
        PricingStrategy pricing = new VisitorPricing();
        BigDecimal normalRate = new BigDecimal(5);
        BigDecimal reducedRate = new BigDecimal(2);
        ArrayList<Period> normalPeriods = new ArrayList<>();
        ArrayList<Period> reducedPeriods = new ArrayList<>();
        normalPeriods.add(new Period(10, 18));
        Period stay = new Period(10, 12);
        Rate rate = new Rate(pricing, normalRate, reducedRate, normalPeriods, reducedPeriods);
        BigDecimal actual = rate.calculate(stay);
        BigDecimal expected = BigDecimal.valueOf(0);
        Assertions.assertEquals(expected, actual);
    }

    // Task 3
    @Test
    public void StaffPayNoMoreThan10PerDay() {
        PricingStrategy pricing = new StaffPricing();
        BigDecimal normalRate = new BigDecimal(5);
        BigDecimal reducedRate = new BigDecimal(4);
        ArrayList<Period> normalPeriods = new ArrayList<>();
        ArrayList<Period> reducedPeriods = new ArrayList<>();
        normalPeriods.add(new Period(9, 17));
        Period stay = new Period(9, 17);
        Rate rate = new Rate(pricing, normalRate, reducedRate, normalPeriods, reducedPeriods);
        BigDecimal actual = rate.calculate(stay);
        BigDecimal expected = BigDecimal.valueOf(10);
        Assertions.assertEquals(expected, actual);
    }
    @Test
    public void ManagementMust_Pay5_50Min() {
        PricingStrategy pricing = new ManagementPricing();
        BigDecimal normalRate = new BigDecimal(2);
        BigDecimal reducedRate = new BigDecimal(1);
        ArrayList<Period> normalPeriods = new ArrayList<>();
        ArrayList<Period> reducedPeriods = new ArrayList<>();
        normalPeriods.add(new Period(9, 17));
        Period stay = new Period(10, 11);
        Rate rate = new Rate(pricing, normalRate, reducedRate, normalPeriods, reducedPeriods);
        BigDecimal actual = rate.calculate(stay);
        BigDecimal expected = BigDecimal.valueOf(5.50);
        Assertions.assertEquals(expected, actual);
    }
    @Test
    public void StudentRateOver5_50ReducedBy33Percent() {
        PricingStrategy pricing = new StudentPricing();
        BigDecimal normalRate = new BigDecimal(6);
        BigDecimal reducedRate = new BigDecimal(0);
        ArrayList<Period> normalPeriods = new ArrayList<>();
        ArrayList<Period> reducedPeriods = new ArrayList<>();
        normalPeriods.add(new Period(7, 18));
        Period stay = new Period(8, 15);
        Rate rate = new Rate(pricing, normalRate, reducedRate, normalPeriods, reducedPeriods);
        BigDecimal actual = rate.calculate(stay);
        BigDecimal expected = BigDecimal.valueOf((7 * 6) * .33).setScale(2, RoundingMode.HALF_UP);
        Assertions.assertEquals(expected, actual);
    }

    // (Task 2 Bug: P1_NormalRateIsEqualOrGreaterThanZero)
    @Test public void TDD_NormalRateIsEqualOrGreaterThanZero() {
        PricingStrategy pricing = new StaffPricing();
        BigDecimal normalRate = BigDecimal.ZERO;
        BigDecimal reducedRate = BigDecimal.ZERO;

        Rate rate = new Rate(pricing, normalRate, reducedRate, new ArrayList<>(), new ArrayList<>());
        Assertions.assertInstanceOf(Rate.class, rate);
    }


    // Task 2 - White Box
    // TC stands for Testcase
    @Test
    public void TC1_StartIsGreaterThan24() {
        Assertions.assertThrows(IllegalArgumentException.class, () -> new Period(25, 30));
    }

    @Test
    public void TC2_EndIsLessThan0() {
        Assertions.assertThrows(IllegalArgumentException.class,() -> new Period(0, -1));
    }


    @Test
    public void TC1_ReducedPeriodsAreNull() {
        PricingStrategy pricing = new StaffPricing();
        BigDecimal normalRate = BigDecimal.valueOf(2);
        BigDecimal reducedRate = BigDecimal.valueOf(1);
        ArrayList<Period> normalPeriods = new ArrayList<>();
        ArrayList<Period> reducedPeriods = null;

        Assertions.assertThrows(IllegalArgumentException.class, () -> new Rate(pricing, normalRate, reducedRate, normalPeriods, reducedPeriods));
    }

    @Test
    public void TC2_ReducedPeriodsAreNullOrNormalPeriodsAreNull() {
        PricingStrategy pricing = new StaffPricing();
        BigDecimal normalRate = BigDecimal.valueOf(2);
        BigDecimal reducedRate = BigDecimal.valueOf(1);
        ArrayList<Period> normalPeriods = null;
        ArrayList<Period> reducedPeriods = new ArrayList<>();

        Assertions.assertThrows(IllegalArgumentException.class, () -> new Rate(pricing, normalRate, reducedRate, normalPeriods, reducedPeriods));
    }

    @Test
    public void TC3_NormalRateIsNull() {
        PricingStrategy pricing = new StudentPricing();
        BigDecimal normalRate = null;
        BigDecimal reducedRate = BigDecimal.ZERO;
        ArrayList<Period> normalPeriods = new ArrayList<>();
        ArrayList<Period> reducedPeriods = new ArrayList<>();

        Assertions.assertThrows(IllegalArgumentException.class, () -> new Rate(pricing, normalRate, reducedRate, normalPeriods, reducedPeriods));
    }

    @Test
    public void TC4_NormalRateIsNullORReducedRateIsNull() {
        PricingStrategy pricing = new StudentPricing();
        BigDecimal normalRate = BigDecimal.valueOf(3);
        BigDecimal reducedRate = null;
        ArrayList<Period> normalPeriods = new ArrayList<>();
        ArrayList<Period> reducedPeriods = new ArrayList<>();

        Assertions.assertThrows(IllegalArgumentException.class, () -> new Rate(pricing, normalRate, reducedRate, normalPeriods, reducedPeriods));
    }

    @Test
    public void TC5_ReducedPeriodsAreInvalid() {
        PricingStrategy pricing = new VisitorPricing();
        BigDecimal normalRate = BigDecimal.valueOf(2);
        BigDecimal reducedRate = BigDecimal.valueOf(1);
        ArrayList<Period> normalPeriods = new ArrayList<>();
        ArrayList<Period> reducedPeriods = new ArrayList<>();
        reducedPeriods.add(new Period(1, 3));
        reducedPeriods.add(new Period(2, 3));
        reducedPeriods.add(new Period(4, 10));

        Assertions.assertThrows(IllegalArgumentException.class, () -> new Rate(pricing, normalRate, reducedRate, normalPeriods, reducedPeriods));
    }

    @Test
    public void TC6_ILessThanPeriodSizeAndIsValid_False() {

        PricingStrategy pricing = new VisitorPricing();
        BigDecimal normalRate = BigDecimal.valueOf(5);
        BigDecimal reducedRate = BigDecimal.valueOf(2);
        ArrayList<Period> normalPeriods = new ArrayList<>();
        ArrayList<Period> reducedPeriods = new ArrayList<>();
        normalPeriods.add(new Period(1, 3));
        normalPeriods.add(new Period(4, 6));
        normalPeriods.add(new Period(7, 9));
        reducedPeriods.add(new Period(3, 4));
        reducedPeriods.add(new Period(4, 5));
        reducedPeriods.add(new Period(9, 10));

        Assertions.assertThrows(IllegalArgumentException.class, () -> new Rate(pricing, normalRate, reducedRate, normalPeriods, reducedPeriods));

    }

    // Task 1 - Black Box
    @Test
    public void P1_NormalRateIsEqualOrGreaterThanZero() {
        PricingStrategy pricing = new StaffPricing();
        BigDecimal normalRate = BigDecimal.ZERO;
        BigDecimal reducedRate = BigDecimal.ZERO;
        ArrayList<Period> normalPeriods = new ArrayList<>();
        ArrayList<Period> reducedPeriods = new ArrayList<>();
        normalPeriods.add(new Period(4, 6));
        normalPeriods.add(new Period(10, 11));
        reducedPeriods.add(new Period(0, 3));

        Rate rate = new Rate(pricing, normalRate, reducedRate, normalPeriods, reducedPeriods);
        Assertions.assertInstanceOf(Rate.class, rate);

    }

    @Test
    public void P2_NormalRateIsEqualOrGreaterThanZero() {
        PricingStrategy pricing = new StudentPricing();
        BigDecimal normalRate = new BigDecimal(-1);
        BigDecimal reducedRate = BigDecimal.ZERO;
        ArrayList<Period> normalPeriods = new ArrayList<>();
        ArrayList<Period> reducedPeriods = new ArrayList<>();
        normalPeriods.add(new Period(3, 6));
        normalPeriods.add(new Period(9, 12));
        reducedPeriods.add(new Period(0, 2));

        Assertions.assertThrows(IllegalArgumentException.class, () -> new Rate(pricing, normalRate, reducedRate, normalPeriods, reducedPeriods));


    }

    @Test
    public void P3_NormalRateIsEqualOrGreaterThanZero() {
        PricingStrategy pricing = new ManagementPricing();
        BigDecimal normalRate = new BigDecimal(1);
        BigDecimal reducedRate = BigDecimal.ZERO;
        ArrayList<Period> normalPeriods = new ArrayList<>();
        ArrayList<Period> reducedPeriods = new ArrayList<>();
        normalPeriods.add(new Period(2, 6));
        normalPeriods.add(new Period(8, 10));
        reducedPeriods.add(new Period(0, 1));

        Rate rate = new Rate(pricing, normalRate, reducedRate, normalPeriods, reducedPeriods);
        Assertions.assertInstanceOf(Rate.class, rate);
    }

    @Test
    public void P4_NormalRateIsEqualOrGreaterThanZero() {
        PricingStrategy pricing = new VisitorPricing();
        BigDecimal normalRate = new BigDecimal(25);
        BigDecimal reducedRate = BigDecimal.ZERO;
        ArrayList<Period> normalPeriods = new ArrayList<>();
        ArrayList<Period> reducedPeriods = new ArrayList<>();
        normalPeriods.add(new Period(4, 6));
        normalPeriods.add(new Period(7, 10));
        reducedPeriods.add(new Period(2, 3));

        Rate rate = new Rate(pricing, normalRate, reducedRate, normalPeriods, reducedPeriods);
        Assertions.assertInstanceOf(Rate.class, rate);

    }

    @Test
    public void P5_ReducedRateIsEqualOrGreaterThanZero() {
        PricingStrategy pricing = new StudentPricing();
        BigDecimal normalRate = new BigDecimal(5);
        BigDecimal reducedRate = new BigDecimal(-1);
        ArrayList<Period> normalPeriods = new ArrayList<>();
        ArrayList<Period> reducedPeriods = new ArrayList<>();
        normalPeriods.add(new Period(5, 6));
        normalPeriods.add(new Period(7, 11));
        reducedPeriods.add(new Period(0, 5));

        Assertions.assertThrows(IllegalArgumentException.class, () -> new Rate(pricing, normalRate, reducedRate, normalPeriods, reducedPeriods));

    }

    @Test
    public void P6_ReducedRateIsEqualOrGreaterThanZero() {
        PricingStrategy pricing = new VisitorPricing();
        BigDecimal normalRate = new BigDecimal(3);
        BigDecimal reducedRate = new BigDecimal(1);
        ArrayList<Period> normalPeriods = new ArrayList<>();
        ArrayList<Period> reducedPeriods = new ArrayList<>();
        normalPeriods.add(new Period(5, 9));
        normalPeriods.add(new Period(10, 11));
        reducedPeriods.add(new Period(0, 5));

        Rate rate = new Rate(pricing, normalRate, reducedRate, normalPeriods, reducedPeriods);
        Assertions.assertInstanceOf(Rate.class, rate);

    }

    @Test
    public void P7_ReducedRateIsEqualOrGreaterThanZero() {
        PricingStrategy pricing = new StaffPricing();
        BigDecimal normalRate = new BigDecimal(45);
        BigDecimal reducedRate = new BigDecimal(30);
        ArrayList<Period> normalPeriods = new ArrayList<>();
        ArrayList<Period> reducedPeriods = new ArrayList<>();
        normalPeriods.add(new Period(5, 6));
        normalPeriods.add(new Period(10, 11));
        reducedPeriods.add(new Period(0, 5));

        Rate rate = new Rate(pricing, normalRate, reducedRate, normalPeriods, reducedPeriods);
        Assertions.assertInstanceOf(Rate.class, rate);
    }

    @Test
    public void P8_ReducedRateIsEqualOrLessThanNormalRate() {
        PricingStrategy pricing = new VisitorPricing();
        BigDecimal normalRate = new BigDecimal(0);
        BigDecimal reducedRate = new BigDecimal(2);
        ArrayList<Period> normalPeriods = new ArrayList<>();
        ArrayList<Period> reducedPeriods = new ArrayList<>();
        normalPeriods.add(new Period(5, 6));
        normalPeriods.add(new Period(10, 11));
        reducedPeriods.add(new Period(0, 5));

        Assertions.assertThrows(IllegalArgumentException.class, () -> new Rate(pricing, normalRate, reducedRate, normalPeriods, reducedPeriods));
    }

    @Test
    public void P9_ReducedRateIsEqualOrGreaterThanZero() {
        PricingStrategy pricing = new StaffPricing();
        BigDecimal normalRate = new BigDecimal(4);
        BigDecimal reducedRate = new BigDecimal(2);
        ArrayList<Period> normalPeriods = new ArrayList<>();
        ArrayList<Period> reducedPeriods = new ArrayList<>();
        normalPeriods.add(new Period(5, 6));
        normalPeriods.add(new Period(10, 11));
        reducedPeriods.add(new Period(0, 5));

        Rate rate = new Rate(pricing, normalRate, reducedRate, normalPeriods, reducedPeriods);
        Assertions.assertInstanceOf(Rate.class, rate);
    }

    @Test
    public void P10_PeriodsCannotOverlap() {
        PricingStrategy pricing = new StaffPricing();
        BigDecimal normalRate = new BigDecimal(5);
        BigDecimal reducedRate = new BigDecimal(1);
        ArrayList<Period> normalPeriods = new ArrayList<>();
        ArrayList<Period> reducedPeriods = new ArrayList<>();
        normalPeriods.add(new Period(5, 6));
        normalPeriods.add(new Period(4, 7));
        reducedPeriods.add(new Period(0, 5));

        Assertions.assertThrows(IllegalArgumentException.class, () -> new Rate(pricing, normalRate, reducedRate, normalPeriods, reducedPeriods));
    }

    // Periods seem to be able to overlap if in different Arrays
    @Test
    public void P11_PeriodsCannotOverlap() {
        PricingStrategy pricing = new VisitorPricing();
        BigDecimal normalRate = new BigDecimal(3);
        BigDecimal reducedRate = new BigDecimal(2);
        ArrayList<Period> normalPeriods = new ArrayList<>();
        ArrayList<Period> reducedPeriods = new ArrayList<>();
        normalPeriods.add(new Period(5, 6));
        normalPeriods.add(new Period(7, 9));
        reducedPeriods.add(new Period(4, 6));

        Assertions.assertThrows(IllegalArgumentException.class, () -> new Rate(pricing, normalRate, reducedRate, normalPeriods, reducedPeriods));
    }

    @Test
    public void P12_PeriodsCannotOverlap() {
        PricingStrategy pricing = new StaffPricing();
        BigDecimal normalRate = new BigDecimal(15);
        BigDecimal reducedRate = new BigDecimal(10);
        ArrayList<Period> normalPeriods = new ArrayList<>();
        ArrayList<Period> reducedPeriods = new ArrayList<>();
        normalPeriods.add(new Period(0, 5));
        normalPeriods.add(new Period(7, 9));
        reducedPeriods.add(new Period(5, 6));

        Rate rate = new Rate(pricing, normalRate, reducedRate, normalPeriods, reducedPeriods);
        Assertions.assertInstanceOf(Rate.class, rate);
    }

    // Rate Calculate Tests
    @Test
    public void P1_periodStayInNormalPeriods() {
        PricingStrategy pricing = new StaffPricing();
        BigDecimal normalRate = new BigDecimal(4);
        BigDecimal reducedRate = new BigDecimal(2);
        ArrayList<Period> normalPeriods = new ArrayList<>();
        ArrayList<Period> reducedPeriods = new ArrayList<>();
        normalPeriods.add(new Period(5, 6));
        normalPeriods.add(new Period(10, 11));
        reducedPeriods.add(new Period(0, 5));
        Period periodStay = new Period(5, 6);

        Rate rate = new Rate(pricing, normalRate, reducedRate, normalPeriods, reducedPeriods);
        Assertions.assertEquals(new BigDecimal(4), rate.calculate(periodStay));
    }

    @Test
    public void P2_periodStayInNormalPeriods() {
        PricingStrategy pricing = new StaffPricing();
        BigDecimal normalRate = new BigDecimal(2);
        BigDecimal reducedRate = new BigDecimal(1);
        ArrayList<Period> normalPeriods = new ArrayList<>();
        ArrayList<Period> reducedPeriods = new ArrayList<>();
        normalPeriods.add(new Period(5, 6));
        normalPeriods.add(new Period(9, 10));
        reducedPeriods.add(new Period(0, 5));
        Period periodStay = new Period(5, 7);

        Rate rate = new Rate(pricing, normalRate, reducedRate, normalPeriods, reducedPeriods);
        Assertions.assertEquals(new BigDecimal(2), rate.calculate(periodStay));
    }

    @Test
    public void P4_periodStayInReducedPeriods() {
        PricingStrategy pricing = new StaffPricing();
        BigDecimal normalRate = new BigDecimal(2);
        BigDecimal reducedRate = new BigDecimal(1);
        ArrayList<Period> normalPeriods = new ArrayList<>();
        ArrayList<Period> reducedPeriods = new ArrayList<>();
        normalPeriods.add(new Period(5, 6));
        normalPeriods.add(new Period(7, 10));
        reducedPeriods.add(new Period(0, 3));
        Period periodStay = new Period(1, 3);

        Rate rate = new Rate(pricing, normalRate, reducedRate, normalPeriods, reducedPeriods);
        Assertions.assertEquals(new BigDecimal(2), rate.calculate(periodStay));
    }

    @Test
    public void P6_periodStayInReducedPeriods() {
        PricingStrategy pricing = new StudentPricing();
        BigDecimal normalRate = new BigDecimal(2);
        BigDecimal reducedRate = new BigDecimal(1);
        ArrayList<Period> normalPeriods = new ArrayList<>();
        ArrayList<Period> reducedPeriods = new ArrayList<>();
        normalPeriods.add(new Period(5, 6));
        normalPeriods.add(new Period(7, 10));
        reducedPeriods.add(new Period(0, 3));
        Period periodStay = new Period(0, 3);

        Rate rate = new Rate(pricing, normalRate, reducedRate, normalPeriods, reducedPeriods);
        Assertions.assertEquals(new BigDecimal(3), rate.calculate(periodStay));
    }

    @Test
    public void P8_periodStayNotInNormalPeriodsANDNotInReducedPeriods() {
        PricingStrategy pricing = new VisitorPricing();
        BigDecimal normalRate = new BigDecimal(2);
        BigDecimal reducedRate = new BigDecimal(1);
        ArrayList<Period> normalPeriods = new ArrayList<>();
        ArrayList<Period> reducedPeriods = new ArrayList<>();
        normalPeriods.add(new Period(5, 6));
        normalPeriods.add(new Period(7, 9));
        reducedPeriods.add(new Period(0, 3));
        Period periodStay = new Period(10, 20);

        Rate rate = new Rate(pricing, normalRate, reducedRate, normalPeriods, reducedPeriods);
        Assertions.assertEquals(new BigDecimal(0), rate.calculate(periodStay));
    }

    // Period Constructor Tests
    // LTE = Less Than or Equal, LT = Less Than
    @Test
    public void P1_ZeroLTEstartHourLTendHourLTE24() {
        Assertions.assertInstanceOf(Period.class, new Period(0, 24));
    }

    @Test
    public void P2_Zero_LTE_startHour_LT_endHour_LTE_24() {
        Assertions.assertInstanceOf(Period.class, new Period(1, 23));
    }

    @Test
    public void P3_Zero_LTE_startHour_LT_endHour_LTE_24() {
        Assertions.assertThrows(IllegalArgumentException.class, () -> new Period(-1, 1));
    }

    @Test
    public void P4_Zero_LTE_startHour_LT_endHour_LTE_24() {
        Assertions.assertThrows(IllegalArgumentException.class, () -> new Period(0, 25));
    }

    @Test
    public void P5_Zero_LTE_startHour_LT_endHour_LTE_24() {
        Assertions.assertThrows(IllegalArgumentException.class, () -> new Period(24, 24));
    }

    @Test
    public void P6_Zero_LTE_startHour_LT_endHour_LTE_24() {
        Assertions.assertThrows(IllegalArgumentException.class, () -> new Period(25, 20));
    }

    // Period Duration Tests
    // D = Duration to avoid name conflicts from above
    @Test
    public void P1_Zero_LTE_startHour_LT_endHour_LTE_24_D() {
        Period period = new Period(0, 24);
        Assertions.assertEquals(24, period.duration());
    }

    @Test
    public void P2_Zero_LTE_startHour_LT_endHour_LTE_24_D() {
        Period period = new Period(1, 23);
        Assertions.assertEquals(22, period.duration());
    }

    @Test
    public void P3_Zero_LTE_startHour_LT_endHour_LTE_24_D() {
        Period period = new Period(7, 12);
        Assertions.assertEquals(5, period.duration());
    }

    // Period Overlaps Tests
    @Test
    public void P1_Period_Overlaps() {
        Period a = new Period(1, 2);
        Period b = new Period(1, 2);

        Assertions.assertTrue(a.overlaps(b));
    }

    @Test
    public void P2_Period_Overlaps() {
        Period a = new Period(0, 1);
        Period b = new Period(1, 2);
        Assertions.assertFalse(a.overlaps(b));
    }

    // This period does not actually overlap
    @Test
    public void P3_Period_Overlaps() {
        Period a = new Period(1, 3);
        Period b = new Period(1, 2);
        Assertions.assertTrue(a.overlaps(b));
    }

    @Test
    public void P4_Period_Overlaps() {
        Period a = new Period(0, 1);
        Period b = new Period(2, 3);
        Assertions.assertFalse(a.overlaps(b));
    }

    @Test
    public void P5_Period_Overlaps() {
        Period a = new Period(0, 24);
        Period b = new Period(5, 10);
        Assertions.assertTrue(a.overlaps(b));
    }
}
