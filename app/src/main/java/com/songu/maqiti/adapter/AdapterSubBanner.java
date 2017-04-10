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
import com.squareup.picasso.MemoryPolicy;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 3/6/2017.
 */
public class AdapterSubBanner extends PagerAdapter {
    private ArrayList<ImageView> imgList;
    public List<String> lstBanners;
    public Context mCon;

    public AdapterSubBanner(Context mContext,List<String> banners) {
        this.mCon = mContext;
        imgList = new ArrayList<ImageView>();
        lstBanners = banners;
    }
    @Override
    public int getCount() {
        return 3;
    }

    @Override
    public Object instantiateItem(ViewGroup container, final int position) {

        if (imgList.size() > (position % 3)) {
            if (lstBanners.size() >(position % 3)) {
                Picasso.with(mCon)
                        .load(Config.mImageBaseUrl + lstBanners.get(position % 3))
                        .placeholder(R.color.color_gray)
                        .memoryPolicy(MemoryPolicy.NO_CACHE)
                        .into(imgList.get(position % 3));
            }
            container.addView(imgList.get(position % 3));
        }
        else
        {
            ImageView m_iv1 = new ImageView(mCon);
            m_iv1 = new ImageView(mCon);
            m_iv1.setScaleType(ImageView.ScaleType.FIT_XY);
            m_iv1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                }
            });
            imgList.add(m_iv1);
            container.addView(m_iv1);
            if (lstBanners != null && lstBanners.size() >(position % 3)) {
                Picasso.with(mCon)
                        .load(Config.mImageBaseUrl + lstBanners.get(position % 3))
                        .placeholder(R.color.color_gray)
                        .memoryPolicy(MemoryPolicy.NO_CACHE)
                        .into(m_iv1);
            }
        }

        return imgList.get(position % 4);
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
