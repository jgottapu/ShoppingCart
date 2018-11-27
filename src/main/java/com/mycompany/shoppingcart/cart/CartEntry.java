package com.mycompany.shoppingcart.cart;

import com.mycompany.shoppingcart.product.Product;

import java.math.BigDecimal;

/**
 * Represents a single entry in the cart that can have multiple quantity.
 */
public class CartEntry {
  private final Product product;
  private BigDecimal entryTotal;
  private long quantity;

  public CartEntry(Product product, long quantity) {
    this.product = product;
    this.quantity = quantity;
    this.entryTotal = product.getPrice().multiply(BigDecimal.valueOf(quantity));
  }

  public long getQuantity() {
    return quantity;
  }

  void addQuantity(long quantity) {
    this.quantity += quantity;
    this.entryTotal = product.getPrice().multiply(BigDecimal.valueOf(this.quantity));
  }

  @Override
  public int hashCode() {
    return getProduct().hashCode();
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (!(o instanceof CartEntry)) return false;

    CartEntry cartEntry = (CartEntry) o;

    return getProduct().equals(cartEntry.getProduct());
  }

  public Product getProduct() {
    return product;
  }

  public BigDecimal getEntryTotal() {
    return entryTotal;
  }

  public void setEntryTotal(BigDecimal price) {
    this.entryTotal = price;
  }
}
