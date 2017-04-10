package com.songu.maqiti.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.InflateException;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.songu.maqiti.R;
import com.songu.maqiti.activity.DetailActivity;
import com.songu.maqiti.adapter.AdapterItem;
import com.songu.maqiti.doc.Globals;
import com.songu.maqiti.service.IServiceResult;
import com.songu.maqiti.service.ServiceManager;

/**
 * Created by Administrator on 2/21/2017.
 */
public class FavouriteFragment extends Fragment implements IServiceResult{


    public View mView;
    public ListView lstFavourites;
    public AdapterItem adapterItem;
    public LinearLayout layoutFavourite;
    public TextView txtNoItems;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        try {
            mView = inflater.inflate(R.layout.fragment_favourite, container, false);
        } catch (InflateException e) {

        }
        initView();
        return mView;
    }
    public void initView()
    {
        lstFavourites = (ListView) mView.findViewById(R.id.lstFavourites);
        layoutFavourite = (LinearLayout) mView.findViewById(R.id.layoutFavouriteList);
        adapterItem = new AdapterItem(this.getActivity());
        txtNoItems = (TextView) mView.findViewById(R.id.txtFavouriteNoItems);
        txtNoItems.setVisibility(View.GONE);
        lstFavourites.setAdapter(adapterItem);

        lstFavourites.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Globals.currentItem = Globals.lstFavouriteItems.get(position);
                Intent m = new Intent(FavouriteFragment.this.getContext(), DetailActivity.class);
                FavouriteFragment.this.startActivityForResult(m,10);
            }
        });

        ServiceManager.serviceLoadFavouriteItems(this);
    }

    @Override
    public void onResponse(int code) {
        switch (code)
        {
            case 200:
                if (Globals.lstFavouriteItems.size() > 0)
                {
                    txtNoItems.setVisibility(View.GONE);
                }
                else {
                    txtNoItems.setVisibility(View.VISIBLE);
                    layoutFavourite.setVisibility(View.GONE);
                }
                adapterItem.update(Globals.lstFavouriteItems);
                break;
        }
    }
}