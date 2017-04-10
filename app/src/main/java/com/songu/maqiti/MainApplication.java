package com.songu.maqiti;

import android.app.Application;

import com.songu.maqiti.doc.Globals;
import com.songu.maqiti.models.PriceModel;
import com.songu.maqiti.view.autocomplete.managers.ContentManager;
import com.songu.maqiti.view.autocomplete.rest.RestClientManager;

import org.acra.ACRA;
import org.acra.ReportingInteractionMode;
import org.acra.annotation.ReportsCrashes;

/**
 * Created by Administrator on 3/22/2016.
 */
@ReportsCrashes(
        mailTo = "pgyhw718@hotmail.com", // my email here
        mode = ReportingInteractionMode.TOAST,
        resToastText = R.string.crash_toast_text)

public class MainApplication extends Application {

    @Override
    public void onCreate()
    {
        super.onCreate();
        RestClientManager.init(getApplicationContext());
        ContentManager.init(getApplicationContext());
        ACRA.init(this);

    }

    @Override
    public void onTerminate()
    {
        super.onTerminate();
    }


}
