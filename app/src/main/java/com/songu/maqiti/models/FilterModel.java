package com.songu.maqiti.models;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 3/1/2017.
 */
public class FilterModel {
    public String mKeyword = "";
    public PriceModel mPrice = new PriceModel("",-1,-1);
    public List<SubCategoryModel> mCategory = new ArrayList<>();
    public List<ColorModel> mColor = new ArrayList<>();

    public void clear()
    {
        mPrice = new PriceModel("",-1,-1);
        mCategory.clear();
        mColor.clear();
        mKeyword = "";
    }
}
