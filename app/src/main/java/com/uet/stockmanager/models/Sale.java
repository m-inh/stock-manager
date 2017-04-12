package com.uet.stockmanager.models;

import org.greenrobot.greendao.annotation.ToOne;

public class Sale {

    // todo: add more properties
    private Long timestamp; // <- getMilisecond()

    @ToOne(joinProperty = "productId")
    private Product product;
}
