package com.songu.maqiti.doc;

/**
 * Created by Administrator on 2/20/2017.
 */
public class Config {
    //public static String mBaseUrl = "http://192.168.1.112:88/maqiti/index.php?c=WebserviceController";
    //public static String mImageBaseUrl = "http://192.168.1.112:88/maqiti/";

    public static String mBaseUrl = "http://hlw.uqh.mybluehost.me/index.php?c=WebserviceController";
    public static String mImageBaseUrl = "http://hlw.uqh.mybluehost.me/";

    public static String mRegisterUrl = mBaseUrl + "&m=onRegister";
    public static String mLoginUrl = mBaseUrl + "&m=onLogin";
    public static String mFacebookLoginUrl = mBaseUrl + "&m=onFacebookLogin";
    public static String mPopularUrl = mBaseUrl + "&m=onLoadPopular";
    public static String mUpdateProfileUrl = mBaseUrl + "&m=onUpdateProfile";
    public static String mLoadItemsCategory = mBaseUrl + "&m=onLoadItems";
    public static String mSearchItemsCategory = mBaseUrl + "&m=onSearchItems";
    public static String mSearchItemsKeyword = mBaseUrl + "&m=onSearchItemsKeyword";
    public static String mLoadItemInfo = mBaseUrl + "&m=onLoadItemInfo";
    public static String mAddFavouriteUrl = mBaseUrl + "&m=onAddItemFavourite";
    public static String mRemoveFavouriteUrl = mBaseUrl + "&m=onRemoveItemFavourite";
    public static String mLoadFavourites = mBaseUrl + "&m=onLoadFavourites";
    public static String mAddOrderUrl = mBaseUrl + "&m=onAddOrder";
    public static String mLoadOrdersUrl = mBaseUrl + "&m=onLoadOrders";
    public static String mDeleteOrderUrl = mBaseUrl + "&m=onDeleteOrder";
    public static String mOrderCompleteUrl = mBaseUrl + "&m=onUpdateOrderComplete";
    public static String mLoadCountryUrl = mBaseUrl + "&m=onLoadCountry";
    public static String mLoadOrderDetail = mBaseUrl + "&m=onLoadOrderById";
    public static String mForgetSendCode = mBaseUrl + "&m=onSendCode";
    public static String mForgetUpdatePassword = mBaseUrl + "&m=onUpdatePassword";
    public static String mLoadFilter = mBaseUrl + "&m=onLoadFilter";
    public static String mAddReview = mBaseUrl + "&m=onAddReview";

}

