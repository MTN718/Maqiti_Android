package com.songu.maqiti.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;


import com.songu.maqiti.R;
import com.songu.maqiti.doc.Globals;
import com.songu.maqiti.models.CountryModel;
import com.songu.maqiti.models.PriceModel;
import com.songu.maqiti.service.IServiceResult;
import com.songu.maqiti.service.ServiceManager;
import com.songu.maqiti.utils.Utils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 12/15/2015.
 */
public class SplashActivity extends Activity implements View.OnClickListener,IServiceResult
{
    private Handler handler = new Handler() {
        public void handleMessage(android.os.Message msg) {
            switch (msg.what) {
                case 1:
                    Intent m = new Intent(SplashActivity.this,HomeActivity.class);
                    m.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    SplashActivity.this.startActivity(m);
                    break;
                case 0:
                    m = new Intent(SplashActivity.this,CountrySelectActivity.class);
                    m.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    SplashActivity.this.startActivity(m);
                    break;
                default:
                    break;
            }
        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        initPriceFilter();
        SharedPreferences sp = this.getSharedPreferences("login", Context.MODE_PRIVATE);
        int isLogin = sp.getInt("login",0);
        String countryNo = sp.getString("countryNo","");

        if (!countryNo.equals(""))
        {
            String countryName = sp.getString("countryName","");
            String countryImage = sp.getString("countryImage","");

            Globals.currentCountry = new CountryModel();
            Globals.currentCountry.mNo = countryNo;
            Globals.currentCountry.mImage = countryImage;
            Globals.currentCountry.mName = countryName;

            handler.sendEmptyMessageDelayed(1,3000);
            return;
        }


        /*if (isLogin == 1)
        {
            String strMail = sp.getString("user","");
            String strPassword = sp.getString("password","");
            ServiceManager.serviceLogin(this,strMail,strPassword);
            return;
        }*/

        initView();

    }
    public void initView()
    {
        handler.sendEmptyMessageDelayed(0,3000);
    }



    public void initPriceFilter()
    {

        if (Globals.lstFilterPrice.size() > 0)
            return;
        PriceModel mPrice = new PriceModel("0 ~ 200",0,200);
        Globals.lstFilterPrice.add(mPrice);

        mPrice = new PriceModel("200 ~ 500",200,500);
        Globals.lstFilterPrice.add(mPrice);

        mPrice = new PriceModel("500 ~ 1000",500,1000);
        Globals.lstFilterPrice.add(mPrice);

        mPrice = new PriceModel("1000 ~ 2000",1000,2000);
        Globals.lstFilterPrice.add(mPrice);

        mPrice = new PriceModel("2000 ~ 5000",2000,5000);
        Globals.lstFilterPrice.add(mPrice);

        mPrice = new PriceModel("5000 +",5000,-1);
        Globals.lstFilterPrice.add(mPrice);
    }
    @Override
    public void onClick(View v)
    {
        switch(v.getId())
        {

        }
    }

    @Override
    public void onResponse(int code) {
        switch(code)
        {
            case 200:
                Utils.savePreference(this);
                Intent intent = new Intent(this,HomeActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                this.startActivity(intent);
                break;
        }
    }
}
