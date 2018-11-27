package com.mycompany.shoppingcart.discount;

import com.mycompany.shoppingcart.cart.CartEntry;
import com.mycompany.shoppingcart.product.Product;
import org.junit.Test;

import java.math.BigDecimal;

import static org.junit.Assert.assertEquals;

public class ThreeForTwoDiscountTest {

  @Test
  public void testDiscountWithNoEntries() {
    CartEntry entry = new CartEntry(new Product("Apple", 0.35), 0);
    new ThreeForTwoDiscount().apply(entry);
    assertEquals(BigDecimal.valueOf(0.00).setScale(2, BigDecimal.ROUND_UP), entry.getEntryTotal());
  }

  @Test
  public void testDiscountWithOnlyOneEntry() {
    CartEntry entry = new CartEntry(new Product("Apple", 0.35), 1);
    new ThreeForTwoDiscount().apply(entry);
    assertEquals(BigDecimal.valueOf(0.35).setScale(2, BigDecimal.ROUND_UP), entry.getEntryTotal());
  }

  @Test
  public void testDiscountWithLessThanThreeEntries() {
    CartEntry entry = new CartEntry(new Product("Apple", 0.35), 2);
    new ThreeForTwoDiscount().apply(entry);
    assertEquals(BigDecimal.valueOf(0.35 * 2).setScale(2, BigDecimal.ROUND_UP), entry.getEntryTotal());
  }

  @Test
  public void testDiscountWithExactMultiplesOfThree(){
    int quantity = 9;
    CartEntry entry = new CartEntry(new Product("Apple", 0.35), quantity);
    new ThreeForTwoDiscount().apply(entry);
    assertEquals(BigDecimal.valueOf(0.35 * quantity / 3 * 2).setScale(2, BigDecimal.ROUND_UP), entry.getEntryTotal());

  }
}