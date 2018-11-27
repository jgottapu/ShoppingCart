package com.mycompany.shoppingcart.discount;

import com.mycompany.shoppingcart.cart.CartEntry;

/**
 * Interface to be implemented to provide discount for cart entries.
 */
public interface Discount {

  void apply(CartEntry entry);
}
