package com.songu.maqiti.view.autocomplete.rest.model;

import com.google.gson.annotations.SerializedName;


/**
 * Created by DAVID-WORK on 22/07/2015.
 */

public class MatchedSubstring
{
    @SerializedName("length")
    private int mLength;

    @SerializedName("offset")
    private int mOffset;

    public int getLength()
    {
        return mLength;
    }

    public int getOffset()
    {
        return mOffset;
    }

    @Override
    public String toString()
    {
        return "MatchedSubstring{" +
                "mLength=" + mLength +
                ", mOffset=" + mOffset +
                '}';
    }
}
