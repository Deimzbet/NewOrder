package com.deimzbet.android.neworder;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.deimzbet.android.neworder.database.OrderBaseHelper;
import com.deimzbet.android.neworder.database.OrderCursorWrapper;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static com.deimzbet.android.neworder.database.OrderDBSchema.OrderTable;

public class OrderLab {

    private static OrderLab mLab;

    private Context mContext;
    private SQLiteDatabase mDatabase;

    private OrderLab(Context context) {
        mContext = context.getApplicationContext();
        mDatabase = new OrderBaseHelper(mContext).getWritableDatabase();
    }

    public static OrderLab get(Context context) {
        if (mLab == null) {
            mLab = new OrderLab(context);
        }
        return mLab;
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

    public Order getOrder(UUID id) {
        try (OrderCursorWrapper cursor = queryOrders(
                OrderTable.Cols.UUID + " = ?",
                new String[]{id.toString()}
        )) {
            if (cursor.getCount() == 0) {
                return null;
            }
            cursor.moveToFirst();
            return cursor.getOrder();
        }
    }

    public List<Order> getOrders() {
        List<Order> orders = new ArrayList<>();
        try (OrderCursorWrapper cursor = queryOrders(null, null)) {
            cursor.moveToFirst();
            while (!cursor.isAfterLast()) {
                orders.add(cursor.getOrder());
                cursor.moveToNext();
            }
        }
        return orders;
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
        mDatabase.delete(OrderTable.NAME,
                OrderTable.Cols.UUID + " = ?",
                new String[]{id.toString()});
    }

    private OrderCursorWrapper queryOrders(String whereClause, String[] whereArgs) {
        Cursor cursor = mDatabase.query(
                OrderTable.NAME,
                null,
                whereClause,
                whereArgs,
                null,
                null,
                null
        );
        return new OrderCursorWrapper(cursor);
    }

}
