package com.songu.maqiti.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.songu.maqiti.R;
import com.songu.maqiti.adapter.AdapterCountry;
import com.songu.maqiti.doc.Globals;
import com.songu.maqiti.service.IServiceResult;
import com.songu.maqiti.service.ServiceManager;
import com.songu.maqiti.utils.Utils;

/**
 * Created by Administrator on 2/24/2017.
 */
public class CountrySelectActivity extends Activity implements View.OnClickListener,IServiceResult{

    public ListView lstCountry;
    public AdapterCountry adapterCountry;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_country);
        initView();
    }
    public void initView()
    {
        lstCountry = (ListView)this.findViewById(R.id.lstCountry);
        adapterCountry = new AdapterCountry(this);
        lstCountry.setAdapter(adapterCountry);
        ServiceManager.serviceLoadCountry(this);
        lstCountry.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Globals.currentCountry = Globals.lstCountry.get(position);
                Utils.saveCountryPreference(CountrySelectActivity.this);
                Intent m = new Intent(CountrySelectActivity.this,HomeActivity.class);
                m.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                CountrySelectActivity.this.startActivity(m);
            }
        });
    }

    @Override
    public void onResponse(int code) {
        switch (code)
        {
            case 200:
                adapterCountry.update(Globals.lstCountry);
                break;
            case 400:
                break;
        }
    }

    @Override
    public void onClick(View v) {

    }
}
