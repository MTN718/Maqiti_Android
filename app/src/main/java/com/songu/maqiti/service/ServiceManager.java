package com.songu.maqiti.service;

import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.songu.maqiti.doc.Config;
import com.songu.maqiti.doc.Globals;
import com.songu.maqiti.models.CategoryModel;
import com.songu.maqiti.models.SubCategoryModel;
import com.songu.maqiti.models.ColorModel;
import com.songu.maqiti.models.CountryModel;
import com.songu.maqiti.models.DepartModel;
import com.songu.maqiti.models.ItemModel;
import com.songu.maqiti.models.OrderModel;
import com.songu.maqiti.models.ReviewModel;
import com.songu.maqiti.models.SizeModel;
import com.songu.maqiti.models.UserModel;
import com.songu.maqiti.utils.HttpUtil;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.FileNotFoundException;
import java.util.ArrayList;

/**
 * Created by Administrator on 2/20/2017.
 */
public class ServiceManager {
    public static void serviceRegister(final IServiceResult caller, final UserModel iModel)
    {
        RequestParams req = new RequestParams();
        req.put("user",iModel.mUser);
        req.put("password",iModel.mPassword);
        req.put("email",iModel.mEmail);
        req.put("phone",iModel.mPhone);
        req.put("address",iModel.mAddress);
        req.put("lat",iModel.mLat);
        req.put("lon",iModel.mLon);

        HttpUtil.post(Config.mRegisterUrl,req,new AsyncHttpResponseHandler() {
            public void onFailure(Throwable paramThrowable) {
                String s = "fail";
                caller.onResponse(400);

            }

            public void onFinish() {
                String s = "finish";
            }

            public void onSuccess(String paramString) {  //that is return when success..
                try {
                    JSONObject localJSONObject1 = new JSONObject(paramString);
                    if (localJSONObject1.has("response")) {
                        int response = localJSONObject1.getInt("response");
                        if (response == 200) {
                            UserModel mModel = new UserModel();
                            mModel.mCreateDate = localJSONObject1.getString("create");
                            mModel.mPhoto = localJSONObject1.getString("image");
                            mModel.mEmail = localJSONObject1.getString("email");
                            mModel.mUser = localJSONObject1.getString("user");
                            mModel.mNo = localJSONObject1.getString("no");
                            mModel.mPhone = localJSONObject1.getString("phone");
                            mModel.mAddress = localJSONObject1.getString("address");
                            mModel.mPassword = iModel.mPassword;

                            Globals.mAccount = mModel;
                            caller.onResponse(200);

                        } else if (response == 400) {
                            caller.onResponse(400);
                        }
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                    caller.onResponse(400);
                }

            }
        });
    }
    public static void serviceFacebookLogin(final IServiceResult caller,final UserModel pModel)
    {
        RequestParams req = new RequestParams();
        req.put("user",pModel.mEmail);
        req.put("name",pModel.mUser);
        req.put("password",pModel.mPassword);

        HttpUtil.post(Config.mFacebookLoginUrl,req,new AsyncHttpResponseHandler() {
            public void onFailure(Throwable paramThrowable) {
                String s = "fail";
                caller.onResponse(400);

            }

            public void onFinish() {
                String s = "finish";
            }

            public void onSuccess(String paramString) {  //that is return when success..
                try {
                    JSONObject localJSONObject1 = new JSONObject(paramString);
                    if (localJSONObject1.has("response")) {
                        int response = localJSONObject1.getInt("response");
                        if (response == 200) {
                            UserModel mModel = new UserModel();
                            mModel.mCreateDate = localJSONObject1.getString("create");
                            mModel.mPhoto = localJSONObject1.getString("image");
                            mModel.mEmail = localJSONObject1.getString("email");
                            mModel.mUser = localJSONObject1.getString("user");
                            mModel.mNo = localJSONObject1.getString("no");
                            mModel.mPhone = localJSONObject1.getString("phone");
                            mModel.mAddress = localJSONObject1.getString("address");
                            mModel.mPassword = pModel.mPassword;

                            Globals.mAccount = mModel;
                            caller.onResponse(200);

                        } else if (response == 400) {
                            caller.onResponse(400);
                        }
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        });
    }
    public static void serviceLogin(final IServiceResult caller,String user,final String password)
    {
        RequestParams req = new RequestParams();
        req.put("user",user);
        req.put("password",password);

        HttpUtil.post(Config.mLoginUrl,req,new AsyncHttpResponseHandler() {
            public void onFailure(Throwable paramThrowable) {
                String s = "fail";
                caller.onResponse(400);

            }

            public void onFinish() {
                String s = "finish";
            }

            public void onSuccess(String paramString) {  //that is return when success..
                try {
                    JSONObject localJSONObject1 = new JSONObject(paramString);
                    if (localJSONObject1.has("response")) {
                        int response = localJSONObject1.getInt("response");
                        if (response == 200) {
                            UserModel mModel = new UserModel();
                            mModel.mCreateDate = localJSONObject1.getString("create");
                            mModel.mPhoto = localJSONObject1.getString("image");
                            mModel.mEmail = localJSONObject1.getString("email");
                            mModel.mUser = localJSONObject1.getString("user");
                            mModel.mNo = localJSONObject1.getString("no");
                            mModel.mPhone = localJSONObject1.getString("phone");
                            mModel.mAddress = localJSONObject1.getString("address");
                            mModel.mPassword = password;

                            Globals.mAccount = mModel;
                            caller.onResponse(200);

                        } else if (response == 400) {
                            caller.onResponse(400);
                        }
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        });
    }
    public static void serviceUpdateProfile(final IServiceResult caller,UserModel mModel)
    {
        RequestParams req = new RequestParams();

        req.put("no",mModel.mNo);
        req.put("user",mModel.mUser);
        req.put("phone",mModel.mPhone);
        req.put("address",mModel.mAddress);
        try {
            req.put("photo",mModel.mPhotoFile);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        HttpUtil.post(Config.mUpdateProfileUrl,req,new AsyncHttpResponseHandler() {
            public void onFailure(Throwable paramThrowable) {
                String s = "fail";
                caller.onResponse(400);

            }

            public void onFinish() {
                String s = "finish";
            }

            public void onSuccess(String paramString) {  //that is return when success..
                try {
                    JSONObject localJSONObject1 = new JSONObject(paramString);
                    if (localJSONObject1.has("response")) {
                        int response = localJSONObject1.getInt("response");
                        if (response == 200) {
                            UserModel mModel = new UserModel();
                            mModel.mCreateDate = localJSONObject1.getString("create");
                            mModel.mPhoto = localJSONObject1.getString("image");
                            mModel.mEmail = localJSONObject1.getString("email");
                            mModel.mUser = localJSONObject1.getString("user");
                            mModel.mNo = localJSONObject1.getString("no");
                            mModel.mPhone = localJSONObject1.getString("phone");
                            mModel.mAddress = localJSONObject1.getString("address");

                            Globals.mAccount = mModel;
                            caller.onResponse(200);

                        } else if (response == 400) {
                            caller.onResponse(400);
                        }
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                    caller.onResponse(400);
                }

            }
        });
    }
    public static void serviceLoadFilter(final IServiceResult caller)
    {
        RequestParams param = new RequestParams();
        param.put("category",Globals.currentCategory.mNo);
        HttpUtil.post(Config.mLoadFilter,param,new AsyncHttpResponseHandler() {
            public void onFailure(Throwable paramThrowable) {
                String s = "fail";
                caller.onResponse(400);

            }

            public void onFinish() {
                String s = "finish";
            }

            public void onSuccess(String paramString) {  //that is return when success..
                try {
                    JSONObject localJSONObject1 = new JSONObject(paramString);
                    if (localJSONObject1.has("response")) {
                        int response = localJSONObject1.getInt("response");
                        if (response == 200) {
                            Globals.lstFilterColor.clear();
                            Globals.lstFilterCategory.clear();

                            JSONArray categoryArray = localJSONObject1.getJSONArray("brands");
                            for (int i = 0;i < categoryArray.length();i++)
                            {
                                JSONObject categoryObject = categoryArray.getJSONObject(i);
                                SubCategoryModel cModel = new SubCategoryModel();
                                cModel.mName = categoryObject.getString("brand");
                                Globals.lstFilterCategory.add(cModel);
                            }

                            JSONArray colorArray = localJSONObject1.getJSONArray("colors");
                            for (int i = 0;i < colorArray.length();i++)
                            {
                                JSONObject categoryObject = colorArray.getJSONObject(i);
                                ColorModel cModel = new ColorModel();
                                cModel.mNo = categoryObject.getString("no");
                                cModel.mName = categoryObject.getString("name");
                                Globals.lstFilterColor.add(cModel);
                            }

                            caller.onResponse(200);

                        } else if (response == 400) {
                            caller.onResponse(400);
                        }
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                    if (caller != null)
                        caller.onResponse(400);
                }

            }
        });
    }
    public static void serviceLoadPopular(final IServiceResult caller)
    {
        Globals.lstBanners.clear();
        Globals.lstSmallBanners.clear();
        Globals.lstSmallBanners1.clear();
        Globals.lstSmallBanners2.clear();
        Globals.lstBannerLinks.clear();
        Globals.featuredCategory.clear();
        Globals.featuredItems.clear();
        HttpUtil.post(Config.mPopularUrl,new AsyncHttpResponseHandler() {
            public void onFailure(Throwable paramThrowable) {
                String s = "fail";
                caller.onResponse(400);

            }

            public void onFinish() {
                String s = "finish";
            }

            public void onSuccess(String paramString) {  //that is return when success..
                try {
                    JSONObject localJSONObject1 = new JSONObject(paramString);
                    if (localJSONObject1.has("response")) {
                        int response = localJSONObject1.getInt("response");
                        if (response == 200) {
                            Globals.lstNewArrival.clear();
                            Globals.lstCategory.clear();
                            Globals.lstDeparts.clear();
                            Globals.lstTopSeller.clear();
                            JSONArray newsArray = localJSONObject1.getJSONArray("news");
                            for (int i = 0;i < newsArray.length();i++)
                            {
                                JSONObject itemObject = newsArray.getJSONObject(i);
                                ItemModel iModel = parseItemModel(itemObject);
                                Globals.lstNewArrival.add(iModel);
                            }
                            JSONArray departArray = localJSONObject1.getJSONArray("departs");
                            for (int i = 0;i < departArray.length();i++)
                            {
                                JSONObject departObject = departArray.getJSONObject(i);
                                DepartModel dModel = new DepartModel();
                                dModel.mNo = departObject.getString("no");
                                dModel.mName = departObject.getString("name");
                                dModel.lstCategory = new ArrayList<CategoryModel>();

                                JSONArray bategoryArray = departObject.getJSONArray("bategory");
                                for (int j = 0;j < bategoryArray.length();j++)
                                {
                                    JSONObject bategoryObject = bategoryArray.getJSONObject(j);
                                    CategoryModel cModel = new CategoryModel();
                                    cModel.mNo = bategoryObject.getString("no");
                                    cModel.mName = bategoryObject.getString("bategory");
                                    cModel.mImage = bategoryObject.getString("image");
                                    cModel.lstSubs = new ArrayList<SubCategoryModel>();

                                    JSONArray categoryArray = bategoryObject.getJSONArray("category");
                                    for (int k = 0;k < categoryArray.length();k++)
                                    {
                                        JSONObject categoryObject = categoryArray.getJSONObject(k);
                                        SubCategoryModel sModel = new SubCategoryModel();
                                        sModel.mNo = categoryObject.getString("no");
                                        sModel.mName = categoryObject.getString("name");
                                        sModel.mImage = categoryObject.getString("image");
                                        sModel.mDepart = categoryObject.getString("depart");
                                        Globals.lstCategory.add(sModel);
                                        cModel.lstSubs.add(sModel);
                                    }
                                    dModel.lstCategory.add(cModel);
                                }
                                Globals.lstDeparts.add(dModel);
                            }

                            JSONArray categoryArray = localJSONObject1.getJSONArray("featurecategory");
                            for (int k = 0;k < categoryArray.length();k++)
                            {
                                JSONObject categoryObject = categoryArray.getJSONObject(k);
                                SubCategoryModel cModel = new SubCategoryModel();
                                cModel.mNo = categoryObject.getString("no");
                                cModel.mName = categoryObject.getString("name");
                                cModel.mImage = categoryObject.getString("image");
                                cModel.mDepart = categoryObject.getString("depart");
                                Globals.featuredCategory.add(cModel);
                            }

                            JSONArray itemArray = localJSONObject1.getJSONArray("featureitems");
                            for (int k = 0;k < itemArray.length();k++)
                            {
                                ItemModel iModel = parseItemModel(itemArray.getJSONObject(k));
                                Globals.featuredItems.add(iModel);
                            }

                            JSONArray topSellerArray = localJSONObject1.getJSONArray("topseller");
                            for (int i = 0; i < topSellerArray.length(); i++) {
                                JSONObject itemObject = topSellerArray.getJSONObject(i);
                                ItemModel iModel = parseItemModel(itemObject);
                                Globals.lstTopSeller.add(iModel);
                            }
                            if (Globals.lstRecommend.size() == 0) {
                                JSONArray recommendArray = localJSONObject1.getJSONArray("recommend");
                                for (int i = 0; i < recommendArray.length(); i++) {
                                    JSONObject itemObject = recommendArray.getJSONObject(i);
                                    ItemModel iModel = parseItemModel(itemObject);
                                    Globals.lstRecommend.add(iModel);
                                }
                            }

                            String banner1 = localJSONObject1.getString("banner1");
                            String banner2 = localJSONObject1.getString("banner2");
                            String banner3 = localJSONObject1.getString("banner3");
                            String banner4 = localJSONObject1.getString("banner4");

                            String banner5 = localJSONObject1.getString("banner5");
                            String banner6 = localJSONObject1.getString("banner6");
                            String banner7 = localJSONObject1.getString("banner7");

                            String banner8 = localJSONObject1.getString("banner8");
                            String banner9 = localJSONObject1.getString("banner9");
                            String banner10 = localJSONObject1.getString("banner10");

                            String banner11 = localJSONObject1.getString("banner11");
                            String banner12 = localJSONObject1.getString("banner12");
                            String banner13 = localJSONObject1.getString("banner13");

                            String link1 = localJSONObject1.getString("bannerlink1");
                            String link2 = localJSONObject1.getString("bannerlink2");
                            String link3 = localJSONObject1.getString("bannerlink3");
                            String link4 = localJSONObject1.getString("bannerlink4");

                            Globals.lstBannerLinks.add(link1);Globals.lstBannerLinks.add(link2);
                            Globals.lstBannerLinks.add(link3);Globals.lstBannerLinks.add(link4);

                            Globals.lstBanners.add(banner1);Globals.lstBanners.add(banner2);
                            Globals.lstBanners.add(banner3);Globals.lstBanners.add(banner4);

                            Globals.lstSmallBanners.add(banner5);Globals.lstSmallBanners.add(banner6);
                            Globals.lstSmallBanners.add(banner7);

                            Globals.lstSmallBanners1.add(banner8);Globals.lstSmallBanners1.add(banner9);
                            Globals.lstSmallBanners1.add(banner10);

                            Globals.lstSmallBanners2.add(banner11);Globals.lstSmallBanners2.add(banner12);
                            Globals.lstSmallBanners2.add(banner13);



                            caller.onResponse(200);

                        } else if (response == 400) {
                            caller.onResponse(400);
                        }
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                    if (caller != null)
                        caller.onResponse(400);
                }

            }
        });
    }
    public static void serviceSendCode(final IServiceResult caller,String email)
    {
        RequestParams param = new RequestParams();
        param.put("email",email);

        HttpUtil.post(Config.mForgetSendCode,param,new AsyncHttpResponseHandler() {
            public void onFailure(Throwable paramThrowable) {
                String s = "fail";
                caller.onResponse(400);

            }

            public void onFinish() {
                String s = "finish";
            }

            public void onSuccess(String paramString) {  //that is return when success..
                try {
                    JSONObject localJSONObject1 = new JSONObject(paramString);
                    if (localJSONObject1.has("response")) {
                        int response = localJSONObject1.getInt("response");
                        if (response == 200) {
                            Globals.g_verifyCode = localJSONObject1.getString("code");
                            caller.onResponse(200);

                        } else if (response == 400) {
                            caller.onResponse(400);
                        }
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        });



    }
    public static void serviceUpdatePassword(final IServiceResult caller,String email,String password)
    {
        RequestParams param = new RequestParams();
        param.put("email",email);
        param.put("password",password);

        HttpUtil.post(Config.mForgetUpdatePassword,param,new AsyncHttpResponseHandler() {
            public void onFailure(Throwable paramThrowable) {
                String s = "fail";
                caller.onResponse(401);

            }

            public void onFinish() {
                String s = "finish";
            }

            public void onSuccess(String paramString) {  //that is return when success..
                try {
                    JSONObject localJSONObject1 = new JSONObject(paramString);
                    if (localJSONObject1.has("response")) {
                        int response = localJSONObject1.getInt("response");
                        if (response == 200) {
                            caller.onResponse(201);

                        } else if (response == 400) {
                            caller.onResponse(401);
                        }
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        });



    }
    public static void serviceLoadItemsUnderCategory(final IServiceResult caller)
    {
        RequestParams param = new RequestParams();
        Globals.lstSearchItems.clear();
        param.put("category",Globals.currentCategory.mNo);
        param.put("count",String.valueOf(Globals.lstItems.size()));

        HttpUtil.post(Config.mLoadItemsCategory,param,new AsyncHttpResponseHandler() {
            public void onFailure(Throwable paramThrowable) {
                String s = "fail";
                caller.onResponse(400);

            }

            public void onFinish() {
                String s = "finish";
            }

            public void onSuccess(String paramString) {  //that is return when success..
                try {
                    JSONObject localJSONObject1 = new JSONObject(paramString);
                    if (localJSONObject1.has("response")) {
                        int response = localJSONObject1.getInt("response");
                        if (response == 200) {
                            JSONArray newsArray = localJSONObject1.getJSONArray("items");
                            for (int i = 0;i < newsArray.length();i++)
                            {
                                JSONObject itemObject = newsArray.getJSONObject(i);
                                ItemModel iModel = parseItemModel(itemObject);
                                Globals.lstItems.add(iModel);
                            }
                            caller.onResponse(200);

                        } else if (response == 400) {
                            caller.onResponse(400);
                        }
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        });
    }
    public static void serviceRemoveFavourite(final IServiceResult caller)
    {
        RequestParams param = new RequestParams();
        param.put("iid",Globals.currentItem.mId);
        param.put("uid",Globals.mAccount.mNo);
        HttpUtil.post(Config.mRemoveFavouriteUrl,param,new AsyncHttpResponseHandler() {
            public void onFailure(Throwable paramThrowable) {
                String s = "fail";

            }

            public void onFinish() {
                String s = "finish";
            }

            public void onSuccess(String paramString) {  //that is return when success..
                String s = "success";
            }
        });
    }
    public static void serviceAddFavourite(final IServiceResult caller)
    {
        RequestParams param = new RequestParams();
        param.put("iid",Globals.currentItem.mId);
        param.put("uid",Globals.mAccount.mNo);
        HttpUtil.post(Config.mAddFavouriteUrl,param,new AsyncHttpResponseHandler() {
            public void onFailure(Throwable paramThrowable) {
                String s = "fail";

            }

            public void onFinish() {
                String s = "finish";
            }

            public void onSuccess(String paramString) {  //that is return when success..
                String s = "success";
            }
        });
    }
    public static void serviceOrderMake(final IServiceResult caller, OrderModel oModel)
    {
        RequestParams param = new RequestParams();
        param.put("count",String.valueOf(oModel.mItemBuy.size()));
        param.put("uid",Globals.mAccount.mNo);
        param.put("price",oModel.mPrice);
        param.put("address",oModel.mAddress);
        param.put("payment",oModel.mPayment);
        for (int i = 0;i < oModel.mItemBuy.size();i++)
        {
            param.put("item" + String.valueOf(i),oModel.mItemBuy.get(i).mId);
            param.put("size" + String.valueOf(i),oModel.mItemBuy.get(i).mCurrentSize);
        }
        if (oModel.isShareLocation) {
            if (Globals.currentLocation != null) {
                param.put("lat", String.valueOf(Globals.currentLocation.getLatitude()));
                param.put("lon", String.valueOf(Globals.currentLocation.getLongitude()));
            } else {
                param.put("lat", "42");
                param.put("lon", "128");
            }
        }
        else
        {
            param.put("lat", "-1");
            param.put("lon", "-1");
        }

        HttpUtil.post(Config.mAddOrderUrl,param,new AsyncHttpResponseHandler() {
            public void onFailure(Throwable paramThrowable) {
                String s = "fail";
                caller.onResponse(400);

            }

            public void onFinish() {
                String s = "finish";
            }

            public void onSuccess(String paramString) {  //that is return when success..
                try {
                    JSONObject localJSONObject1 = new JSONObject(paramString);
                    if (localJSONObject1.has("response")) {
                        int response = localJSONObject1.getInt("response");
                        if (response == 200) {
                            caller.onResponse(200);
                        } else if (response == 400) {
                            caller.onResponse(400);
                        }
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                    caller.onResponse(400);
                }
            }
        });
    }
    public static void serviceLoadCountry(final IServiceResult caller)
    {
        Globals.lstCountry.clear();
        HttpUtil.post(Config.mLoadCountryUrl,new AsyncHttpResponseHandler() {
            public void onFailure(Throwable paramThrowable) {
                String s = "fail";
                caller.onResponse(400);

            }

            public void onFinish() {
                String s = "finish";
            }

            public void onSuccess(String paramString) {  //that is return when success..
                try {
                    JSONObject localJSONObject1 = new JSONObject(paramString);
                    if (localJSONObject1.has("response")) {
                        int response = localJSONObject1.getInt("response");
                        if (response == 200) {
                            JSONArray countryArray = localJSONObject1.getJSONArray("countrys");
                            for (int i = 0;i < countryArray.length();i++)
                            {
                                JSONObject countryObject = countryArray.getJSONObject(i);
                                CountryModel cModel = new CountryModel();
                                cModel.mNo = countryObject.getString("no");
                                cModel.mName = countryObject.getString("name");
                                cModel.mImage = countryObject.getString("image");
                                Globals.lstCountry.add(cModel);
                            }
                            caller.onResponse(200);
                        } else if (response == 400) {
                            caller.onResponse(400);
                        }
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                    caller.onResponse(400);
                }

            }
        });
    }
    public static void serviceLoadItemInfo(final IServiceResult caller)
    {
        RequestParams param = new RequestParams();
        if (Globals.mAccount == null)
            param.put("uid","-1");
        else param.put("uid",Globals.mAccount.mNo);
        param.put("iid",Globals.currentItem.mId);
        Globals.lstRelated.clear();
        HttpUtil.post(Config.mLoadItemInfo,param,new AsyncHttpResponseHandler() {
            public void onFailure(Throwable paramThrowable) {
                String s = "fail";
                caller.onResponse(400);

            }

            public void onFinish() {
                String s = "finish";
            }

            public void onSuccess(String paramString) {  //that is return when success..
                try {
                    Globals.lstReviews.clear();
                    Globals.lstSizes.clear();
                    JSONObject localJSONObject1 = new JSONObject(paramString);
                    if (localJSONObject1.has("response")) {
                        int response = localJSONObject1.getInt("response");
                        if (response == 200) {
                            int fav = localJSONObject1.getInt("favourite");
                            JSONArray reviewArray = localJSONObject1.getJSONArray("reviews");
                            for (int i = 0;i < reviewArray.length();i++)
                            {
                                JSONObject object = reviewArray.getJSONObject(i);
                                ReviewModel rModel = new ReviewModel();
                                rModel.mComment = object.getString("comment");
                                rModel.mNo = object.getString("no");
                                rModel.mReview = object.getString("star");
                                rModel.mUser = object.getString("uname");

                                Globals.lstReviews.add(rModel);
                            }

                            reviewArray = localJSONObject1.getJSONArray("sizes");
                            for (int i = 0;i < reviewArray.length();i++)
                            {
                                JSONObject object = reviewArray.getJSONObject(i);
                                SizeModel rModel = new SizeModel();
                                rModel.mSize = object.getString("name");
                                rModel.mNo = object.getString("no");
                                Globals.lstSizes.add(rModel);
                            }

                            JSONArray relateArray = localJSONObject1.getJSONArray("related");

                            for (int i = 0;i < relateArray.length();i++)
                            {
                                JSONObject object = relateArray.getJSONObject(i);
                                ItemModel rModel = new ItemModel();
                                rModel = parseItemModel(object);
                                Globals.lstRelated.add(rModel);
                            }

                            if (fav == 1)
                                caller.onResponse(200);
                            else
                                caller.onResponse(400);
                        } else if (response == 400) {
                            caller.onResponse(400);
                        }
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                    caller.onResponse(400);
                }

            }
        });
    }
    public static void serviceLoadFavouriteItems(final IServiceResult caller)
    {
        RequestParams param = new RequestParams();
        param.put("uid",Globals.mAccount.mNo);
        Globals.lstFavouriteItems.clear();
        HttpUtil.post(Config.mLoadFavourites,param,new AsyncHttpResponseHandler() {
            public void onFailure(Throwable paramThrowable) {
                String s = "fail";
                caller.onResponse(400);

            }

            public void onFinish() {
                String s = "finish";
            }

            public void onSuccess(String paramString) {  //that is return when success..
                try {
                    JSONObject localJSONObject1 = new JSONObject(paramString);
                    if (localJSONObject1.has("response")) {
                        int response = localJSONObject1.getInt("response");
                        if (response == 200) {
                            JSONArray newsArray = localJSONObject1.getJSONArray("items");
                            for (int i = 0;i < newsArray.length();i++)
                            {
                                JSONObject itemObject = newsArray.getJSONObject(i);
                                ItemModel iModel = parseItemModel(itemObject);
                                Globals.lstFavouriteItems.add(iModel);
                            }
                            caller.onResponse(200);

                        } else if (response == 400) {
                            caller.onResponse(400);
                        }
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        });
    }

    public static void serviceSearchByKeyword(final IServiceResult caller,String keyword)
    {
        RequestParams param = new RequestParams();
        param.put("keyword",keyword);
        //Add Filter
        param.put("categorycount",String.valueOf(Globals.g_FilterModel.mCategory.size()));
        for (int i = 0;i < Globals.g_FilterModel.mCategory.size();i++)
        {
            param.put("category" + String.valueOf(i),Globals.g_FilterModel.mCategory.get(i).mName);
        }

        param.put("colorcount",String.valueOf(Globals.g_FilterModel.mColor.size()));
        for (int i = 0;i < Globals.g_FilterModel.mColor.size();i++)
        {
            param.put("color" + String.valueOf(i),Globals.g_FilterModel.mColor.get(i).mNo);
        }
        param.put("minprice",String.valueOf(Globals.g_FilterModel.mPrice.mMin));
        param.put("maxprice",String.valueOf(Globals.g_FilterModel.mPrice.mMax));

        param.put("count",String.valueOf(Globals.lstSearchItems.size()));

        HttpUtil.post(Config.mSearchItemsKeyword,param,new AsyncHttpResponseHandler() {
            public void onFailure(Throwable paramThrowable) {
                String s = "fail";
                caller.onResponse(400);

            }

            public void onFinish() {
                String s = "finish";
            }

            public void onSuccess(String paramString) {  //that is return when success..
                try {
                    JSONObject localJSONObject1 = new JSONObject(paramString);
                    if (localJSONObject1.has("response")) {
                        int response = localJSONObject1.getInt("response");
                        if (response == 200) {
                            JSONArray newsArray = localJSONObject1.getJSONArray("items");
                            for (int i = 0;i < newsArray.length();i++)
                            {
                                JSONObject itemObject = newsArray.getJSONObject(i);
                                ItemModel iModel = parseItemModel(itemObject);
                                Globals.lstSearchItems.add(iModel);
                            }
                            caller.onResponse(200);

                        } else if (response == 400) {
                            caller.onResponse(400);
                        }
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        });
    }

    public static void serviceAddReview(final IServiceResult caller,String star,String comment)
    {
        RequestParams param = new RequestParams();
        param.put("oid",Globals.currentOrder.mNo);
        param.put("star",star);
        param.put("uid",Globals.mAccount.mNo);
        param.put("comment",comment);

        HttpUtil.post(Config.mAddReview,param,new AsyncHttpResponseHandler() {
            public void onFailure(Throwable paramThrowable) {
                String s = "fail";
                caller.onResponse(400);

            }

            public void onFinish() {
                String s = "finish";
            }

            public void onSuccess(String paramString) {  //that is return when success..
                caller.onResponse(200);
            }
        });
    }
    public static void serviceSearchItems(final IServiceResult caller,String keyword,String minValue,String maxValue)
    {
        RequestParams param = new RequestParams();
        param.put("category",Globals.currentCategory.mNo);
        param.put("keyword",keyword);
        param.put("min",minValue);
        param.put("max",maxValue);
        Globals.lstSearchItems.clear();
        HttpUtil.post(Config.mSearchItemsCategory,param,new AsyncHttpResponseHandler() {
            public void onFailure(Throwable paramThrowable) {
                String s = "fail";
                caller.onResponse(400);

            }

            public void onFinish() {
                String s = "finish";
            }

            public void onSuccess(String paramString) {  //that is return when success..
                try {
                    JSONObject localJSONObject1 = new JSONObject(paramString);
                    if (localJSONObject1.has("response")) {
                        int response = localJSONObject1.getInt("response");
                        if (response == 200) {
                            JSONArray newsArray = localJSONObject1.getJSONArray("items");
                            for (int i = 0;i < newsArray.length();i++)
                            {
                                JSONObject itemObject = newsArray.getJSONObject(i);
                                ItemModel iModel = parseItemModel(itemObject);
                                Globals.lstSearchItems.add(iModel);
                            }
                            caller.onResponse(300);

                        } else if (response == 400) {
                            caller.onResponse(500);
                        }
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        });
    }
    public static void serviceDeleteOrder(final IServiceResult caller)
    {
        RequestParams param = new RequestParams();
        param.put("oid",Globals.currentOrder.mNo);
        HttpUtil.post(Config.mDeleteOrderUrl,param,new AsyncHttpResponseHandler() {
            public void onFailure(Throwable paramThrowable) {
                String s = "fail";
                caller.onResponse(400);

            }

            public void onFinish() {
                String s = "finish";
            }

            public void onSuccess(String paramString) {  //that is return when success..
                String s = "finish";

            }
        });
    }
    public static void serviceUpdateOrderComplete(final IServiceResult caller)
    {
        RequestParams param = new RequestParams();
        param.put("oid",Globals.currentOrder.mNo);
        HttpUtil.post(Config.mOrderCompleteUrl,param,new AsyncHttpResponseHandler() {
            public void onFailure(Throwable paramThrowable) {
                String s = "fail";
                caller.onResponse(400);

            }

            public void onFinish() {
                String s = "finish";
            }

            public void onSuccess(String paramString) {  //that is return when success..
                String s = "finish";

            }
        });
    }
    public static void serviceLoadOrdersDetail(final IServiceResult caller)
    {
        RequestParams param = new RequestParams();
        param.put("oid",Globals.currentOrder.mNo);
        HttpUtil.post(Config.mLoadOrderDetail,param,new AsyncHttpResponseHandler() {
            public void onFailure(Throwable paramThrowable) {
                String s = "fail";
                caller.onResponse(400);

            }

            public void onFinish() {
                String s = "finish";
            }

            public void onSuccess(String paramString) {  //that is return when success..
                try {
                    JSONObject localJSONObject1 = new JSONObject(paramString);
                    if (localJSONObject1.has("response")) {
                        int response = localJSONObject1.getInt("response");
                        if (response == 200) {
                            JSONArray itemsArray = localJSONObject1.getJSONArray("items");
                            Globals.currentOrder.mItemBuy = new ArrayList<ItemModel>();
                            for (int i = 0;i < itemsArray.length();i++)
                            {
                                JSONObject orderObject = itemsArray.getJSONObject(i);
                                ItemModel iModel = parseItemModel(orderObject);
                                Globals.currentOrder.mItemBuy.add(iModel);
                            }
                            caller.onResponse(200);

                        } else if (response == 400) {
                            caller.onResponse(400);
                        }
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        });
    }

    public static void serviceLoadOrders(final IServiceResult caller)
    {
        RequestParams param = new RequestParams();
        param.put("uid",Globals.mAccount.mNo);
        Globals.lstOrders.clear();
        Globals.lstHistory.clear();
        HttpUtil.post(Config.mLoadOrdersUrl,param,new AsyncHttpResponseHandler() {
            public void onFailure(Throwable paramThrowable) {
                String s = "fail";
                caller.onResponse(400);

            }

            public void onFinish() {
                String s = "finish";
            }

            public void onSuccess(String paramString) {  //that is return when success..
                try {
                    JSONObject localJSONObject1 = new JSONObject(paramString);
                    if (localJSONObject1.has("response")) {
                        int response = localJSONObject1.getInt("response");
                        if (response == 200) {
                            JSONArray ordersArray = localJSONObject1.getJSONArray("orders");
                            for (int i = 0;i < ordersArray.length();i++)
                            {
                                JSONObject orderObject = ordersArray.getJSONObject(i);
                                OrderModel oModel = parseOrderModel(orderObject);
                                Globals.lstOrders.add(oModel);
                            }

                            JSONArray historyArray = localJSONObject1.getJSONArray("history");
                            for (int i = 0;i < historyArray.length();i++)
                            {
                                JSONObject historyObject = historyArray.getJSONObject(i);
                                OrderModel oModel = parseOrderModel(historyObject);
                                Globals.lstHistory.add(oModel);
                            }


                            caller.onResponse(200);

                        } else if (response == 400) {
                            caller.onResponse(400);
                        }
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        });
    }



    public static OrderModel parseOrderModel(JSONObject itemObject)
    {
        OrderModel iModel = new OrderModel();
        try {
            iModel.mNo = itemObject.getString("no");
            iModel.mAddress = itemObject.getString("address");
            iModel.mCreateDate = itemObject.getString("createdate");
            iModel.mPayment = itemObject.getString("payment");
            iModel.mStatus = itemObject.getString("state");
            iModel.mPrice = itemObject.getString("price");
            iModel.mName = itemObject.getString("name");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return iModel;
    }
    public static ItemModel parseItemModel(JSONObject itemObject)
    {
        ItemModel iModel = new ItemModel();
        try {
            iModel.mId = itemObject.getString("no");
            iModel.mName = itemObject.getString("name");
            iModel.mDescription = itemObject.getString("description");
            iModel.mAmount = itemObject.getString("amount");
            iModel.mPrice = itemObject.getString("price");
            iModel.mCreateDate = itemObject.getString("createdate");
            iModel.mImages = new ArrayList<String>();
            iModel.mImages.add(itemObject.getString("image1"));
            iModel.mImages.add(itemObject.getString("image2"));
            iModel.mImages.add(itemObject.getString("image3"));
            iModel.mImages.add(itemObject.getString("image4"));
            iModel.mCategory = itemObject.getString("category");
            iModel.mPhone = itemObject.getString("phone");
            iModel.mEmail = itemObject.getString("email");
            iModel.mBrand = itemObject.getString("brand");
            JSONObject rateObject = itemObject.getJSONObject("review");
            iModel.mStars = rateObject.getString("star");
            iModel.mCountReview = rateObject.getString("count");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return iModel;
    }
}
