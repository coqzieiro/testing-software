package pt.up.fe.util;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

import org.junit.jupiter.api.Assumptions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.condition.EnabledOnOs;
import org.junit.jupiter.api.condition.OS;

public class EmailValidatorTest {

  @Test
  public void ensureThatEmailValidatorReturnsTrueForValidEmail() {
    assertTrue(EmailValidator.isValidEmail("lars.vogel@gmail.com"));
  }

  @Test
  @DisplayName("Ensure that the usage of a subdomain is still valid, see https://en.wikipedia.org/wiki/Subdomain")
  public void emailValidator_CorrectEmailSubDomain_ReturnsTrue() {
    // TODO
    assertTrue(EmailValidator.isValidEmail("lars.vogel@subdomain.gmail.com"));
  }

  @Test
  @DisplayName("Ensure that a missiong top level domain returns false")
  public void emailValidator_InvalidEmailNoTld_ReturnsFalse() {
    // TODO
    Assumptions.assumeTrue(System.getProperty("os.name").contains("Linux"));
    assertFalse(EmailValidator.isValidEmail("lars.vogel@gmail"));
  }

  @Test
  @EnabledOnOs(OS.LINUX)
  public void emailValidator_InvalidEmailDoubleDot_ReturnsFalse() {
    // TODO
    assertFalse(EmailValidator.isValidEmail("lars.vogel@gmail..com"));
  }

  @Test
  public void emailValidator_InvalidEmailNoUsername_ReturnsFalse() {
    // TODO
    assertFalse(EmailValidator.isValidEmail("@gmail.com"));
  }

  @Test
  public void emailValidator_EmptyString_ReturnsFalse() {
    // TODO
    assertFalse(EmailValidator.isValidEmail(""));
  }

  @Test
  public void emailValidator_NullEmail_ReturnsFalse() {
    // TODO
    assertFalse(EmailValidator.isValidEmail(null));
  }
}
