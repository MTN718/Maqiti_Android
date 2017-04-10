package com.songu.maqiti.doc;

import android.location.Location;

import com.facebook.CallbackManager;
import com.songu.maqiti.models.SubCategoryModel;
import com.songu.maqiti.models.ColorModel;
import com.songu.maqiti.models.CountryModel;
import com.songu.maqiti.models.DepartModel;
import com.songu.maqiti.models.FilterModel;
import com.songu.maqiti.models.ItemModel;
import com.songu.maqiti.models.OrderModel;
import com.songu.maqiti.models.PriceModel;
import com.songu.maqiti.models.ReviewModel;
import com.songu.maqiti.models.SizeModel;
import com.songu.maqiti.models.UserModel;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2/20/2017.
 */
public class Globals {

    public static UserModel mAccount;
    public static Enums.MODE e_mode;

    public static SubCategoryModel currentCategory = null;
    public static ItemModel currentItem = null;
    public static OrderModel currentOrder = null;
    public static CountryModel currentCountry = null;



    public static List<ItemModel> lstNewArrival = new ArrayList<>();
    public static List<SubCategoryModel> lstCategory = new ArrayList<>();
    public static List<SubCategoryModel> featuredCategory = new ArrayList<>();
    public static List<DepartModel> lstDeparts = new ArrayList<>();
    public static List<ItemModel> lstItems = new ArrayList<>();
    public static List<ItemModel> lstSearchItems = new ArrayList<>();
    public static List<ItemModel> lstFavouriteItems = new ArrayList<>();
    public static List<ItemModel> featuredItems = new ArrayList<>();
    public static List<OrderModel> lstOrders = new ArrayList<>();
    public static List<OrderModel> lstHistory = new ArrayList<>();
    public static List<CountryModel> lstCountry = new ArrayList<>();
    public static List<String> lstBanners = new ArrayList<>();
    public static List<String> lstBannerLinks = new ArrayList<>();
    public static List<String> lstSmallBanners = new ArrayList<>();
    public static List<String> lstSmallBanners1 = new ArrayList<>();
    public static List<String> lstSmallBanners2 = new ArrayList<>();

    public static List<ItemModel> lstCarts = new ArrayList<>();
    public static List<ItemModel> lstTopSeller = new ArrayList<>();
    public static List<ItemModel> lstRecommend = new ArrayList<>();
    public static List<ItemModel> lstRelated = new ArrayList<>();



    public static CallbackManager g_callbackManager;

    public static String g_verifyCode = "";

    //Filters
    public static List<SubCategoryModel> lstFilterCategory = new ArrayList<>();
    public static List<ColorModel> lstFilterColor = new ArrayList<>();
    public static List<PriceModel> lstFilterPrice = new ArrayList<>();

    public static FilterModel g_FilterModel = new FilterModel();
    public static List<ReviewModel> lstReviews = new ArrayList<>();
    public static List<SizeModel> lstSizes = new ArrayList<>();
    public static Location currentLocation = null;
    public static int currentThumb;


}
