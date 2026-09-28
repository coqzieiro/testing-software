package pt.up.fe.inject;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.doReturn;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class TestingSpy {

  @Spy
  private List<String> values = new ArrayList<>();

  @Test
  public void ensureToLearnAboutSpy() {
    doReturn("XPTO").when(values).get(10000000);

    assertEquals("XPTO", values.get(10000000));
  }
}
