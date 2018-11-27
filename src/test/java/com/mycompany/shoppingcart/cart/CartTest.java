package com.mycompany.shoppingcart.cart;

import com.mycompany.shoppingcart.discount.BuyOneGetOneDiscount;
import com.mycompany.shoppingcart.discount.Discount;
import com.mycompany.shoppingcart.discount.ThreeForTwoDiscount;
import com.mycompany.shoppingcart.product.Product;
import org.junit.Before;
import org.junit.Test;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

import static org.junit.Assert.assertEquals;

public class CartTest {

  private Product apple;
  private Product banana;
  private Product melon;
  private Product lime;

  @Before
  public void setup() {
    // Create product definitions first
    apple = new Product("Apple", 0.35);
    banana = new Product("Banana", 0.20);
    melon = new Product("Melon", 0.50);
    lime = new Product("Lime", 0.15);
  }

  @Test
  public void testEmptyCartTotal() {
    Cart cart = new Cart();
    cart.addDiscount(melon, setOf(new BuyOneGetOneDiscount()));
    cart.addDiscount(lime, setOf(new ThreeForTwoDiscount()));

    assertEquals(0.00, cart.checkout(), 0.0);
  }

  private static void addToCart(Cart cart, Product apple, long quantity) {
    cart.addEntry(new CartEntry(apple, quantity));
  }

  private static Set<Discount> setOf(Discount... discounts) {
    return Arrays.stream(discounts).collect(Collectors.toSet());
  }

  @Test
  public void testCartWithNoDiscountedItems() {
    Cart cart = new Cart();
    cart.addDiscount(melon, setOf(new BuyOneGetOneDiscount()));
    cart.addDiscount(lime, setOf(new ThreeForTwoDiscount()));

    addToCart(cart, apple, 1);
    addToCart(cart, banana, 2);

    assertEquals(apple.getPrice().add(banana.getPrice().multiply(BigDecimal.valueOf(2))).doubleValue(), cart.checkout(), 0.0);
  }

  @Test
  public void testCartWithOnlyDiscountedItemsButUnderTheRequiredMinimumQuantities() {
    Cart cart = new Cart();
    cart.addDiscount(melon, setOf(new BuyOneGetOneDiscount()));
    cart.addDiscount(lime, setOf(new ThreeForTwoDiscount()));

    addToCart(cart, melon, 1);
    addToCart(cart, lime, 2);

    assertEquals(melon.getPrice().add(lime.getPrice().multiply(BigDecimal.valueOf(2))).doubleValue(), cart.checkout(), 0.0);
  }

  @Test
  public void testCartWithOnlyDiscountedItems() {
    Cart cart = new Cart();
    cart.addDiscount(melon, setOf(new BuyOneGetOneDiscount()));
    cart.addDiscount(lime, setOf(new ThreeForTwoDiscount()));

    addToCart(cart, melon, 1);
    addToCart(cart, lime, 9);

//    BigDecimal limePrice = lime.getPrice().multiply(BigDecimal.valueOf((20 / 3) * 2 + (20 % 3)));
    BigDecimal limePrice = lime.getPrice().multiply(BigDecimal.valueOf(6));
    assertEquals(melon.getPrice().add(limePrice).doubleValue(), cart.checkout(), 0.0);
  }

  @Test
  public void testCartWithSameItemAddedTwice() {
    Cart cart = new Cart();
    cart.addDiscount(melon, setOf(new BuyOneGetOneDiscount()));
    cart.addDiscount(lime, setOf(new ThreeForTwoDiscount()));

    addToCart(cart, apple, 1);
    addToCart(cart, banana, 1);
    addToCart(cart, apple, 3);

    BigDecimal applePrice = apple.getPrice().multiply(BigDecimal.valueOf(4));
    assertEquals(banana.getPrice().add(applePrice).doubleValue(), cart.checkout(), 0.0);
  }

  @Test
  public void testCartWithBothDiscountedAndNonDiscountedItems() {
    Cart cart = new Cart();
    cart.addDiscount(melon, setOf(new BuyOneGetOneDiscount()));
    cart.addDiscount(lime, setOf(new ThreeForTwoDiscount()));

    addToCart(cart, apple, 1);
    addToCart(cart, banana, 2);
    addToCart(cart, apple, 3);
    addToCart(cart, melon, 4);
    addToCart(cart, lime, 5);

    BigDecimal applePrice = apple.getPrice().multiply(BigDecimal.valueOf(4)); // 1.4
    BigDecimal bananaPrice = banana.getPrice().multiply(BigDecimal.valueOf(2)); // 0.4
    BigDecimal melonPrice = melon.getPrice().multiply(BigDecimal.valueOf(2)); // 1.0
    BigDecimal limePrice = lime.getPrice().multiply(BigDecimal.valueOf(4)); // 0.60

    assertEquals(melonPrice.add(limePrice).add(bananaPrice).add(applePrice).doubleValue(), cart.checkout(), 0.0);
  }
}