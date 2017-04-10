package com.songu.maqiti.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.InflateException;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;

import com.afollestad.materialdialogs.MaterialDialog;
import com.songu.maqiti.R;
import com.songu.maqiti.activity.DetailActivity;
import com.songu.maqiti.activity.HomeActivity;
import com.songu.maqiti.adapter.AdapterBanner;
import com.songu.maqiti.adapter.AdapterSubBanner;
import com.songu.maqiti.doc.Config;
import com.songu.maqiti.doc.Enums;
import com.songu.maqiti.doc.Globals;
import com.songu.maqiti.models.SubCategoryModel;
import com.songu.maqiti.models.CategoryViewHolder;
import com.songu.maqiti.models.GridItemViewHolder;
import com.songu.maqiti.models.ItemModel;
import com.songu.maqiti.service.IServiceResult;
import com.songu.maqiti.service.ServiceManager;
import com.songu.maqiti.utils.Utils;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2/21/2017.
 */
public class HomeFragment extends Fragment implements IServiceResult {


    public View mView;
    public LinearLayout layoutNews;
    public HorizontalScrollView horNews;

    public LinearLayout layoutPopular;
    public HorizontalScrollView horPopular;

    public LinearLayout layoutRecommend;
    public HorizontalScrollView horRecommend;

    public HorizontalScrollView horFeatured;
    public LinearLayout layoutFeatured;

    public ViewPager vwPager;
    public ViewPager vwSmallPager;
    public ViewPager vwSmallPager1;
    public ViewPager vwSmallPager2;
    public AdapterBanner adapterBanner;
    public AdapterSubBanner adapterSmallBanner;
    public AdapterSubBanner adapterSmallBanner1;
    public AdapterSubBanner adapterSmallBanner2;
    public LinearLayout layoutGrdCategory;
    public MaterialDialog progressDialog;

    public List<TextView> lstDots = new ArrayList<>();


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        try {
            mView = inflater.inflate(R.layout.fragment_home, container, false);
        } catch (InflateException e) {

        }
        initView();
        progressDialog.show();
        ServiceManager.serviceLoadPopular(this);
        return mView;
    }
    public void initView()
    {
        horNews = (HorizontalScrollView) mView.findViewById(R.id.horNewItems);
        horPopular = (HorizontalScrollView) mView.findViewById(R.id.horPopularItems);
        horRecommend = (HorizontalScrollView) mView.findViewById(R.id.horRecommendItems);
        horFeatured = (HorizontalScrollView) mView.findViewById(R.id.horFeaturedItems);


        vwPager = (ViewPager)mView.findViewById(R.id.vpHome);
        vwSmallPager = (ViewPager) mView.findViewById(R.id.vpSubBanner);
        vwSmallPager1 = (ViewPager) mView.findViewById(R.id.vpSubBanner1);
        vwSmallPager2 = (ViewPager) mView.findViewById(R.id.vpSubBanner2);

        layoutNews = (LinearLayout) mView.findViewById(R.id.layoutHomeNew);
        layoutPopular = (LinearLayout) mView.findViewById(R.id.layoutPopular);
        layoutRecommend = (LinearLayout) mView.findViewById(R.id.layoutRecommend);
        layoutGrdCategory = (LinearLayout) mView.findViewById(R.id.layoutGrdCategory);
        layoutFeatured = (LinearLayout) mView.findViewById(R.id.layoutFeatured);
        adapterBanner = new AdapterBanner(this.getActivity());
        adapterSmallBanner = new AdapterSubBanner(this.getActivity(),Globals.lstSmallBanners);
        adapterSmallBanner1 = new AdapterSubBanner(this.getActivity(),Globals.lstSmallBanners1);
        adapterSmallBanner2 = new AdapterSubBanner(this.getActivity(),Globals.lstSmallBanners2);
        vwPager.setAdapter(adapterBanner);
        vwSmallPager.setAdapter(adapterSmallBanner);
        vwSmallPager1.setAdapter(adapterSmallBanner1);
        vwSmallPager2.setAdapter(adapterSmallBanner2);

        progressDialog = new MaterialDialog.Builder(this.getActivity())
                .title(Utils.getResourceString(this.getActivity(),R.string.message_wait))
                .content(Utils.getResourceString(this.getActivity(),R.string.message_loading))
                .progress(true, 0).build();
        progressDialog.setCancelable(false);


        horNews.setVerticalScrollBarEnabled(false);
        horNews.setHorizontalScrollBarEnabled(false);

        horFeatured.setVerticalScrollBarEnabled(false);
        horFeatured.setHorizontalScrollBarEnabled(false);

        horPopular.setVerticalScrollBarEnabled(false);
        horPopular.setHorizontalScrollBarEnabled(false);
        lstDots.clear();
        lstDots.add((TextView)mView.findViewById(R.id.txtHomeDot1));
        lstDots.add((TextView)mView.findViewById(R.id.txtHomeDot2));
        lstDots.add((TextView)mView.findViewById(R.id.txtHomeDot3));
        lstDots.add((TextView)mView.findViewById(R.id.txtHomeDot4));

        horRecommend.setVerticalScrollBarEnabled(false);
        horRecommend.setHorizontalScrollBarEnabled(false);

        vwPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                for (int i = 0;i <lstDots.size();i++)
                {
                    lstDots.get(i).setBackgroundResource(R.drawable.drawable_circle_gray);
                }
                lstDots.get(position).setBackgroundResource(R.drawable.drawable_circle_white);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        addRecommendViews();

    }
    private void addViews() {
        layoutNews.removeAllViews();
        for (int i = 0; i < Globals.lstNewArrival.size(); i++) {
            layoutNews.addView(addNewArrivalView(Globals.lstNewArrival.get(i)));
        }
    }
    private void addPopularViews() {
        layoutPopular.removeAllViews();
        for (int i = 0; i < Globals.lstTopSeller.size(); i++) {
            layoutPopular.addView(addNewArrivalView(Globals.lstTopSeller.get(i)));
        }
    }

    private void addFeaturedViews()
    {
        layoutFeatured.removeAllViews();
        for (int i = Globals.featuredItems.size() - 1; i >=0; i--) {
            layoutFeatured.addView(addNewArrivalView(Globals.featuredItems.get(i)));
        }
    }
    private void addRecommendViews()
    {
        layoutRecommend.removeAllViews();
        for (int i = Globals.lstRecommend.size() - 1; i >=0; i--) {
            layoutRecommend.addView(addNewArrivalView(Globals.lstRecommend.get(i)));
        }
    }
    private void addCategoryViews()
    {
        layoutGrdCategory.removeAllViews();
        for (int i = 0; i < Globals.featuredCategory.size() - 1; i+=2) {
            addCategoryView(Globals.featuredCategory.get(i),Globals.featuredCategory.get(i + 1),true);
        }
        if (Globals.featuredCategory.size() % 2  == 1)
        {
            addCategoryView(Globals.featuredCategory.get(Globals.featuredCategory.size() - 1),null,false);
        }
    }
    public void addCategoryView(final SubCategoryModel mLeft, final SubCategoryModel mRight, boolean showRight)
    {
        View localView;
        CategoryViewHolder localViewHolder = null;
        localView = LayoutInflater.from(this.getContext()).inflate(R.layout.item_category, null);

        localViewHolder = new CategoryViewHolder();
        localViewHolder.layoutRight = (LinearLayout) localView.findViewById(R.id.layoutCategoryItemRight);
        localViewHolder.layoutLeft = (LinearLayout) localView.findViewById(R.id.layoutCategoryItemLeft);

        localViewHolder.txtName1 = (TextView) localView.findViewById(R.id.txtCategoryName1);
        localViewHolder.txtName2 = (TextView) localView.findViewById(R.id.txtCategoryName2);

        localViewHolder.txtDepart1 = (TextView) localView.findViewById(R.id.txtCategoryDepart1);
        localViewHolder.txtDepart2 = (TextView) localView.findViewById(R.id.txtCategoryDepart2);

        localViewHolder.imgCategory1 = (ImageView) localView.findViewById(R.id.imgCategoryPhoto1);
        localViewHolder.imgCategory2 = (ImageView) localView.findViewById(R.id.imgCategoryPhoto2);

        localView.setTag(localViewHolder);

        localViewHolder.txtName1.setText(mLeft.mName);
        localViewHolder.txtDepart1.setText(mLeft.mDepart);
        Picasso.with(this.getContext())
                .load(Config.mImageBaseUrl + mLeft.mImage)
                .placeholder(HomeFragment.this.getResources().getDrawable(R.drawable.cartplaceholder))
                .into(localViewHolder.imgCategory1);

        if (showRight)
        {
            localViewHolder.layoutRight.setVisibility(View.VISIBLE);
            localViewHolder.txtName2.setText(mRight.mName);
            localViewHolder.txtDepart2.setText(mRight.mDepart);
            Picasso.with(this.getContext())
                    .load(Config.mImageBaseUrl + mRight.mImage)
                    .placeholder(HomeFragment.this.getResources().getDrawable(R.drawable.cartplaceholder))
                    .into(localViewHolder.imgCategory2);
        }else localViewHolder.layoutRight.setVisibility(View.INVISIBLE);

        localView.setTag(localViewHolder);
        localViewHolder.layoutRight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Globals.currentCategory = mRight;
                Globals.e_mode = Enums.MODE.BROWSE;
                Globals.lstItems.clear();
                Globals.lstSearchItems.clear();
                ((HomeActivity)(HomeFragment.this.getActivity())).setFragment();

            }
        });
        localViewHolder.layoutLeft.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Globals.currentCategory = mLeft;
                Globals.e_mode = Enums.MODE.BROWSE;
                Globals.lstItems.clear();
                Globals.lstSearchItems.clear();
                ((HomeActivity)(HomeFragment.this.getActivity())).setFragment();
            }
        });
        layoutGrdCategory.addView(localView);
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        addRecommendViews();
    }
    public View addNewArrivalView(final ItemModel mModel)
    {
        View localView;
        GridItemViewHolder localViewHolder = null;
        localView = LayoutInflater.from(this.getContext()).inflate(R.layout.item_grid, null);

        localViewHolder = new GridItemViewHolder();
        localViewHolder.imgItem = (ImageView) localView.findViewById(R.id.imgGridItem);
        localViewHolder.txtItemName = (TextView) localView.findViewById(R.id.txtGridItemName);
        localViewHolder.txtItemPrice = (TextView) localView.findViewById(R.id.txtGridItemPrice);
        localViewHolder.txtItemBrand = (TextView) localView.findViewById(R.id.txtGridItemBrand);
        localViewHolder.rateStar = (RatingBar)localView.findViewById(R.id.ratingGridItem);
        localViewHolder.txtReviews = (TextView) localView.findViewById(R.id.txtRateGridItem);
        localView.setTag(localViewHolder);

        localViewHolder.txtItemName.setText(mModel.mName);
        localViewHolder.txtItemPrice.setText(Utils.getPriceValue(mModel.mPrice) + Utils.getResourceString(HomeFragment.this.getActivity(),R.string.currency));
        localViewHolder.txtItemBrand.setText(mModel.mBrand);

        localViewHolder.rateStar.setRating(Float.parseFloat(mModel.mStars));
        localViewHolder.txtReviews.setText("(" + mModel.mCountReview + ")");


        if (mModel.mImages.size() > 0) {
            Picasso.with(this.getContext())
                    .load(Config.mImageBaseUrl + mModel.mImages.get(0))
                    .placeholder(HomeFragment.this.getResources().getDrawable(R.drawable.cartplaceholder))
                    .into(localViewHolder.imgItem);
        }
        localView.setTag(localViewHolder);
        localView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Globals.currentItem = mModel;
                Intent m = new Intent(HomeFragment.this.getContext(), DetailActivity.class);
                HomeFragment.this.startActivityForResult(m,10);
            }
        });
        return localView;
    }
    @Override
    public void onResponse(int code) {
        this.getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                progressDialog.dismiss();
            }
        });

        switch (code)
        {
            case 200:
                addViews();
                addPopularViews();
                addCategoryViews();
                addRecommendViews();
                addFeaturedViews();
                vwPager.setAdapter(adapterBanner);
                vwSmallPager.setAdapter(adapterSmallBanner);
                vwSmallPager1.setAdapter(adapterSmallBanner1);
                vwSmallPager2.setAdapter(adapterSmallBanner2);
                ((HomeActivity)getActivity()).addMenuViews();
                break;
        }
    }
}
