package com.deimzbet.android.neworder;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.deimzbet.android.neworder.database.OrderBaseHelper;
import com.deimzbet.android.neworder.database.OrderDBSchema;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static com.deimzbet.android.neworder.database.OrderDBSchema.*;

public class OrderLab {

    private static OrderLab mLab;

    private Context mContext;
    private SQLiteDatabase mDatabase;

    public static OrderLab get(Context context) {
        if (mLab == null) {
            mLab = new OrderLab(context);
        }
        return mLab;
    }

    private OrderLab(Context context) {
        mContext = context.getApplicationContext();
        mDatabase = new OrderBaseHelper(mContext).getWritableDatabase();
    }

    public Order getOrder(UUID id) {
        return null;
    }

    public List<Order> getOrders() {
        return new ArrayList<>();
    }

    public void addOrder(Order order) {
        ContentValues values = getContentValues(order);

        mDatabase.insert(OrderTable.NAME, null, values);
    }

    public void updateOrder(Order order) {
        String uuidString = order.getId().toString();
        ContentValues values = getContentValues(order);

        mDatabase.update(OrderTable.NAME, values,
                OrderTable.Cols.UUID + " = ?",
                new String[]{uuidString});
    }

    public void deleteOrder(UUID id) {
    }

    private static ContentValues getContentValues(Order order) {
        ContentValues values = new ContentValues();
        values.put(OrderTable.Cols.UUID, order.getId().toString());
        values.put(OrderTable.Cols.TITLE, order.getTitle());
        values.put(OrderTable.Cols.TYPE, order.getType());
        values.put(OrderTable.Cols.DATE, order.getDate().getTime());
        values.put(OrderTable.Cols.FINISHED, order.isFinished() ? 1 : 0);

        return values;
    }
}
