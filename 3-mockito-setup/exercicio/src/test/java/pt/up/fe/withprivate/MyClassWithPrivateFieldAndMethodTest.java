package pt.up.fe.withprivate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import pt.up.fe.withprivate.MyClassWithPrivateFieldAndMethod;

@ExtendWith(MockitoExtension.class)
public class MyClassWithPrivateFieldAndMethodTest {

  @Spy
  private MyClassWithPrivateFieldAndMethod mock = new MyClassWithPrivateFieldAndMethod();

  @Test
  public void ensureSpyAndReflectiveAccessCanChangeAPrivateField() throws Exception {
    assertEquals("initial", mock.getValue());
    mock.field1 = "Hello";

    when(mock.toBeMockedByMockito()).thenReturn("mocked by Mockito");
    Field declaredField = MyClassWithPrivateFieldAndMethod.class.getDeclaredField("hiddenField");
    declaredField.setAccessible(true);
    declaredField.set(mock, "changed");

    assertEquals("Hello", mock.field1);
    assertEquals("changed", mock.getValue());
    assertEquals("mocked by Mockito", mock.toBeMockedByMockito());
  }

  @Test
  public void ensureSpyAndReflectiveAccessCanChangeAPrivateMethod() throws Exception {
    // TODO: Use reflection to call the private method 'meineMethod' and validate that the internal value was changed.
    Method privateMethod = MyClassWithPrivateFieldAndMethod.class.getDeclaredMethod("meineMethod");
    privateMethod.setAccessible(true);
    privateMethod.invoke(mock);

    assertEquals("lalal", mock.getValueSetByPrivateMethod());
  }
}
