package com.deimzbet.android.neworder;

import android.content.Context;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class OrderLab {

    private static OrderLab mLab;

    private List<Order> mOrders;

    public static OrderLab get(Context context) {
        if (mLab == null) {
            mLab = new OrderLab(context);
        }
        return mLab;
    }

    private OrderLab(Context context) {
        mOrders = new ArrayList<>();
        for (int i = 0; i < 20; i++) {
            Order order = new Order();
            order.setTitle("Order #" + (i + 1));
            mOrders.add(order);
        }
    }

    public Order getOrder(UUID id) {
        for (Order order : mOrders) {
            if (id.equals(order.getId())) {
                return order;
            }
        }
        return null;
    }

    public List<Order> getOrders() {
        return mOrders;
    }
}
