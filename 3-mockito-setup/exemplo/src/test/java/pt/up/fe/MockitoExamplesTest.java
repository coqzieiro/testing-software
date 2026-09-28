package pt.up.fe;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.mockStatic;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class MockitoExamplesTest {

  @Mock
  private Database database;

  @InjectMocks
  private Service service;

  @Test
  void mockShouldReturnConfiguredValueAndInjectDependency() {
    when(database.isAvailable()).thenReturn(true);

    assertTrue(service.query("SELECT * FROM clients"));
    verify(database).isAvailable();
    verifyNoMoreInteractions(database);
  }

  @Test
  void mockShouldConfigureMethodReturnValue() {
    when(database.getUniqueId()).thenReturn(42);

    assertEquals("Using database with id: 42", service.toString());
    verify(database).getUniqueId();
  }

  @Test
  void mockShouldThrowConfiguredException() {
    Properties properties = mock(Properties.class);
    when(properties.get("connection")).thenThrow(new IllegalArgumentException("Connection unavailable"));

    IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
        () -> properties.get("connection"));

    assertEquals("Connection unavailable", exception.getMessage());
  }

  @Test
  void spyShouldOverrideOneMethodWithoutCallingTheRealImplementation() {
    List<String> values = spy(new ArrayList<>());
    doReturn("mocked value").when(values).get(0);

    assertEquals("mocked value", values.get(0));
    assertEquals(0, values.size());
  }

  @Test
  void mockStaticShouldReturnConfiguredValueInsideScope() {
    try (MockedStatic<Utility> utility = mockStatic(Utility.class)) {
      utility.when(() -> Utility.getDatabaseConnection("test")).thenReturn("testing");

      assertEquals("testing", Utility.getDatabaseConnection("test"));
      utility.verify(() -> Utility.getDatabaseConnection("test"));
    }
  }
}
