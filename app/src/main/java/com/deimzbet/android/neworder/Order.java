package com.deimzbet.android.neworder;

import java.io.Serializable;
import java.util.Date;
import java.util.UUID;

public class Order implements Serializable{

    private UUID mId;
    private String mTitle;
    private String mType;
    private Date mDate;
    private boolean isFinished;

    public Order() {
        mId = UUID.randomUUID();
        mDate = new Date();
    }

    public UUID getId() {
        return mId;
    }

    public String getTitle() {
        return mTitle;
    }

    public void setTitle(String title) {
        mTitle = title;
    }

    public String getType() {
        return mType;
    }

    public void setType(String type) {
        mType = type;
    }

    public Date getDate() {
        return mDate;
    }

    public void setDate(Date date) {
        mDate = date;
    }

    public boolean isFinished() {
        return isFinished;
    }

    public void setFinished(boolean finished) {
        isFinished = finished;
    }
}

