package com.songu.maqiti.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.songu.maqiti.R;
import com.songu.maqiti.doc.Config;
import com.songu.maqiti.models.ItemModel;
import com.songu.maqiti.utils.Utils;
import com.squareup.picasso.Picasso;

import org.w3c.dom.Text;

import java.util.List;

/**
 * Created by Administrator on 2/21/2017.
 */
public class AdapterItem extends BaseAdapter {


    List<ItemModel> m_lstItems;
    Context mContext;


    public AdapterItem(Context con) {
        super();
        this.mContext = con;
    }

    public int getCount() {
        if (this.m_lstItems == null)
            return 0;
        return this.m_lstItems.size();

    }

    public ItemModel getItem(int paramInt) {
        return this.m_lstItems.get(paramInt);
    }

    public long getItemId(int paramInt) {
        return 0L;
    }

    public View getView(final int paramInt, View paramView, ViewGroup paramViewGroup) {

        View localView = paramView;
        ViewHolder localViewHolder = null;

        if (localView == null) {
            localView = LayoutInflater.from(paramViewGroup.getContext()).inflate(R.layout.item_list, null);

        } else {
            localViewHolder = (ViewHolder) localView.getTag();
        }
        if (localViewHolder == null) {
            localViewHolder = new ViewHolder();

            localViewHolder.mPhoto = (ImageView) localView.findViewById(R.id.imgListItem);
            localViewHolder.mName = (TextView) localView.findViewById(R.id.txtListItemName);
            localViewHolder.mEmail = (TextView) localView.findViewById(R.id.txtListItemEmail);
            localViewHolder.mPhone = (TextView) localView.findViewById(R.id.txtListItemPhone);
            localViewHolder.mPrice = (TextView) localView.findViewById(R.id.txtListItemPrice);
            localViewHolder.mBrand = (TextView) localView.findViewById(R.id.txtListItemBrand);
            localViewHolder.mRating = (TextView) localView.findViewById(R.id.txtRateItem);
            localViewHolder.mStar = (RatingBar) localView.findViewById(R.id.ratingItem);

            localView.setTag(localViewHolder);
        }

        final ViewHolder fHolder = localViewHolder;

        if (m_lstItems.get(paramInt).mImages.size() > 0) {
            Picasso.with(mContext)
                    .load(Config.mImageBaseUrl + m_lstItems.get(paramInt).mImages.get(0))
                    .into(localViewHolder.mPhoto);
        }
        localViewHolder.mName.setText(m_lstItems.get(paramInt).mName);
        localViewHolder.mPrice.setText(Utils.getPriceValue(m_lstItems.get(paramInt).mPrice) + Utils.getResourceString(mContext,R.string.currency));
        localViewHolder.mEmail.setText(m_lstItems.get(paramInt).mEmail);
        localViewHolder.mPhone.setText(m_lstItems.get(paramInt).mPhone);
        localViewHolder.mBrand.setText(m_lstItems.get(paramInt).mBrand);


        localViewHolder.mStar.setRating(Float.parseFloat(m_lstItems.get(paramInt).mStars));
        localViewHolder.mRating.setText("(" + m_lstItems.get(paramInt).mCountReview + ")");
        localView.setTag(localViewHolder);
        return localView;

    }

    public void update(List<ItemModel> tickets) {
        this.m_lstItems = tickets;

        notifyDataSetChanged();
    }

    public class ViewHolder {
        public ImageView mPhoto;
        public TextView mName;
        public TextView mPrice;
        public TextView mEmail;
        public TextView mPhone;
        public TextView mBrand;
        public RatingBar mStar;
        public TextView mRating;
    }
}

