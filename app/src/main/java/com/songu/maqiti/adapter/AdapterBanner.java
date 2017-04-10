package com.songu.maqiti.adapter;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.songu.maqiti.R;
import com.songu.maqiti.activity.HomeActivity;
import com.songu.maqiti.doc.Config;
import com.songu.maqiti.doc.Enums;
import com.songu.maqiti.doc.Globals;
import com.songu.maqiti.models.SubCategoryModel;
import com.squareup.picasso.MemoryPolicy;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by Administrator on 2/23/2017.
 */
public class AdapterBanner extends PagerAdapter {
    private ArrayList<ImageView> imgList;
    public Context mCon;

    public AdapterBanner(Context mContext) {
        this.mCon = mContext;
        imgList = new ArrayList<ImageView>();
    }

    @Override
    public int getCount() {
        return 4;
    }

    @Override
    public Object instantiateItem(ViewGroup container, final int position) {

        if (imgList.size() > (position % 4)) {
            if (Globals.lstBanners.size() >(position % 4)) {
                Picasso.with(mCon)
                        .load(Config.mImageBaseUrl + Globals.lstBanners.get(position % 4))
                        .placeholder(R.color.color_gray)
                        .memoryPolicy(MemoryPolicy.NO_CACHE)
                        .into(imgList.get(position % 4));
            }
            container.addView(imgList.get(position % 4));
        }
        else
        {
            ImageView m_iv1 = new ImageView(mCon);
            m_iv1 = new ImageView(mCon);
            m_iv1.setScaleType(ImageView.ScaleType.FIT_XY);
            m_iv1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Globals.e_mode = Enums.MODE.BROWSE;
                    Globals.lstItems.clear();
                    Globals.lstSearchItems.clear();
                    Globals.currentCategory = getCategoryFromId(Globals.lstBannerLinks.get(position % 4));
                    if (Globals.currentCategory != null)
                        ((HomeActivity)mCon).setFragment();
                }
            });
            imgList.add(m_iv1);
            container.addView(m_iv1);
            if (Globals.lstBanners != null && Globals.lstBanners.size() >(position % 4)) {
                Picasso.with(mCon)
                        .load(Config.mImageBaseUrl + Globals.lstBanners.get(position % 4))
                        .placeholder(R.color.color_gray)
                        .memoryPolicy(MemoryPolicy.NO_CACHE)
                        .into(m_iv1);
            }
        }

        return imgList.get(position % 4);
    }
    public SubCategoryModel getCategoryFromId(String id)
    {
        for (int i = 0;i < Globals.lstCategory.size();i++)
        {
            if (Globals.lstCategory.get(i).mNo.equals(id))
                return Globals.lstCategory.get(i);
        }
        return null;
    }
    @Override
    public boolean isViewFromObject(View view, Object obj) {
        return view == obj;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView(imgList.get(position % 4));
    }
}
