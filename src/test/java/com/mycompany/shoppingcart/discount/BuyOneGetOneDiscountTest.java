package com.mycompany.shoppingcart.discount;

import com.mycompany.shoppingcart.cart.CartEntry;
import com.mycompany.shoppingcart.product.Product;
import org.junit.Test;

import java.math.BigDecimal;

import static org.junit.Assert.assertEquals;

public class BuyOneGetOneDiscountTest {

  @Test
  public void testDiscountWithNoEntries(){
    CartEntry entry = new CartEntry(new Product("Apple", 0.35), 0);
    new BuyOneGetOneDiscount().apply(entry);
    assertEquals(BigDecimal.valueOf(0.0).setScale(2, BigDecimal.ROUND_UP), entry.getEntryTotal());
  }

  @Test
  public void testDiscountWithOnlyOneEntry(){
    CartEntry entry = new CartEntry(new Product("Apple", 0.35), 1);
    new BuyOneGetOneDiscount().apply(entry);
    assertEquals(BigDecimal.valueOf(0.35).setScale(2, BigDecimal.ROUND_UP), entry.getEntryTotal());

  }

  @Test
  public void testDiscountWithExactMultiplesOfTwo(){
    CartEntry entry = new CartEntry(new Product("Apple", 0.35), 10);
    new BuyOneGetOneDiscount().apply(entry);
    assertEquals(BigDecimal.valueOf(0.35 * 5).setScale(2, BigDecimal.ROUND_UP), entry.getEntryTotal());
  }

  @Test
  public void testDiscountWithOddQuantity(){
    CartEntry entry = new CartEntry(new Product("Apple", 0.35), 11);
    new BuyOneGetOneDiscount().apply(entry);
    assertEquals(BigDecimal.valueOf(0.35 * 6).setScale(2, BigDecimal.ROUND_UP), entry.getEntryTotal());
  }
}