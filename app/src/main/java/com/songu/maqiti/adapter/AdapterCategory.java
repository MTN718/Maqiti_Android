package com.songu.maqiti.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.songu.maqiti.R;
import com.songu.maqiti.models.SubCategoryModel;

import java.util.List;

/**
 * Created by Administrator on 2/21/2017.
 */
public class AdapterCategory extends BaseAdapter {


    List<SubCategoryModel> m_lstCategorys;
    Context mContext;


    public AdapterCategory(Context con) {
        super();
        this.mContext = con;
    }

    public int getCount() {
        if (this.m_lstCategorys == null)
            return 0;
        return this.m_lstCategorys.size();

    }

    public SubCategoryModel getItem(int paramInt) {
        return this.m_lstCategorys.get(paramInt);
    }

    public long getItemId(int paramInt) {
        return 0L;
    }

    public View getView(final int paramInt, View paramView, ViewGroup paramViewGroup) {

        View localView = paramView;
        ViewHolder localViewHolder = null;

        if (localView == null) {
            localView = LayoutInflater.from(paramViewGroup.getContext()).inflate(R.layout.item_category, null);

        } else {
            localViewHolder = (ViewHolder) localView.getTag();
        }
        if (localViewHolder == null) {
            localViewHolder = new ViewHolder();


            localView.setTag(localViewHolder);
        }

        final ViewHolder fHolder = localViewHolder;

        /*localViewHolder.mName.setText(m_lstCategorys.get(paramInt).mName);

        Picasso.with(mContext)
                .load(Config.mImageBaseUrl + m_lstCategorys.get(paramInt).mImage)
                .into(localViewHolder.mPhoto);*/

        localView.setTag(localViewHolder);
        return localView;

    }

    public void update(List<SubCategoryModel> tickets) {
        this.m_lstCategorys = tickets;

        notifyDataSetChanged();
    }

    public class ViewHolder {
        public ImageView mPhoto;
        public TextView mName;
    }
}

