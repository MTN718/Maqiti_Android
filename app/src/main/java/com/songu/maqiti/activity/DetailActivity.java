package com.songu.maqiti.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewParent;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.afollestad.materialdialogs.MaterialDialog;
import com.songu.maqiti.R;
import com.songu.maqiti.adapter.AdapterItemPager;
import com.songu.maqiti.doc.Config;
import com.songu.maqiti.doc.Globals;
import com.songu.maqiti.models.FilterViewHolder;
import com.songu.maqiti.models.GridItemViewHolder;
import com.songu.maqiti.models.ItemModel;
import com.songu.maqiti.models.RateViewHolder;
import com.songu.maqiti.models.ReviewModel;
import com.songu.maqiti.models.SizeModel;
import com.songu.maqiti.models.ThumbHolder;
import com.songu.maqiti.service.IServiceResult;
import com.songu.maqiti.service.ServiceManager;
import com.songu.maqiti.utils.Utils;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2/22/2017.
 */
public class DetailActivity extends Activity implements View.OnClickListener,IServiceResult{

    public ViewPager vwItemImage;
    public AdapterItemPager adapterPager;
    public TextView txtName;
    public TextView txtPrice;
    public TextView txtDescription;
    public TextView txtEmail;
    public TextView txtPhone;
    public TextView txtBrand;
    public ImageView imgClose;
    public Button btnAddCart;
    public Button btnBuyNow;
    public MaterialDialog mSuccessDialog;
    public LinearLayout layoutReviews;
    public LinearLayout layoutRelated;
    public LinearLayout layoutSizes;
    public List<FilterViewHolder> sizeHolders = new ArrayList<>();
    public LinearLayout layoutThumbs;
    public List<ThumbHolder> lstHolders = new ArrayList<>();


    private Handler handler = new Handler() {
        public void handleMessage(android.os.Message msg) {
            switch (msg.what) {
                case 0:
                    mSuccessDialog.dismiss();
                    break;
                case 1:
                    int k = vwItemImage.getCurrentItem();
                    if (Globals.currentThumb < vwItemImage.getCurrentItem()) {
                        vwItemImage.setCurrentItem(vwItemImage.getCurrentItem() - 1);
                        handler.sendEmptyMessageDelayed(1,100);
                    }
                    else if (Globals.currentThumb  > vwItemImage.getCurrentItem())
                    {
                        vwItemImage.setCurrentItem(vwItemImage.getCurrentItem() + 1);
                        handler.sendEmptyMessageDelayed(1,100);
                    }
                    break;
            }
        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        initView();
    }

    public void initView()
    {
        if (!Globals.lstRecommend.contains(Globals.currentItem))
            Globals.lstRecommend.add(Globals.currentItem);

        vwItemImage = (ViewPager) findViewById(R.id.vpItem);
        txtPhone = (TextView) findViewById(R.id.txtDetailItemPhone);
        txtEmail = (TextView) findViewById(R.id.txtDetailItemEmail);
        txtDescription = (TextView) findViewById(R.id.txtDetailItemDescription);
        txtPrice = (TextView)findViewById(R.id.txtDetailItemPrice);
        txtName = (TextView) findViewById(R.id.txtDetailItemName);
        imgClose = (ImageView) findViewById(R.id.imgDetailClose);
        layoutReviews = (LinearLayout) findViewById(R.id.layoutDetailReviews);
        layoutRelated = (LinearLayout) findViewById(R.id.layoutRelated);
        layoutSizes = (LinearLayout) findViewById(R.id.layoutDetailSize);
        layoutThumbs = (LinearLayout) findViewById(R.id.layoutDetailProductThumbs);
        txtBrand = (TextView) findViewById(R.id.txtDetailItemBrand);

        btnAddCart = (Button) findViewById(R.id.btnDetailAddCart);
        btnBuyNow = (Button) findViewById(R.id.btnDetailBuyNow);


        adapterPager = new AdapterItemPager(this,Globals.currentItem);
        vwItemImage.setAdapter(adapterPager);

        txtPhone.setText(Globals.currentItem.mPhone);
        txtDescription.setText(Globals.currentItem.mDescription);
        txtPrice.setText(Utils.getPriceValue(Globals.currentItem.mPrice) + Utils.getResourceString(this,R.string.currency));
        txtEmail.setText(Globals.currentItem.mEmail);
        txtName.setText(Globals.currentItem.mName);
        txtBrand.setText(Globals.currentItem.mBrand);

        imgClose.setOnClickListener(this);
        btnAddCart.setOnClickListener(this);
        btnBuyNow.setOnClickListener(this);

        mSuccessDialog = new MaterialDialog.Builder(this)
                .title(Utils.getResourceString(this,R.string.message_success))
                .content(Utils.getResourceString(this,R.string.message_addedcart))
                .build();

        if (Globals.mAccount != null) {
            btnAddCart.setVisibility(View.VISIBLE);
        }
        else
        {
            btnAddCart.setVisibility(View.GONE);
        }
        addThumbs();
        ServiceManager.serviceLoadItemInfo(this);

        vwItemImage.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                for (int i = 0;i < lstHolders.size();i++)
                {
                    lstHolders.get(i).imgThumb.setBackground(null);
                }
                lstHolders.get(position).imgThumb.setBackground(getResources().getDrawable(R.drawable.drawable_stroke_red));
                //Globals.currentThumb  = position;
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

    }

    @Override
    public void onClick(View v) {
        switch (v.getId())
        {
            case R.id.imgDetailClose:
                finish();
                break;
            case R.id.btnDetailAddCart:
                if (btnAddCart.getText().equals(Utils.getResourceString(this,R.string.detail_addcart))) {
                    ServiceManager.serviceAddFavourite(this);
                    btnAddCart.setText(Utils.getResourceString(this, R.string.detail_addcart_done));
                }
                else
                {
                    ServiceManager.serviceRemoveFavourite(this);
                    btnAddCart.setText(Utils.getResourceString(this, R.string.detail_addcart));
                }
                break;
            case R.id.btnDetailBuyNow:
                //Get Size
                for (int i = 0;i < sizeHolders.size();i++)
                {
                    if (sizeHolders.get(i).chkItemLeft.isChecked())
                    {
                        Globals.currentItem.mCurrentSize = Globals.lstSizes.get(i * 2).mNo;
                        break;
                    }
                    if (sizeHolders.get(i).chkItemRight.isChecked())
                    {
                        Globals.currentItem.mCurrentSize = Globals.lstSizes.get(i * 2 + 1).mNo;
                        break;
                    }
                }
                if (Globals.currentItem.mCurrentSize.equals("-1") && Globals.lstSizes.size() > 0) {
                    Toast.makeText(this, Utils.getResourceString(this, R.string.detail_toast), Toast.LENGTH_SHORT).show();
                    return;
                }
                Globals.lstCarts.add(Globals.currentItem);
                mSuccessDialog.show();
                handler.sendEmptyMessageDelayed(0,2000);
                break;
        }
    }
    public void addThumbs()
    {
        layoutThumbs.removeAllViews();
        lstHolders.clear();
        for (int i = 0;i < Globals.currentItem.mImages.size();i++)
        {
            addImageView(Globals.currentItem.mImages.get(i),i);
        }
        lstHolders.get(0).imgThumb.setBackground(getResources().getDrawable(R.drawable.drawable_stroke_red));
    }
    public void addImageView(final String path,final int index)
    {
        View localView;
        localView = LayoutInflater.from(this).inflate(R.layout.item_thumbs, null);

        final ThumbHolder localViewHolder = new ThumbHolder();
        localViewHolder.imgThumb = (ImageView) localView.findViewById(R.id.imgProductThumb);
        localView.setTag(localViewHolder);

        Picasso.with(DetailActivity.this)
                .load(Config.mImageBaseUrl + path)
                .placeholder(R.color.color_gray)
                .into(localViewHolder.imgThumb);
        localViewHolder.imgThumb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                for (int i = 0;i < lstHolders.size();i++)
                {
                    lstHolders.get(i).imgThumb.setBackground(null);
                }
                lstHolders.get(index).imgThumb.setBackground(getResources().getDrawable(R.drawable.drawable_stroke_red));
                Globals.currentThumb  = index;
                handler.sendEmptyMessageDelayed(1,100);
                //vwItemImage.setCurrentItem(index);
            }
        });
        lstHolders.add(localViewHolder);
        layoutThumbs.addView(localView);
    }
    public void addReviews()
    {
        layoutReviews.removeAllViews();
        for (int i = 0;i < Globals.lstReviews.size();i++)
        {
            addView(Globals.lstReviews.get(i));
        }
    }
    public void addRelatedViews()
    {
        layoutRelated.removeAllViews();
        for (int i = 0;i < Globals.lstRelated.size();i++)
        {
            addRelateView(Globals.lstRelated.get(i));
        }
    }
    public void addSizes()
    {
        layoutSizes.removeAllViews();
        if (Globals.lstSizes.size() > 0) {
            for (int i = 0; i < Globals.lstSizes.size() - 1; i += 2) {
                addSizeView(Globals.lstSizes.get(i), Globals.lstSizes.get(i + 1));
            }
            if (Globals.lstSizes.size() % 2 == 1)
                addSizeView(Globals.lstSizes.get(Globals.lstSizes.size() - 1), null);
        }
    }
    public void addSizeView(final SizeModel left,final SizeModel right)
    {
        View localView;
        FilterViewHolder localViewHolder = null;
        localView = LayoutInflater.from(this).inflate(R.layout.item_filter, null);

        localViewHolder = new FilterViewHolder();
        localViewHolder.chkItemLeft = (CheckBox) localView.findViewById(R.id.chkFilterItem1);
        localViewHolder.chkItemRight = (CheckBox) localView.findViewById(R.id.chkFilterItem2);

        localView.setTag(localViewHolder);

        localViewHolder.chkItemLeft.setText(left.mSize);
        if (right != null) {
            localViewHolder.chkItemRight.setVisibility(View.VISIBLE);
            localViewHolder.chkItemRight.setText(right.mSize);
        }
        else localViewHolder.chkItemRight.setVisibility(View.INVISIBLE);

        localViewHolder.chkItemLeft.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    for (int i = 0;i < sizeHolders.size();i++)
                    {
                        sizeHolders.get(i).chkItemLeft.setChecked(false);
                        sizeHolders.get(i).chkItemRight.setChecked(false);
                    }
                }
                buttonView.setChecked(isChecked);
            }
        });

        localViewHolder.chkItemRight.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    for (int i = 0;i < sizeHolders.size();i++)
                    {
                        sizeHolders.get(i).chkItemLeft.setChecked(false);
                        sizeHolders.get(i).chkItemRight.setChecked(false);
                    }
                }
                buttonView.setChecked(isChecked);
            }
        });

        localView.setTag(localViewHolder);
        sizeHolders.add(localViewHolder);
        layoutSizes.addView(localView);
    }
    public void addRelateView(final ItemModel mModel)
    {
        View localView;
        GridItemViewHolder localViewHolder = null;
        localView = LayoutInflater.from(this).inflate(R.layout.item_grid, null);

        localViewHolder = new GridItemViewHolder();
        localViewHolder.imgItem = (ImageView) localView.findViewById(R.id.imgGridItem);
        localViewHolder.txtItemName = (TextView) localView.findViewById(R.id.txtGridItemName);
        localViewHolder.txtItemPrice = (TextView) localView.findViewById(R.id.txtGridItemPrice);
        localViewHolder.txtItemBrand = (TextView) localView.findViewById(R.id.txtGridItemBrand);

        localViewHolder.rateStar = (RatingBar)localView.findViewById(R.id.ratingGridItem);
        localViewHolder.txtReviews = (TextView) localView.findViewById(R.id.txtRateGridItem);
        localView.setTag(localViewHolder);

        localViewHolder.txtItemName.setText(mModel.mName);
        localViewHolder.txtItemPrice.setText(Utils.getPriceValue(mModel.mPrice) + Utils.getResourceString(this,R.string.currency));
        localViewHolder.txtItemBrand.setText(mModel.mBrand);

        localViewHolder.rateStar.setRating(Float.parseFloat(mModel.mStars));
        localViewHolder.txtReviews.setText("(" + mModel.mCountReview + ")");
        if (mModel.mImages.size() > 0) {
            Picasso.with(this)
                    .load(Config.mImageBaseUrl + mModel.mImages.get(0))
                    .placeholder(this.getResources().getDrawable(R.drawable.cartplaceholder))
                    .into(localViewHolder.imgItem);
        }
        localView.setTag(localViewHolder);
        localView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Globals.currentItem = mModel;
                Intent m = new Intent(DetailActivity.this, DetailActivity.class);
                DetailActivity.this.startActivityForResult(m,10);
            }
        });
        layoutRelated.addView(localView);
    }
    public void addView(final ReviewModel rModel)
    {
        View localView;
        RateViewHolder localViewHolder = null;
        localView = LayoutInflater.from(this).inflate(R.layout.item_review, null);

        localViewHolder = new RateViewHolder();
        localViewHolder.txtComment = (TextView) localView.findViewById(R.id.txtRateComment);
        localViewHolder.rateStar = (RatingBar) localView.findViewById(R.id.rateStars);
        localViewHolder.txtUser = (TextView) localView.findViewById(R.id.txtRateUser);

        localView.setTag(localViewHolder);

        localViewHolder.txtComment.setText(rModel.mComment);
        localViewHolder.txtUser.setText(rModel.mUser);
        localViewHolder.rateStar.setRating(Float.parseFloat(rModel.mReview));
        layoutReviews.addView(localView);
    }
    @Override
    public void onResponse(int code) {
        switch (code)
        {
            case 200:
                btnAddCart.setText(Utils.getResourceString(this,R.string.detail_addcart_done));
                break;
            case 400:
                btnAddCart.setText(Utils.getResourceString(this,R.string.detail_addcart));
                break;
        }
        addReviews();
        addSizes();
        addRelatedViews();
    }
}
