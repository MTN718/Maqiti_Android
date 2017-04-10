package com.songu.maqiti.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.songu.maqiti.R;
import com.songu.maqiti.activity.ImageSlideActivity;
import com.songu.maqiti.doc.Config;
import com.songu.maqiti.models.ItemModel;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by Administrator on 3/1/2017.
 */
public class AdapterImageSlide extends PagerAdapter {
    private ArrayList<ImageView> imgList;
    public Context mCon;
    public ItemModel mItem;

    public AdapterImageSlide(Context mContext, ItemModel itemModel) {
        this.mCon = mContext;
        imgList = new ArrayList<ImageView>();
        mItem = itemModel;
    }

    @Override
    public int getCount() {
        return 4;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {

        if (imgList.size() > (position % 4)) {
            if (mItem.mImages.size() >(position % 4)) {
                Picasso.with(mCon)
                        .load(Config.mImageBaseUrl + mItem.mImages.get(position % 4))
                        .placeholder(R.color.color_gray)
                        .into(imgList.get(position % 4));
            }
            container.addView(imgList.get(position % 4));
        }
        else
        {
            ImageView m_iv1 = new ImageView(mCon);
            m_iv1 = new ImageView(mCon);
            m_iv1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ((Activity)mCon).finish();
                }
            });
            imgList.add(m_iv1);
            container.addView(m_iv1);
            if (mItem.mImages != null && mItem.mImages.size() >(position % 4)) {
                Picasso.with(mCon)
                        .load(Config.mImageBaseUrl + mItem.mImages.get(position % 4))
                        .placeholder(R.color.color_gray)
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
