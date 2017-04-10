package com.songu.maqiti.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.songu.maqiti.R;
import com.songu.maqiti.doc.Config;
import com.songu.maqiti.models.CountryModel;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by Administrator on 2/24/2017.
 */
public class AdapterCountry extends BaseAdapter {

    List<CountryModel> m_lstCountry;
    Context mContext;


    public AdapterCountry(Context con) {
        super();
        this.mContext = con;
    }

    public int getCount() {
        if (this.m_lstCountry == null)
            return 0;
        return this.m_lstCountry.size();

    }

    public CountryModel getItem(int paramInt) {
        return this.m_lstCountry.get(paramInt);
    }

    public long getItemId(int paramInt) {
        return 0L;
    }

    public View getView(final int paramInt, View paramView, ViewGroup paramViewGroup) {

        View localView = paramView;
        ViewHolder localViewHolder = null;

        if (localView == null) {
            localView = LayoutInflater.from(paramViewGroup.getContext()).inflate(R.layout.item_country, null);

        } else {
            localViewHolder = (ViewHolder) localView.getTag();
        }
        if (localViewHolder == null) {
            localViewHolder = new ViewHolder();

            localViewHolder.mPhoto = (ImageView) localView.findViewById(R.id.imgItemCountryFlag);
            localViewHolder.mName = (TextView) localView.findViewById(R.id.txtItemCountryName);
            localView.setTag(localViewHolder);
        }

        final ViewHolder fHolder = localViewHolder;

        localViewHolder.mName.setText(m_lstCountry.get(paramInt).mName);

        Picasso.with(mContext)
                .load(Config.mImageBaseUrl + m_lstCountry.get(paramInt).mImage)
                .into(localViewHolder.mPhoto);

        localView.setTag(localViewHolder);
        return localView;

    }

    public void update(List<CountryModel> tickets) {
        this.m_lstCountry = tickets;

        notifyDataSetChanged();
    }

    public class ViewHolder {
        public ImageView mPhoto;
        public TextView mName;
    }

}
