package pt.up.fe.util;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.stream.Stream;

import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.CsvSource;

public class ConverterUtilTest {

  // TODO
  static Stream<Arguments> fahrenheitToCelsiusValues() {
    return Stream.of(
        Arguments.of(32.0, 0.0),
        Arguments.of(212.0, 100.0),
        Arguments.of(-40.0, -40.0));
  }

  @ParameterizedTest
  @MethodSource("fahrenheitToCelsiusValues")
  public void convertFahrenheitToCelsiusReturnsExpectedValue(double fahrenheit, double expectedCelsius) {
    assertEquals(expectedCelsius, ConverterUtil.convertFahrenheitToCelsius(fahrenheit), 0.001);
  }

  @ParameterizedTest
  @CsvSource({"0, 32", "100, 212", "-40, -40"})
  public void convertCelsiusToFahrenheitReturnsExpectedValue(double celsius, double expectedFahrenheit) {
    assertEquals(expectedFahrenheit, ConverterUtil.convertCelsiusToFahrenheit(celsius), 0.001);
  }
}
