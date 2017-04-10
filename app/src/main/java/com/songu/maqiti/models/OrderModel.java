package com.songu.maqiti.models;

import java.util.List;

/**
 * Created by Administrator on 2/22/2017.
 */
public class OrderModel {
    public String mNo;
    public String mName;
    public String mAddress;
    public String mUid;
    public String mPayment;
    public String mStatus;
    public String mPrice;
    public String mCreateDate;
    public boolean isShareLocation;
    public List<ItemModel> mItemBuy;
}
