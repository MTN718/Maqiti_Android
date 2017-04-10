package com.songu.maqiti.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.InflateException;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.ScrollView;

import com.songu.maqiti.R;
import com.songu.maqiti.activity.HomeActivity;
import com.songu.maqiti.doc.Enums;
import com.songu.maqiti.doc.Globals;
import com.songu.maqiti.models.SubCategoryModel;
import com.songu.maqiti.models.ColorModel;
import com.songu.maqiti.models.FilterViewHolder;
import com.songu.maqiti.models.PriceModel;
import com.songu.maqiti.service.IServiceResult;
import com.songu.maqiti.service.ServiceManager;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 3/1/2017.
 */
public class FilterFragment extends Fragment implements View.OnClickListener,IServiceResult{

    public View mView;
    public ScrollView scrollFilter;
    public LinearLayout layoutCategory;
    public LinearLayout layoutColor;
    public LinearLayout layoutPrice;
    public Button btnFilter;

    public List<FilterViewHolder> categoryHolders = new ArrayList<>();
    public List<FilterViewHolder> colorHolders = new ArrayList<>();
    public List<FilterViewHolder> priceHolders = new ArrayList<>();


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        try {
            mView = inflater.inflate(R.layout.fragment_filter, container, false);
        } catch (InflateException e) {

        }
        initView();
        ServiceManager.serviceLoadFilter(this);
        return mView;
    }
    public void initView()
    {
        scrollFilter = (ScrollView) mView.findViewById(R.id.scrlFilter);
        layoutCategory = (LinearLayout) mView.findViewById(R.id.layoutFilterCategory);
        layoutColor = (LinearLayout) mView.findViewById(R.id.layoutFilterColor);
        layoutPrice = (LinearLayout) mView.findViewById(R.id.layoutFilterPrice);
        btnFilter = (Button) mView.findViewById(R.id.btnFilterFilter);

        btnFilter.setOnClickListener(this);
    }
    public void addCategoryViews()
    {
        layoutCategory.removeAllViews();
        for (int i = 0;i < Globals.lstFilterCategory.size() - 1;i+=2)
        {
            addCategoryView(Globals.lstFilterCategory.get(i),Globals.lstFilterCategory.get(i + 1));
        }
        if (Globals.lstFilterCategory.size() % 2 == 1)
            addCategoryView(Globals.lstFilterCategory.get(Globals.lstFilterCategory.size() - 1),null);
    }
    public void addColorViews()
    {
        layoutColor.removeAllViews();
        for (int i = 0;i < Globals.lstFilterColor.size() - 1;i+=2)
        {
            addColorView(Globals.lstFilterColor.get(i),Globals.lstFilterColor.get(i + 1));
        }
        if (Globals.lstFilterColor.size() % 2 == 1)
            addColorView(Globals.lstFilterColor.get(Globals.lstFilterColor.size() - 1),null);
    }

    public void addPriceViews()
    {
        layoutPrice.removeAllViews();
        for (int i = 0;i < Globals.lstFilterPrice.size() - 1;i+=2)
        {
            addPriceView(Globals.lstFilterPrice.get(i),Globals.lstFilterPrice.get(i + 1));
        }
        if (Globals.lstFilterPrice.size() % 2 == 1)
            addPriceView(Globals.lstFilterPrice.get(Globals.lstFilterPrice.size() - 1),null);
    }
    public void addPriceView(PriceModel item1, PriceModel item2)
    {
        View localView;
        FilterViewHolder localViewHolder = null;
        localView = LayoutInflater.from(this.getContext()).inflate(R.layout.item_filter, null);

        localViewHolder = new FilterViewHolder();
        localViewHolder.chkItemLeft = (CheckBox) localView.findViewById(R.id.chkFilterItem1);
        localViewHolder.chkItemRight = (CheckBox) localView.findViewById(R.id.chkFilterItem2);

        localView.setTag(localViewHolder);

        localViewHolder.chkItemLeft.setText(item1.mName);
        if (item2 != null) {
            localViewHolder.chkItemRight.setVisibility(View.VISIBLE);
            localViewHolder.chkItemRight.setText(item2.mName);
        }
        else localViewHolder.chkItemRight.setVisibility(View.INVISIBLE);

        localViewHolder.chkItemLeft.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    for (int i = 0;i < priceHolders.size();i++)
                    {
                        priceHolders.get(i).chkItemLeft.setChecked(false);
                        priceHolders.get(i).chkItemRight.setChecked(false);
                    }
                }
                buttonView.setChecked(isChecked);
            }
        });

        localViewHolder.chkItemRight.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    for (int i = 0;i < priceHolders.size();i++)
                    {
                        priceHolders.get(i).chkItemLeft.setChecked(false);
                        priceHolders.get(i).chkItemRight.setChecked(false);
                    }
                }
                buttonView.setChecked(isChecked);
            }
        });

        localView.setTag(localViewHolder);
        priceHolders.add(localViewHolder);
        layoutPrice.addView(localView);
    }
    public void addCategoryView(SubCategoryModel item1, SubCategoryModel item2)
    {
        View localView;
        FilterViewHolder localViewHolder = null;
        localView = LayoutInflater.from(this.getContext()).inflate(R.layout.item_filter, null);

        localViewHolder = new FilterViewHolder();
        localViewHolder.chkItemLeft = (CheckBox) localView.findViewById(R.id.chkFilterItem1);
        localViewHolder.chkItemRight = (CheckBox) localView.findViewById(R.id.chkFilterItem2);

        localView.setTag(localViewHolder);

        localViewHolder.chkItemLeft.setText(item1.mName);
        if (item2 != null) {
            localViewHolder.chkItemRight.setVisibility(View.VISIBLE);
            localViewHolder.chkItemRight.setText(item2.mName);
        }
        else localViewHolder.chkItemRight.setVisibility(View.INVISIBLE);

        categoryHolders.add(localViewHolder);
        localView.setTag(localViewHolder);
        layoutCategory.addView(localView);
    }

    public void addColorView(ColorModel item1, ColorModel item2)
    {
        View localView;
        FilterViewHolder localViewHolder = null;
        localView = LayoutInflater.from(this.getContext()).inflate(R.layout.item_filter, null);

        localViewHolder = new FilterViewHolder();
        localViewHolder.chkItemLeft = (CheckBox) localView.findViewById(R.id.chkFilterItem1);
        localViewHolder.chkItemRight = (CheckBox) localView.findViewById(R.id.chkFilterItem2);

        localView.setTag(localViewHolder);

        localViewHolder.chkItemLeft.setText(item1.mName);
        if (item2 != null) {
            localViewHolder.chkItemRight.setVisibility(View.VISIBLE);
            localViewHolder.chkItemRight.setText(item2.mName);
        }
        else localViewHolder.chkItemRight.setVisibility(View.INVISIBLE);

        colorHolders.add(localViewHolder);

        localView.setTag(localViewHolder);
        layoutColor.addView(localView);
    }
    @Override
    public void onResponse(int code) {
        switch (code)
        {
            case 200:
                addCategoryViews();
                addColorViews();
                addPriceViews();
                break;
            case 400:
                break;
        }
    }
    public void filterItems()
    {
        for (int i = 0;i < categoryHolders.size();i++)
        {
            if (categoryHolders.get(i).chkItemLeft.isChecked())
            {
                Globals.g_FilterModel.mCategory.add(Globals.lstFilterCategory.get(i * 2));
            }
            if (categoryHolders.get(i).chkItemRight.isChecked())
            {
                Globals.g_FilterModel.mCategory.add(Globals.lstFilterCategory.get((i * 2) + 1));
            }
        }
        for (int i = 0;i < colorHolders.size();i++)
        {
            if (colorHolders.get(i).chkItemLeft.isChecked())
            {
                Globals.g_FilterModel.mColor.add(Globals.lstFilterColor.get(i * 2));
            }
            if (colorHolders.get(i).chkItemRight.isChecked())
            {
                Globals.g_FilterModel.mColor.add(Globals.lstFilterColor.get((i * 2) + 1));
            }
        }
        for (int i = 0;i < priceHolders.size();i++)
        {
            if (priceHolders.get(i).chkItemLeft.isChecked())
            {
                Globals.g_FilterModel.mPrice = Globals.lstFilterPrice.get(i * 2);
                break;
            }
            if (priceHolders.get(i).chkItemRight.isChecked())
            {
                Globals.g_FilterModel.mPrice = Globals.lstFilterPrice.get((i * 2) + 1);
                break;
            }
        }
        Globals.e_mode = Enums.MODE.SEARCHRESULT;
        ((HomeActivity)getActivity()).setFragment();
    }
    @Override
    public void onClick(View v) {
        switch (v.getId())
        {
            case R.id.btnFilterFilter:
                filterItems();
                break;
        }
    }
}
