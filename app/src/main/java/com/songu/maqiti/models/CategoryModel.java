package com.songu.maqiti.models;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 3/21/2017.
 */
public class CategoryModel {
    public String mNo;
    public String mName;
    public String mImage;
    public List<SubCategoryModel> lstSubs = new ArrayList<>();
}
