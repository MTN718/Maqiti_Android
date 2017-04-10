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
import com.songu.maqiti.models.ItemModel;
import com.songu.maqiti.models.OrderModel;
import com.songu.maqiti.utils.Utils;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by Administrator on 2/22/2017.
 */
public class AdapterOrder extends BaseAdapter {


    List<OrderModel> m_lstItems;
    Context mContext;


    public AdapterOrder(Context con) {
        super();
        this.mContext = con;
    }

    public int getCount() {
        if (this.m_lstItems == null)
            return 0;
        return this.m_lstItems.size();

    }

    public OrderModel getItem(int paramInt) {
        return this.m_lstItems.get(paramInt);
    }

    public long getItemId(int paramInt) {
        return 0L;
    }

    public View getView(final int paramInt, View paramView, ViewGroup paramViewGroup) {

        View localView = paramView;
        ViewHolder localViewHolder = null;

        if (localView == null) {
            localView = LayoutInflater.from(paramViewGroup.getContext()).inflate(R.layout.item_order, null);

        } else {
            localViewHolder = (ViewHolder) localView.getTag();
        }
        if (localViewHolder == null) {
            localViewHolder = new ViewHolder();

            localViewHolder.mName = (TextView) localView.findViewById(R.id.txtOrderItemName);
            localViewHolder.mStatus = (TextView) localView.findViewById(R.id.txtOrderItemProgress);

            localView.setTag(localViewHolder);
        }

        final ViewHolder fHolder = localViewHolder;
        localViewHolder.mName.setText(m_lstItems.get(paramInt).mName + "     " +m_lstItems.get(paramInt).mPrice + Utils.getResourceString(mContext,R.string.currency));
        switch (Integer.parseInt(m_lstItems.get(paramInt).mStatus))
        {
            case 0:
                localViewHolder.mStatus.setText(Utils.getResourceString(mContext,R.string.order_status1));
                break;
            case 1:
                localViewHolder.mStatus.setText(Utils.getResourceString(mContext,R.string.order_status2));
                break;
            case 2:
                localViewHolder.mStatus.setText(Utils.getResourceString(mContext,R.string.order_status3));
                break;
            case 3:
                localViewHolder.mStatus.setText(Utils.getResourceString(mContext,R.string.order_status4));
                break;
            case 4:
                localViewHolder.mStatus.setText(Utils.getResourceString(mContext,R.string.order_status5));
                break;
        }
        localView.setTag(localViewHolder);
        return localView;

    }

    public void update(List<OrderModel> tickets) {
        this.m_lstItems = tickets;

        notifyDataSetChanged();
    }

    public class ViewHolder {
        public TextView mName;
        public TextView mStatus;
    }
}

