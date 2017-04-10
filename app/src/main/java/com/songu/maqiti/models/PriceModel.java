package com.songu.maqiti.models;

/**
 * Created by Administrator on 3/1/2017.
 */
public class PriceModel {
    public String mNo;
    public String mName;
    public int mMin;
    public int mMax;

    public PriceModel(String name,int min,int max)
    {
        mName = name;
        mMin = min;
        mMax = max;
    }
}
