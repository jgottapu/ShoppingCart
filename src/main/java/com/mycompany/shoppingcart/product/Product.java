package com.mycompany.shoppingcart.product;

import java.math.BigDecimal;

/**
 * Represents a product that can be put in a cart.
 */
public class Product {
  private final String name;
  private final BigDecimal price;

  public Product(String name, double price) {
    this.name = name;
    this.price = BigDecimal.valueOf(price);
  }

  public BigDecimal getPrice() {
    return price;
  }

  private String getName() {
    return name;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (!(o instanceof Product)) return false;

    Product product = (Product) o;

    return getName().equals(product.getName());
  }

  @Override
  public int hashCode() {
    return getName().hashCode();
  }
}
