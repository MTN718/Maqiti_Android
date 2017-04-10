package com.songu.maqiti.view.autocomplete.rest.model;

import com.google.gson.annotations.SerializedName;


/**
 * Created by DAVID-WORK on 22/07/2015.
 */

public class Terms
{
    @SerializedName("offset")
    private int mOffset;

    @SerializedName("value")
    private String mValue;

    public String getValue()
    {
        return mValue;
    }

    public int getOffset()
    {
        return mOffset;
    }

    @Override
    public String toString()
    {
        return "Terms{" +
                "mOffset=" + mOffset +
                ", mValue='" + mValue + '\'' +
                '}';
    }
}
