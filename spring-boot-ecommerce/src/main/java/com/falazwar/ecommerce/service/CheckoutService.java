package com.falazwar.ecommerce.service;

import com.falazwar.ecommerce.dto.Purchase;
import com.falazwar.ecommerce.dto.PurchaseResponse;

public interface CheckoutService {

  PurchaseResponse placeOrder(Purchase purchase);

}
