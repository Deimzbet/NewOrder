package com.deimzbet.android.neworder.database;

public class OrderDBSchema {
    public static final class OrderTable {
        public static final String NAME = "orders";

        public static final class Cols {
            public static final String UUID = "uuid";
            public static final String TITLE = "title";
            public static final String TYPE = "type";
            public static final String DATE = "date";
            public static final String FINISHED = "finished";
        }
    }
}
