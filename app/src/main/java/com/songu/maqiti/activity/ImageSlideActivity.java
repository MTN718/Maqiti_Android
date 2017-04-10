package com.songu.maqiti.activity;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.view.ViewPager;

import com.songu.maqiti.R;
import com.songu.maqiti.adapter.AdapterImageSlide;
import com.songu.maqiti.doc.Globals;

/**
 * Created by Administrator on 3/1/2017.
 */
public class ImageSlideActivity extends Activity {

    public ViewPager vwPager;
    public AdapterImageSlide adapterImageSlide;

    private Handler handler = new Handler() {
        public void handleMessage(android.os.Message msg) {
            switch (msg.what) {
                case 1:
                    if (Globals.currentThumb < vwPager.getCurrentItem()) {
                        vwPager.setCurrentItem(vwPager.getCurrentItem() - 1);
                        handler.sendEmptyMessageDelayed(1,100);
                    }
                    else if (Globals.currentThumb  > vwPager.getCurrentItem())
                    {
                        vwPager.setCurrentItem(vwPager.getCurrentItem() + 1);
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
        setContentView(R.layout.activity_slide);
        initView();
    }
    public void initView()
    {
        vwPager = (ViewPager) this.findViewById(R.id.vpDetailItem);
        adapterImageSlide = new AdapterImageSlide(this, Globals.currentItem);
        vwPager.setAdapter(adapterImageSlide);
        handler.sendEmptyMessageDelayed(1,100);
    }
}
