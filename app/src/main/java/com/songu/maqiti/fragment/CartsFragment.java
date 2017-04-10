package com.songu.maqiti.fragment;

import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.InflateException;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;

import com.songu.maqiti.R;
import com.songu.maqiti.activity.DetailActivity;
import com.songu.maqiti.activity.HomeActivity;
import com.songu.maqiti.adapter.AdapterCart;
import com.songu.maqiti.adapter.AdapterItem;
import com.songu.maqiti.doc.Enums;
import com.songu.maqiti.doc.Globals;
import com.songu.maqiti.models.OrderModel;
import com.songu.maqiti.service.IServiceResult;
import com.songu.maqiti.service.ServiceManager;
import com.songu.maqiti.utils.Utils;

import java.io.File;
import java.math.BigDecimal;

/**
 * Created by Administrator on 2/25/2017.
 */
public class CartsFragment extends Fragment implements View.OnClickListener,IServiceResult,LocationListener {

    public View mView;
    public ListView lstCarts;
    public LinearLayout layoutCarts;
    public AdapterCart adapterItem;
    public TextView txtNoItems;
    public TextView txtPriceCart;
    public Button btnCheckout;
    public TextView editCheckoutAddress;
    public EditText editAddress;
    public MaterialDialog progressDialog;
    public int totalPrice = 0;

    //private static final String CONFIG_ENVIRONMENT = PayPalConfiguration.ENVIRONMENT_PRODUCTION;
    //private static final String CONFIG_ENVIRONMENT = PayPalConfiguration.ENVIRONMENT_SANDBOX;

    // note that these credentials will differ between live & sandbox environments.
    //private static final String CONFIG_CLIENT_ID = "credentials from developer.paypal.com";
    private static final String CONFIG_CLIENT_ID = "AU9IPNPizwr4o1iyWIDMyN17422o8kuS2yzcJz-SxWSNjnNHvqiFq3AiE7vvI5RBuMHevrTtM7tZ_um4";
    private static final int REQUEST_CODE_PAYMENT = 1;
    //private static final String CONFIG_CLIENT_ID = "AYFKfBAU1ydwlSX0PRYHNY2ep3Vfzt2NXj61JhKTLxmANufKO28ZAow27qRabB2rqq6hGGdcuASqYnEY";


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



//    private static PayPalConfiguration config = new PayPalConfiguration()
//            .environment(CONFIG_ENVIRONMENT)
//            .clientId(CONFIG_CLIENT_ID)
//            // The following are only used in PayPalFuturePaymentActivity.
//            .merchantName("Example Merchant")
//            .merchantPrivacyPolicyUri(Uri.parse("https://www.example.com/privacy"))
//            .merchantUserAgreementUri(Uri.parse("https://www.example.com/legal"));


    public CheckBox chkBox;
    public CheckBox chkDifferent;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        try {
            mView = inflater.inflate(R.layout.fragment_carts, container, false);
        } catch (InflateException e) {

        }
        initView();
        return mView;
    }
    public void initView()
    {
        txtNoItems = (TextView) mView.findViewById(R.id.txtCartNoItems);
        lstCarts = (ListView) mView.findViewById(R.id.lstCarts);
        layoutCarts = (LinearLayout) mView.findViewById(R.id.layoutCarts);
        txtPriceCart = (TextView) mView.findViewById(R.id.txtCartTotalPrice);
        btnCheckout = (Button) mView.findViewById(R.id.btnCartCheckOut);
        editCheckoutAddress = (TextView) mView.findViewById(R.id.editCheckoutItemAddress);
        editAddress = (EditText) mView.findViewById(R.id.editCheckoutItemAddressOther);
        chkBox = (CheckBox) mView.findViewById(R.id.chkCartShareLocation);
        chkDifferent = (CheckBox) mView.findViewById(R.id.chkShipOther);

        btnCheckout.setOnClickListener(this);
        adapterItem = new AdapterCart(this.getActivity(),this);
        lstCarts.setAdapter(adapterItem);
        editAddress.setVisibility(View.GONE);
        progressDialog = new MaterialDialog.Builder(this.getActivity())
                .title(Utils.getResourceString(this.getActivity(),R.string.message_process))
                .content(Utils.getResourceString(this.getActivity(),R.string.message_generate_order))
                .progress(true, 0).build();
        updatePrice();
        editAddress.setVisibility(View.GONE);
        chkBox.setVisibility(View.GONE);
        chkBox.setChecked(false);
        chkDifferent.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    editAddress.setVisibility(View.VISIBLE);
                    chkBox.setVisibility(View.VISIBLE);
                    chkBox.setChecked(true);
                }
                else
                {
                    editAddress.setVisibility(View.GONE);
                    chkBox.setVisibility(View.GONE);
                    chkBox.setChecked(false);
                }
            }
        });
        if (Globals.mAccount != null)
            editCheckoutAddress.setText(Globals.mAccount.mAddress);

        lstCarts.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Globals.currentItem = Globals.lstCarts.get(position);
                Intent m = new Intent(CartsFragment.this.getContext(), DetailActivity.class);
                CartsFragment.this.startActivityForResult(m,10);
            }
        });
    }

    public void updatePrice()
    {
        if (Globals.lstCarts.size() == 0)
        {
            txtNoItems.setVisibility(View.VISIBLE);
            layoutCarts.setVisibility(View.GONE);
        }
        else {
            txtNoItems.setVisibility(View.GONE);
            layoutCarts.setVisibility(View.VISIBLE);
            adapterItem.update(Globals.lstCarts);
        }

        for (int i = 0;i < Globals.lstCarts.size();i++)
        {
            totalPrice = totalPrice + Integer.parseInt(Globals.lstCarts.get(i).mPrice);
        }
        txtPriceCart.setText(Utils.getResourceString(this.getActivity(),R.string.checkout_total) + Utils.getPriceValue(String.valueOf(totalPrice)) + Utils.getResourceString(CartsFragment.this.getActivity(),R.string.currency));
    }
    @Override
    public void onClick(View v) {
        switch (v.getId())
        {
            case R.id.btnCartCheckOut:
                if (Globals.mAccount == null)
                {
                    new MaterialDialog.Builder(this.getActivity())
                            .title(Utils.getResourceString(this.getActivity(),R.string.message_notice))
                            .content(Utils.getResourceString(this.getActivity(),R.string.message_loginoffer))
                            .positiveText(Utils.getResourceString(this.getActivity(),R.string.message_login))
                            .negativeText(Utils.getResourceString(this.getActivity(),R.string.profile_cancel))
                            .onPositive(new MaterialDialog.SingleButtonCallback() {
                                @Override
                                public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                                    Globals.e_mode = Enums.MODE.LOGIN;
                                    ((HomeActivity)CartsFragment.this.getActivity()).setFragment();
                                }
                            })
                            .onNegative(new MaterialDialog.SingleButtonCallback() {
                                @Override
                                public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                                    dialog.dismiss();
                                }
                            })
                            .show();
                }
                else
                {
                    Globals.currentLocation = getLocation();
                    new MaterialDialog.Builder(this.getActivity())
                            .title(R.string.order_choosepayment)
                            .positiveText(Utils.getResourceString(this.getActivity(),R.string.order_payment3))
                            .neutralText(Utils.getResourceString(this.getActivity(),R.string.order_payment1))
                            .onPositive(new MaterialDialog.SingleButtonCallback() {
                                @Override
                                public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                                    processOrderBank();
                                }
                            })
                            .onNeutral(new MaterialDialog.SingleButtonCallback() {
                                @Override
                                public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                                    processOrderCash();
                                }
                            })
                            .show();
                }
                break;
        }
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
    public void processOrderCash()
    {
        String strAddress = editCheckoutAddress.getText().toString();
        if (chkDifferent.isChecked())
        {
            strAddress = editAddress.getText().toString();
            if (!chkBox.isChecked() && strAddress.equals(""))
            {
                Toast.makeText(this.getActivity(), Utils.getResourceString(this.getActivity(),R.string.message_empty_address),Toast.LENGTH_SHORT).show();
                return;
            }
        }
        else
        {
            if (!chkBox.isChecked() && strAddress.equals(""))
            {
                Toast.makeText(this.getActivity(), Utils.getResourceString(this.getActivity(),R.string.message_empty_address),Toast.LENGTH_SHORT).show();
                return;
            }
        }
        OrderModel oModel = new OrderModel();
        oModel.mAddress = strAddress;
        oModel.mPayment = String.valueOf(0);
        oModel.mItemBuy = Globals.lstCarts;
        oModel.mPrice = String.valueOf(totalPrice);
        oModel.isShareLocation = chkBox.isChecked();
        progressDialog.show();
        ServiceManager.serviceOrderMake(this,oModel);
    }
    public void processOrderBank()
    {
//        PayPalPayment thingToBuy = getThingToBuy(PayPalPayment.PAYMENT_INTENT_SALE,String.valueOf(totalPrice));
//        Intent intent = new Intent(this.getContext(), PaymentActivity.class);
//        intent.putExtra(PayPalService.EXTRA_PAYPAL_CONFIGURATION, config);
//
//        intent.putExtra(PaymentActivity.EXTRA_PAYMENT, thingToBuy);
//
//        startActivityForResult(intent, REQUEST_CODE_PAYMENT);
    }
//    private PayPalPayment getThingToBuy(String paymentIntent,String price) {
//        return new PayPalPayment(new BigDecimal(price), "USD", "Order from Maqiti",
//                paymentIntent);
//    }
    @Override
    public void onResponse(int code) {
        progressDialog.dismiss();
        switch (code)
        {
            case 200:
                Globals.lstCarts.clear();
                Toast.makeText(this.getActivity(),Utils.getResourceString(this.getActivity(),R.string.message_success_order),Toast.LENGTH_SHORT).show();
                Globals.e_mode = Enums.MODE.ORDERS;
                ((HomeActivity)getActivity()).updateCartCount();
                ((HomeActivity)getActivity()).setFragment();
                break;
            case 400:
                break;
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
