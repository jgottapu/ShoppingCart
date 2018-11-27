package com.mycompany.shoppingcart;

import com.mycompany.shoppingcart.cart.Cart;
import com.mycompany.shoppingcart.cart.CartEntry;
import com.mycompany.shoppingcart.discount.BuyOneGetOneDiscount;
import com.mycompany.shoppingcart.discount.Discount;
import com.mycompany.shoppingcart.discount.ThreeForTwoDiscount;
import com.mycompany.shoppingcart.product.Product;

import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Example main class to illustrate how to run the program.
 */
public class Main {

  public static void main(String[] args) {
    // Create product definitions first
    Product apple = new Product("Apple", 0.35);
    Product banana = new Product("Banana", 0.20);
    Product melon = new Product("Melon", 0.50);
    Product lime = new Product("Lime", 0.15);

    // Create a cart
    Cart cart = new Cart();

    // Add discounts, if any for the above products
    cart.addDiscount(melon, setOf(new BuyOneGetOneDiscount()));
    cart.addDiscount(lime, setOf(new ThreeForTwoDiscount()));

    // Start shopping
    addToCart(cart, apple, 1);
    addToCart(cart, banana, 2);
    addToCart(cart, apple, 3);
    addToCart(cart, melon, 4);
    addToCart(cart, lime, 5);

    System.out.println("Total price for the cart: " + cart.checkout());
  }

  private static void addToCart(Cart cart, Product apple, long quantity) {
    cart.addEntry(new CartEntry(apple, quantity));
  }

  private static Set<Discount> setOf(Discount... discounts) {
    return Arrays.stream(discounts).collect(Collectors.toSet());
  }
}
