package pt.up.fe.api;

import java.util.List;

public class CartService {

  private PriceCalculator priceCalculator;

  public CartService(PriceCalculator priceCalculator) {
    this.priceCalculator = priceCalculator;
  }

  public double calculateTotalPrice(List<String> itemIds) {
    double total = 0;
    for (String itemId : itemIds) {
      total += priceCalculator.getPrice(itemId);
    }
    return total;
  }
}
