package com.songu.maqiti.adapter;

import android.content.Context;
import android.media.Image;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.songu.maqiti.R;
import com.songu.maqiti.activity.HomeActivity;
import com.songu.maqiti.doc.Config;
import com.songu.maqiti.doc.Globals;
import com.songu.maqiti.fragment.CartsFragment;
import com.songu.maqiti.models.ItemModel;
import com.songu.maqiti.utils.Utils;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by Administrator on 2/25/2017.
 */
public class AdapterCart extends BaseAdapter {


    List<ItemModel> m_lstItems;
    Context mContext;
    CartsFragment cartsFragment;


    public AdapterCart(Context con,CartsFragment frag) {
        super();
        this.mContext = con;
        this.cartsFragment = frag;
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
            localView = LayoutInflater.from(paramViewGroup.getContext()).inflate(R.layout.item_cart, null);

        } else {
            localViewHolder = (ViewHolder) localView.getTag();
        }
        if (localViewHolder == null) {
            localViewHolder = new ViewHolder();

            localViewHolder.mPhoto = (ImageView) localView.findViewById(R.id.imgCartItem);
            localViewHolder.mName = (TextView) localView.findViewById(R.id.txtCartItemName);
            localViewHolder.mPrice = (TextView) localView.findViewById(R.id.txtCartItemPrice);
            localViewHolder.mBrand = (TextView) localView.findViewById(R.id.txtCartItemBrand);
            localViewHolder.imgDelete = (ImageView) localView.findViewById(R.id.imgCartItemClose);
            localViewHolder.rateItem = (RatingBar) localView.findViewById(R.id.ratingCartItem);
            localViewHolder.txtReview = (TextView) localView.findViewById(R.id.txtRateCartItem);

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
        localViewHolder.mBrand.setText(m_lstItems.get(paramInt).mBrand);
        localViewHolder.imgDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Globals.lstCarts.remove(paramInt);
                update(Globals.lstCarts);
                cartsFragment.updatePrice();
                ((HomeActivity)cartsFragment.getActivity()).updateCartCount();
            }
        });
        localViewHolder.rateItem.setRating(Float.parseFloat(m_lstItems.get(paramInt).mStars));
        localViewHolder.txtReview.setText("(" + m_lstItems.get(paramInt).mCountReview + ")");
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
        public TextView mBrand;
        public ImageView imgDelete;
        public RatingBar rateItem;
        public TextView txtReview;
    }
}