package com.mycompany.shoppingcart.discount;

import com.mycompany.shoppingcart.cart.CartEntry;
import com.mycompany.shoppingcart.product.Product;

import java.math.BigDecimal;

/**
 * If a product has more than 3 as quantity, for every 3 we only bill for 2.
 */
public class ThreeForTwoDiscount implements Discount {

  @Override
  public void apply(CartEntry entry) {
    long quantity = entry.getQuantity();
    long numberOfTriplets = quantity / 3;
    if (numberOfTriplets > 0) {
      long remaining = quantity % 3;
      Product product = entry.getProduct();
      BigDecimal price = product.getPrice();
      entry.setEntryTotal(price.multiply(BigDecimal.valueOf((numberOfTriplets * 2) + remaining)));
    }
  }
}
