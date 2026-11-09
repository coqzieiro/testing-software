package pt.up.fe.testing;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import net.jqwik.api.Arbitraries;
import net.jqwik.api.Arbitrary;
import net.jqwik.api.Example;
import net.jqwik.api.ForAll;
import net.jqwik.api.Property;
import net.jqwik.api.Provide;

class PassingGradeTest {

    private final PassingGrade passingGrade = new PassingGrade();

    @Example
    void coversGradeBoundaries() {
        assertFalse(passingGrade.passed(0.0f));
        assertFalse(passingGrade.passed(Math.nextDown(9.5f)));
        assertTrue(passingGrade.passed(9.5f));
        assertTrue(passingGrade.passed(20.0f));
        assertThrows(AssertionError.class,
            () -> passingGrade.passed(Math.nextDown(0.0f)));
        assertThrows(AssertionError.class,
            () -> passingGrade.passed(Math.nextUp(20.0f)));
    }

    @Property(tries = 100)
    void passingGradesPass(@ForAll("passingGrades") float grade) {
        assertTrue(passingGrade.passed(grade));
    }

    @Property(tries = 100)
    void failingGradesDoNotPass(@ForAll("failingGrades") float grade) {
        assertFalse(passingGrade.passed(grade));
    }

    @Property(tries = 100)
    void gradesOutsideTheAllowedRangeFailAnAssertion(
        @ForAll("invalidGrades") float grade) {
        assertThrows(AssertionError.class, () -> passingGrade.passed(grade));
    }

    @Provide
    Arbitrary<Float> passingGrades() {
        return Arbitraries.oneOf(
            Arbitraries.floats().between(9.5f, 20.0f),
            Arbitraries.of(9.5f, 20.0f));
    }

    @Provide
    Arbitrary<Float> failingGrades() {
        return Arbitraries.oneOf(
            Arbitraries.floats().between(0.0f, 9.49f),
            Arbitraries.of(0.0f, 7.5f, Math.nextDown(9.5f)));
    }

    @Provide
    Arbitrary<Float> invalidGrades() {
        Arbitrary<Float> belowMinimum = Arbitraries.floats()
            .between(-100.0f, -0.01f);
        Arbitrary<Float> aboveMaximum = Arbitraries.floats()
            .between(20.01f, 100.0f);
        return Arbitraries.oneOf(belowMinimum, aboveMaximum);
    }
}
