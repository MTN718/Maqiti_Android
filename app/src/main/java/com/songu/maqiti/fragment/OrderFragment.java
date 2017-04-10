package com.songu.maqiti.fragment;

import android.app.Service;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.InflateException;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.songu.maqiti.R;
import com.songu.maqiti.activity.DetailActivity;
import com.songu.maqiti.activity.OrderDetailActivity;
import com.songu.maqiti.adapter.AdapterOrder;
import com.songu.maqiti.adapter.AdapterOrderPager;
import com.songu.maqiti.doc.Config;
import com.songu.maqiti.doc.Globals;
import com.songu.maqiti.models.GridItemViewHolder;
import com.songu.maqiti.models.OrderViewHolder;
import com.songu.maqiti.service.IServiceResult;
import com.songu.maqiti.service.ServiceManager;
import com.songu.maqiti.utils.Utils;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2/22/2017.
 */
public class OrderFragment extends Fragment implements View.OnClickListener,IServiceResult{

    public View mView;
    public ViewPager vpOrders;
    public AdapterOrderPager adapterOrder;
    public List<View> lstOrderViews;
    public List<OrderViewHolder> lstHolder;
    public AdapterOrder adapterOrders;
    public AdapterOrder adapterHistory;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        try {
            mView = inflater.inflate(R.layout.fragment_order, container, false);
        } catch (InflateException e) {

        }
        initView();
        setData();
        return mView;
    }
    public void initView()
    {
        vpOrders = (ViewPager) mView.findViewById(R.id.vpOrders);
        adapterOrders = new AdapterOrder(this.getActivity());
        adapterHistory = new AdapterOrder(this.getActivity());
        addViews();
        adapterOrder = new AdapterOrderPager(this.getActivity(),lstOrderViews);
        vpOrders.setAdapter(adapterOrder);
    }
    public void setData()
    {
        ServiceManager.serviceLoadOrders(this);
    }
    public void addViews()
    {
        lstOrderViews = new ArrayList<>();
        lstHolder = new ArrayList<>();

        for (int i = 0;  i < 2;i++) {
            View localView;
            OrderViewHolder localViewHolder = null;
            localView = LayoutInflater.from(this.getContext()).inflate(R.layout.layout_order, null);

            localViewHolder = new OrderViewHolder();
            localViewHolder.lstOrders = (ListView) localView.findViewById(R.id.lstOrders);
            localViewHolder.txtDescription = (TextView) localView.findViewById(R.id.txtOrderDescription);
            if (i == 0) {
                localViewHolder.lstOrders.setAdapter(adapterOrders);
                localViewHolder.txtDescription.setText(Utils.getResourceString(this.getActivity(),R.string.order_active));
            }
            else
            {
                localViewHolder.lstOrders.setAdapter(adapterHistory);
                localViewHolder.txtDescription.setText(Utils.getResourceString(this.getActivity(),R.string.order_history));
            }

            localView.setTag(localViewHolder);
            lstHolder.add(localViewHolder);
            lstOrderViews.add(localView);
        }

        lstHolder.get(0).lstOrders.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Globals.currentOrder = Globals.lstOrders.get(position);
                Intent m = new Intent(OrderFragment.this.getContext(), OrderDetailActivity.class);
                OrderFragment.this.startActivityForResult(m,100);
            }
        });
        lstHolder.get(1).lstOrders.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Globals.currentOrder = Globals.lstHistory.get(position);
                Intent m = new Intent(OrderFragment.this.getContext(), OrderDetailActivity.class);
                OrderFragment.this.startActivityForResult(m,100);
            }
        });
    }

    @Override
    public void onClick(View v) {

    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == 100) {
            setData();
        }
    }
    @Override
    public void onResponse(int code) {
        switch (code)
        {
            case 200:
                adapterOrders.update(Globals.lstOrders);
                adapterHistory.update(Globals.lstHistory);
                break;
            case 400:
                break;
        }
    }
}
