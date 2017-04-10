package com.songu.maqiti.fragment;

import android.app.Service;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.InflateException;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.songu.maqiti.R;
import com.songu.maqiti.activity.DetailActivity;
import com.songu.maqiti.adapter.AdapterItem;
import com.songu.maqiti.doc.Globals;
import com.songu.maqiti.service.IServiceResult;
import com.songu.maqiti.service.ServiceManager;
import com.songu.maqiti.view.rangeseekbar.RangeSeekBar;
import com.songu.maqiti.view.xlist.XListView;

/**
 * Created by Administrator on 2/21/2017.
 */
public class BrowseFragment extends Fragment implements IServiceResult,XListView.IXListViewListener,View.OnClickListener {


    public View mView;
    private XListView lstItems;
    public AdapterItem adapterItem;
    public MaterialDialog dialogSearch;
    public EditText editKeyword;
    public RangeSeekBar rangePrice;
    public EditText editSearchName;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        try {
            mView = inflater.inflate(R.layout.fragment_browse, container, false);
        } catch (InflateException e) {

        }
        initView();
        ServiceManager.serviceLoadItemsUnderCategory(this);
        return mView;
    }
    public void initView()
    {
        lstItems = (XListView) mView.findViewById(R.id.lstItemsBrowse);
        editSearchName = (EditText) mView.findViewById(R.id.editSearchKeyword);

        adapterItem = new AdapterItem(this.getActivity());
        lstItems.setAdapter(adapterItem);

        lstItems.setPullLoadEnable(true);
        //		mListView.setPullLoadEnable(false);
        //		mListView.setPullRefreshEnable(false);
        lstItems.setXListViewListener(this);

        boolean wrapInScrollView = true;
        dialogSearch = new MaterialDialog.Builder(this.getActivity())
                .title(R.string.browse_search)
                .customView(R.layout.layout_search, wrapInScrollView)
                .positiveText(R.string.browse_filter)
                .onPositive(new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                        String keyword = editKeyword.getText().toString();
                        String minValue = rangePrice.getSelectedMinValue().toString();
                        String maxValue = rangePrice.getSelectedMaxValue().toString();
                        ServiceManager.serviceSearchItems(BrowseFragment.this,keyword,minValue,maxValue);
                    }
                })
                .build();
        editKeyword = (EditText)dialogSearch.getCustomView().findViewById(R.id.editSearchKeyword);
        rangePrice = (RangeSeekBar) dialogSearch.getCustomView().findViewById(R.id.rangeSearchPrice);
        rangePrice.setRangeValues(0,1000);
        lstItems.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (Globals.lstSearchItems.size() > 0)
                {
                    Globals.currentItem = Globals.lstSearchItems.get(position - 1);
                    Intent m = new Intent(BrowseFragment.this.getContext(), DetailActivity.class);
                    BrowseFragment.this.startActivityForResult(m,10);

                }
                else {
                    Globals.currentItem = Globals.lstItems.get(position - 1);
                    Intent m = new Intent(BrowseFragment.this.getContext(), DetailActivity.class);
                    BrowseFragment.this.startActivityForResult(m,10);
                }
            }
        });

    }

    @Override
    public void onResponse(int code) {
        lstItems.stopRefresh();
        lstItems.stopLoadMore();
        switch (code)
        {
            case 200:
                adapterItem.update(Globals.lstItems);
                break;
            case 300:
                adapterItem.update(Globals.lstSearchItems);
                break;
        }
    }

    @Override
    public void onRefresh() {
        lstItems.stopRefresh();
        lstItems.stopLoadMore();
    }

    @Override
    public void onLoadMore() {
        ServiceManager.serviceLoadItemsUnderCategory(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId())
        {

        }
    }
}