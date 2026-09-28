package pt.up.fe.inject;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;

public class CartServiceTest {

  @Test
  public void testCalculateTotalPrice() {
    // TODO: Mock the PriceCalculator
    PriceCalculator priceCalculator = mock(PriceCalculator.class);
    // TODO: Create CartService and inject the mock
    CartService cartService = new CartService(priceCalculator);
    // TODO: Stub the behavior of the mock using when(...).thenReturn(...)
    when(priceCalculator.getPrice("book")).thenReturn(12.50);
    when(priceCalculator.getPrice("pen")).thenReturn(1.50);
    // TODO: Call the method under test, i.e., calculateTotalPrice
    List<String> itemIds = Arrays.asList("book", "pen");
    double totalPrice = cartService.calculateTotalPrice(itemIds);
    // TODO: Assert the result
    assertEquals(14.00, totalPrice, 0.001);
    // TODO: Verify that the mock was interacted with correctly
    verify(priceCalculator).getPrice("book");
    verify(priceCalculator).getPrice("pen");
  }
}
