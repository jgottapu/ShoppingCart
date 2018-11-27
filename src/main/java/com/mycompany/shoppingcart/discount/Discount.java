package com.mycompany.shoppingcart.discount;

import com.mycompany.shoppingcart.cart.CartEntry;

public interface Discount {

  void apply(CartEntry entry);
}
