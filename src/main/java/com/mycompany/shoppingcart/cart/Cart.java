package com.mycompany.shoppingcart.cart;

import com.mycompany.shoppingcart.discount.Discount;
import com.mycompany.shoppingcart.product.Product;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Has a list of {@link CartEntry}'s and {@link Discount}'s are applied during checkout.
 */
public class Cart {

  private final Map<Product, Set<Discount>> discountMap;
  private final List<CartEntry> entries;

  /**
   * Default constructor
   */
  public Cart() {
    this.entries = new ArrayList<>();
    this.discountMap = new HashMap<>();
  }

  public void addEntry(CartEntry entry) {
    this.entries.add(entry);
  }

  /**
   * Populate the discounted products map
   *
   * @param product   the product
   * @param discounts number of discounts that can be applied to a single product.
   */
  public void addDiscount(Product product, Set<Discount> discounts) {
    this.discountMap.put(product, discounts);
  }

  /**
   * Returns the total price for the cart.
   */
  public double checkout() {
    // Empty cart
    if (entries.size() < 1) {
      return 0;
    }

    // Aggregate duplicates. User can add the same item twice.
    List<CartEntry> nonDuplicateCartEntries = new ArrayList<>();
    for (CartEntry entry : entries) {
      if (nonDuplicateCartEntries.contains(entry)) {
        CartEntry previousEntry = nonDuplicateCartEntries.get(nonDuplicateCartEntries.indexOf(entry));
        previousEntry.addQuantity(entry.getQuantity());
      } else {
        nonDuplicateCartEntries.add(entry);
      }
    }

    // Apply discounts, if applicable products in cart
    for (CartEntry entry : nonDuplicateCartEntries) {
      Product product = entry.getProduct();
      if (isDiscountAvailable(product)) {
        for (Discount discount : discountMap.get(product)) {
          discount.apply(entry);
        }
      }
    }

    // Total the individual cart entries
    return nonDuplicateCartEntries.stream().map(CartEntry::getEntryTotal).collect(Collectors.toList()).stream().reduce(BigDecimal.ZERO, BigDecimal::add).doubleValue();
  }

  private boolean isDiscountAvailable(Product product) {
    return discountMap.containsKey(product) && discountMap.get(product) != null && discountMap.get(product).size() > 0;
  }
}
