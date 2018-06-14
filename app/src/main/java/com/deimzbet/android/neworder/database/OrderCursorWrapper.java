package com.deimzbet.android.neworder.database;

import android.database.Cursor;
import android.database.CursorWrapper;

import com.deimzbet.android.neworder.Order;

import java.util.Date;
import java.util.UUID;

public class OrderCursorWrapper extends CursorWrapper {

    public OrderCursorWrapper(Cursor cursor) {
        super(cursor);
    }

    public Order getOrder() {
        String uuidString = getString(getColumnIndex(OrderDBSchema.OrderTable.Cols.UUID));
        String title = getString(getColumnIndex(OrderDBSchema.OrderTable.Cols.TITLE));
        String type = getString(getColumnIndex(OrderDBSchema.OrderTable.Cols.TYPE));
        long date = getLong(getColumnIndex(OrderDBSchema.OrderTable.Cols.DATE));
        int isFinished = getInt(getColumnIndex(OrderDBSchema.OrderTable.Cols.FINISHED));

        Order order = new Order(UUID.fromString(uuidString));
        order.setTitle(title);
        order.setType(type);
        order.setDate(new Date(date));
        order.setFinished(isFinished > 0);

        return order;
    }
}
