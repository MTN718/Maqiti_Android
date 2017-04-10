package com.songu.maqiti.fragment;

import android.content.Context;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.InflateException;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import com.afollestad.materialdialogs.MaterialDialog;
import com.songu.maqiti.R;
import com.songu.maqiti.activity.HomeActivity;
import com.songu.maqiti.adapter.AutoCompleteAdapter;
import com.songu.maqiti.doc.Enums;
import com.songu.maqiti.doc.Globals;
import com.songu.maqiti.models.UserModel;
import com.songu.maqiti.service.IServiceResult;
import com.songu.maqiti.service.ServiceManager;
import com.songu.maqiti.utils.Utils;
import com.songu.maqiti.view.DelayAutoCompleteTextView;

import java.io.IOException;
import java.util.List;

/**
 * Created by Administrator on 2/25/2017.
 */
public class RegisterFragment extends Fragment implements View.OnClickListener,IServiceResult,LocationListener {

    public EditText editUser;
    public EditText editEmail;
    public EditText editPassword;
    public EditText editConfirm;
    public EditText editRegisterPhone;
    public Button btnRegister;
    public View mView;
    public DelayAutoCompleteTextView autoCompleteTextView;
    public AutoCompleteAdapter autoCompleteAdapter;
    public CheckBox chkShareLocation;

    boolean isGPSEnabled = false;

    // flag for network status
    boolean isNetworkEnabled = false;

    // flag for GPS status
    boolean canGetLocation = false;

    Location location; // location
    String latitude; // latitude
    String longitude; // longitude

    protected LocationManager locationManager;

    private static final long MIN_DISTANCE_CHANGE_FOR_UPDATES = 10; // 10 meters

    // The minimum time between updates in milliseconds
    private static final long MIN_TIME_BW_UPDATES = 1000 * 60 * 1; // 1 minute
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        try {
            mView = inflater.inflate(R.layout.fragment_register, container, false);
        } catch (InflateException e) {

        }
        initView();
        return mView;
    }
    public void initView()
    {
        editUser = (EditText) mView.findViewById(R.id.editRegisterUser);
        editPassword = (EditText) mView.findViewById(R.id.editRegsiterPassword);
        editConfirm = (EditText) mView.findViewById(R.id.editRegisterConfirm);
        editEmail = (EditText) mView.findViewById(R.id.editRegisterEmail);
        btnRegister = (Button) mView.findViewById(R.id.btnRegisterRegister);
        autoCompleteTextView = (DelayAutoCompleteTextView)mView.findViewById(R.id.cityTitle);
        autoCompleteAdapter = new AutoCompleteAdapter(this.getActivity());
        autoCompleteTextView.setAdapter(autoCompleteAdapter);
        autoCompleteTextView.setThreshold(1);
        editRegisterPhone = (EditText) mView.findViewById(R.id.editRegisterPhone);
        chkShareLocation = (CheckBox) mView.findViewById(R.id.chkRegisterShareLocation);

        btnRegister.setOnClickListener(this);

        autoCompleteTextView.setOnItemClickListener(new AdapterView.OnItemClickListener()
        {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id)
            {


            }
        });
    }

    public void processSignUp()
    {
        String strUser = editUser.getText().toString();
        String strPassword = editPassword.getText().toString();
        String strConfrim = editConfirm.getText().toString();
        String strEmail = editEmail.getText().toString();
        String strPhone = editRegisterPhone.getText().toString();
        String strAddress = autoCompleteTextView.getText().toString();

        if (strUser.equals(""))
        {
            Toast.makeText(this.getActivity(), Utils.getResourceString(this.getActivity(),R.string.message_empty_user),Toast.LENGTH_SHORT).show();
            return;
        }
        if (strPassword.equals("") || strConfrim.equals(""))
        {
            Toast.makeText(this.getActivity(), Utils.getResourceString(this.getActivity(),R.string.message_empty_password), Toast.LENGTH_SHORT).show();
            return;
        }
        if (strEmail.equals(""))
        {
            Toast.makeText(this.getActivity(),Utils.getResourceString(this.getActivity(),R.string.message_empty_email),Toast.LENGTH_SHORT).show();
            return;
        }
        if (!strEmail.contains("@"))
        {
            Toast.makeText(this.getActivity(),Utils.getResourceString(this.getActivity(),R.string.message_email_validate),Toast.LENGTH_SHORT).show();
            return;
        }
        if (!strPassword.equals(strConfrim))
        {
            Toast.makeText(this.getActivity(),Utils.getResourceString(this.getActivity(),R.string.message_password_match),Toast.LENGTH_SHORT).show();
            return;
        }
        if (strPhone.equals(""))
        {
            Toast.makeText(this.getActivity(),Utils.getResourceString(this.getActivity(),R.string.message_empty_phone),Toast.LENGTH_SHORT).show();
            return;
        }
        if (strAddress.equals(""))
        {
            Toast.makeText(this.getActivity(),Utils.getResourceString(this.getActivity(),R.string.message_empty_address_register),Toast.LENGTH_SHORT).show();
            return;
        }

        UserModel mModel = new UserModel();
        mModel.mUser = strUser;
        mModel.mPassword = strPassword;
        mModel.mEmail = strEmail;
        mModel.mPhone = strPhone;
        mModel.mAddress = autoCompleteTextView.getText().toString();

        Geocoder geocoder = new Geocoder(RegisterFragment.this.getActivity());
        List<Address> addresses;
        try {
            addresses = geocoder.getFromLocationName(autoCompleteTextView.getText().toString(), 1);
            if(addresses.size() > 0) {
                double latitude= addresses.get(0).getLatitude();
                double longitude= addresses.get(0).getLongitude();
                mModel.mLat = String.valueOf(latitude);
                mModel.mLon = String.valueOf(longitude);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (chkShareLocation.isChecked())
        {

        }
        ServiceManager.serviceRegister(this,mModel);
    }

    public Location getLocation() {
        try {
            locationManager = (LocationManager) this.getActivity().getSystemService(Context.LOCATION_SERVICE);

            // getting GPS status
            isGPSEnabled = locationManager
                    .isProviderEnabled(LocationManager.GPS_PROVIDER);

            // getting network status
            isNetworkEnabled = locationManager
                    .isProviderEnabled(LocationManager.NETWORK_PROVIDER);

            if (!isGPSEnabled && !isNetworkEnabled) {
                // no network provider is enabled
            } else {
                this.canGetLocation = true;
                // First get location from Network Provider
                if (isNetworkEnabled) {
                    locationManager.requestLocationUpdates(
                            LocationManager.NETWORK_PROVIDER,
                            MIN_TIME_BW_UPDATES,
                            MIN_DISTANCE_CHANGE_FOR_UPDATES, this);
                    if (locationManager != null) {
                        location = locationManager
                                .getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
                        if (location != null) {

                        }
                    }
                }
                // if GPS Enabled get lat/long using GPS Services
                if (isGPSEnabled) {
                    if (location == null) {
                        locationManager.requestLocationUpdates(
                                LocationManager.GPS_PROVIDER,
                                MIN_TIME_BW_UPDATES,
                                MIN_DISTANCE_CHANGE_FOR_UPDATES, this);
                        if (locationManager != null) {
                            location = locationManager
                                    .getLastKnownLocation(LocationManager.GPS_PROVIDER);
                            if (location != null) {

                            }
                        }
                    }
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return location;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId())
        {
            case R.id.btnRegisterRegister:
                processSignUp();
                break;
        }
    }

    @Override
    public void onResponse(int code) {
        if (code == 200)
        {
            Utils.savePreference(this.getActivity());
            Globals.e_mode = Enums.MODE.HOME;
            ((HomeActivity)getActivity()).setFragment();
            ((HomeActivity)getActivity()).updateData();
        }
        else
        {
            new MaterialDialog.Builder(this.getActivity())
                    .title(Utils.getResourceString(this.getActivity(),R.string.message_error))
                    .content(Utils.getResourceString(this.getActivity(),R.string.message_signupfail))
                    .show();
        }
    }


    @Override
    public void onLocationChanged(Location location) {

    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(String provider) {

    }

    @Override
    public void onProviderDisabled(String provider) {

    }
}
