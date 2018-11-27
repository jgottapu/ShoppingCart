package com.mycompany.shoppingcart.discount;

import com.mycompany.shoppingcart.cart.CartEntry;
import com.mycompany.shoppingcart.product.Product;

import java.math.BigDecimal;

/**
 * Applies a buy one get one free discount to the cart entry.
 */
public class BuyOneGetOneDiscount implements Discount {

  @Override
  public void apply(CartEntry entry) {
    long quantity = entry.getQuantity();
    long numberOfPairs = quantity / 2;
    if (numberOfPairs > 0) {
      long remaining = quantity % 2;
      Product product = entry.getProduct();
      BigDecimal price = product.getPrice();
      entry.setEntryTotal(price.multiply(BigDecimal.valueOf(numberOfPairs + remaining)));
    }
  }
}
