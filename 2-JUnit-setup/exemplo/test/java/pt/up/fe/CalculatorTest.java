package pt.up.fe;

import static java.time.Duration.ofMillis;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTimeout;

import java.util.stream.Stream;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assumptions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DynamicTest;
import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestFactory;
import org.junit.jupiter.api.condition.DisabledOnOs;
import org.junit.jupiter.api.condition.EnabledOnOs;
import org.junit.jupiter.api.condition.OS;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.MethodSource;

public class CalculatorTest {

  private Calculator calculator;

  @BeforeAll
  static void beforeAll() {
  }

  @BeforeEach
  void setUp() {
    calculator = new Calculator();
  }

  @AfterEach
  void tearDown() {
    calculator = null;
  }

  @AfterAll
  static void afterAll() {
  }

  @Test
  public void testMultiply() {
    assertEquals(20, calculator.multiply(4, 5));
  }

  @Test
  void groupedAssertions() {
    assertAll(
        () -> assertEquals(0, calculator.multiply(0, 5)),
        () -> assertEquals(-15, calculator.multiply(-3, 5)));
  }

  @Test
  void exceptionAssertion() {
    assertThrows(ArithmeticException.class, () -> {
      int result = 1 / 0;
    });
  }

  @Test
  void timeoutAssertion() {
    assertTimeout(ofMillis(100), () -> calculator.multiply(4, 5));
  }

  @RepeatedTest(3)
  void repeatedTest() {
    assertEquals(12, calculator.multiply(3, 4));
  }

  @ParameterizedTest
  @MethodSource("multiplicationValues")
  void parameterizedWithMethodSource(int first, int second, int expected) {
    assertEquals(expected, calculator.multiply(first, second));
  }

  static Stream<Arguments> multiplicationValues() {
    return Stream.of(
        Arguments.of(1, 2, 2),
        Arguments.of(5, 3, 15),
        Arguments.of(121, 4, 484));
  }

  @ParameterizedTest
  @CsvSource({"1, 2, 2", "5, 3, 15", "121, 4, 484"})
  void parameterizedWithCsvSource(int first, int second, int expected) {
    assertEquals(expected, calculator.multiply(first, second));
  }

  @ParameterizedTest
  @CsvFileSource(resources = "/multiplications.csv", numLinesToSkip = 1)
  void parameterizedWithCsvFileSource(int first, int second, int expected) {
    assertEquals(expected, calculator.multiply(first, second));
  }

  @TestFactory
  Stream<DynamicTest> dynamicTests() {
    return Stream.of(
        DynamicTest.dynamicTest("2 x 3 equals 6", () -> assertEquals(6, calculator.multiply(2, 3))),
        DynamicTest.dynamicTest("7 x 8 equals 56", () -> assertEquals(56, calculator.multiply(7, 8))));
  }

  @Test
  @Disabled("Example of a deliberately disabled test")
  void disabledTest() {
    assertEquals(20, calculator.multiply(4, 5));
  }

  @Test
  void assumptionExample() {
    Assumptions.assumeFalse(System.getProperty("os.name").contains("Linux"));
    assertEquals(20, calculator.multiply(4, 5));
  }

  @Test
  @DisabledOnOs(OS.LINUX)
  void disabledOnLinux() {
    assertEquals(20, calculator.multiply(4, 5));
  }

  @Test
  @EnabledOnOs(OS.LINUX)
  void enabledOnLinux() {
    assertEquals(20, calculator.multiply(4, 5));
  }
}
