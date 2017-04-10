package com.songu.maqiti.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.InflateException;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;

import com.afollestad.materialdialogs.MaterialDialog;
import com.songu.maqiti.R;
import com.songu.maqiti.activity.DetailActivity;
import com.songu.maqiti.activity.HomeActivity;
import com.songu.maqiti.adapter.AdapterItem;
import com.songu.maqiti.doc.Globals;
import com.songu.maqiti.service.IServiceResult;
import com.songu.maqiti.service.ServiceManager;
import com.songu.maqiti.view.rangeseekbar.RangeSeekBar;
import com.songu.maqiti.view.xlist.XListView;

/**
 * Created by Administrator on 2/25/2017.
 */
public class SearchResultFragment extends Fragment implements IServiceResult,XListView.IXListViewListener,View.OnClickListener {


    public View mView;
    private XListView lstResult;
    public AdapterItem adapterItem;
    public String strKeyword;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        try {
            mView = inflater.inflate(R.layout.fragment_searchresult, container, false);
        } catch (InflateException e) {

        }
        initView();
        return mView;
    }
    public void initView()
    {
        Globals.lstSearchItems.clear();
        lstResult = (XListView) mView.findViewById(R.id.lstItemsSearchResult);
        adapterItem = new AdapterItem(this.getActivity());
        lstResult.setAdapter(adapterItem);

        lstResult.setPullLoadEnable(true);
        lstResult.setXListViewListener(this);
        strKeyword = ((HomeActivity)getActivity()).editKeyword.getText().toString();
        ServiceManager.serviceSearchByKeyword(this,strKeyword);

        lstResult.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Globals.currentItem = Globals.lstSearchItems.get(position - 1);
                Intent m = new Intent(SearchResultFragment.this.getContext(), DetailActivity.class);
                SearchResultFragment.this.startActivityForResult(m,10);
            }
        });
    }

    @Override
    public void onResponse(int code) {
        lstResult.stopRefresh();
        lstResult.stopLoadMore();
        switch (code)
        {
            case 200:
                adapterItem.update(Globals.lstSearchItems);
                break;
        }
    }

    @Override
    public void onRefresh() {
        lstResult.stopRefresh();
        lstResult.stopLoadMore();
    }

    @Override
    public void onLoadMore() {
        ServiceManager.serviceSearchByKeyword(this,strKeyword);
    }
    
    @Override
    public void onClick(View v) {
        switch (v.getId())
        {

        }
    }
}