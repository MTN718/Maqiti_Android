package com.songu.maqiti.adapter;

import android.content.Context;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.songu.maqiti.doc.Config;
import com.songu.maqiti.models.ItemModel;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2/22/2017.
 */
public class AdapterOrderPager extends PagerAdapter {

    public List<View> lstViews;
    public Context mCon;

    public AdapterOrderPager(Context mContext, List<View> lstVws) {
        this.mCon = mContext;
        lstViews = lstVws;
    }

    @Override
    public int getCount() {
        return 2;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {

        container.addView(lstViews.get(position % 2));
        return lstViews.get(position % 2);
    }

    @Override
    public boolean isViewFromObject(View view, Object obj) {
        return view == obj;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView(lstViews.get(position % 2));
    }
}
