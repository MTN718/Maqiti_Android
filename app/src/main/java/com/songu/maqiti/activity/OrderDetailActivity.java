package com.songu.maqiti.activity;

import android.app.Activity;
import android.media.Rating;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.ipmacro.utils.aes.AESUtil;
import com.songu.maqiti.R;
import com.songu.maqiti.adapter.AdapterItem;
import com.songu.maqiti.adapter.AdapterItemPager;
import com.songu.maqiti.doc.Globals;
import com.songu.maqiti.service.IServiceResult;
import com.songu.maqiti.service.ServiceManager;
import com.songu.maqiti.utils.Utils;

/**
 * Created by Administrator on 2/22/2017.
 */
public class OrderDetailActivity extends Activity implements View.OnClickListener,IServiceResult{


    public TextView txtName;
    public TextView txtStatus;
    public TextView txtPrice;
    public ListView lstItems;
    public TextView txtAddress;
    public ImageView imgClose;
    public Button btnAction;
    public AdapterItem adapterItem;
    public MaterialDialog dialogRate;
    public EditText editComment;
    public RatingBar rateOrder;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_orderdetail);
        initView();
    }
    public void initView()
    {
        txtStatus = (TextView) findViewById(R.id.txtOrderDetailStatus);
        lstItems = (ListView) findViewById(R.id.lstOrderDetailItems);
        txtPrice = (TextView) findViewById(R.id.txtOrderDetailAmount);
        txtAddress = (TextView) findViewById(R.id.txtOrderDetailAddress);
        txtPrice = (TextView)findViewById(R.id.txtOrderDetailAmount);
        txtName = (TextView) findViewById(R.id.txtOrderDetailName);
        imgClose = (ImageView) findViewById(R.id.imgOrderDetailClose);
        adapterItem = new AdapterItem(this);
        lstItems.setAdapter(adapterItem);
        btnAction = (Button) findViewById(R.id.btnOrderDetailComplete);
        setData();
        ServiceManager.serviceLoadOrdersDetail(this);

        imgClose.setOnClickListener(this);
        btnAction.setOnClickListener(this);

        dialogRate = new MaterialDialog.Builder(this)
                .title(R.string.order_review_button)
                .customView(R.layout.layout_review, true)
                .positiveText(R.string.order_review_button)
                .negativeText(R.string.profile_cancel)
                .autoDismiss(false)
                .onPositive(new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                        float rate = rateOrder.getRating();
                        String strComment = editComment.getText().toString();
                        if (strComment.equals(""))
                        {
                            Toast.makeText(OrderDetailActivity.this,"No Comment..",Toast.LENGTH_SHORT).show();
                            return;
                        }
                        ServiceManager.serviceAddReview(OrderDetailActivity.this,String.valueOf(rate),strComment);
                        dialog.dismiss();
                        Globals.currentOrder.mStatus = "3";
                        initView();
                        setResult(100);
                        finish();
                    }
                })
                .onNegative(new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                        dialog.dismiss();
                    }
                })
                .build();
        rateOrder = (RatingBar)dialogRate.getCustomView().findViewById(R.id.rateOrder);
        editComment = (EditText) dialogRate.getCustomView().findViewById(R.id.editRateOrder);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId())
        {
            case R.id.imgOrderDetailClose:
                finish();
                break;
            case R.id.btnOrderDetailComplete:
                if (Integer.parseInt(Globals.currentOrder.mStatus) == 0)
                {
                    ServiceManager.serviceDeleteOrder(this);
                    Globals.currentOrder.mStatus = "4";
                    initView();
                    setResult(100);
                    finish();
                }
                else if (Integer.parseInt(Globals.currentOrder.mStatus) == 1) {
                    ServiceManager.serviceUpdateOrderComplete(this);
                    Globals.currentOrder.mStatus = "2";
                    initView();
                    setResult(100);
                    finish();
                }
                else if (Integer.parseInt(Globals.currentOrder.mStatus) == 2)
                {
                    dialogRate.show();
                }
                break;
        }
    }
    public void setData()
    {
        adapterItem.update(Globals.currentOrder.mItemBuy);
        txtAddress.setText(Globals.currentOrder.mAddress);
        txtName.setText(Globals.currentOrder.mName);
        txtPrice.setText(Utils.getResourceString(this,R.string.checkout_total) + Utils.getPriceValue(Globals.currentOrder.mPrice) + Utils.getResourceString(this,R.string.currency));
        switch (Integer.parseInt(Globals.currentOrder.mStatus))
        {
            case 0:
                txtStatus.setText(Utils.getResourceString(this,R.string.order_status1));
                btnAction.setText(Utils.getResourceString(this,R.string.order_cancel));
                btnAction.setVisibility(View.VISIBLE);
                break;
            case 1:
                txtStatus.setText(Utils.getResourceString(this,R.string.order_status2));
                btnAction.setText(Utils.getResourceString(this,R.string.order_complete));
                btnAction.setVisibility(View.VISIBLE);
                break;
            case 2:
                txtStatus.setText(Utils.getResourceString(this,R.string.order_status3));
                btnAction.setText(Utils.getResourceString(this,R.string.order_review));
                btnAction.setVisibility(View.VISIBLE);
                break;
            case 3:
                txtStatus.setText(Utils.getResourceString(this,R.string.order_status4));
                btnAction.setVisibility(View.GONE);
                break;
            case 4:
                txtStatus.setText(Utils.getResourceString(this,R.string.order_status5));
                btnAction.setVisibility(View.GONE);
                break;
        }
    }
    public void addReviews()
    {

    }
    @Override
    public void onResponse(int code) {
        switch (code)
        {
            case 200:
                setData();
                break;
            case 400:
                break;
        }
    }
}
