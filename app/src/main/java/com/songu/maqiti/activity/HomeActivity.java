package com.songu.maqiti.activity;

import android.Manifest;
import android.app.ActionBar;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.widget.DrawerLayout;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.afollestad.materialdialogs.MaterialDialog;
import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.Profile;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.songu.maqiti.R;
import com.songu.maqiti.doc.Config;
import com.songu.maqiti.doc.Enums;
import com.songu.maqiti.doc.Globals;
import com.songu.maqiti.fragment.BrowseFragment;
import com.songu.maqiti.fragment.CartsFragment;
import com.songu.maqiti.fragment.FilterFragment;
import com.songu.maqiti.fragment.ForgetFragment;
import com.songu.maqiti.fragment.HomeFragment;
import com.songu.maqiti.fragment.LoginFragment;
import com.songu.maqiti.fragment.OrderFragment;
import com.songu.maqiti.fragment.ProfileFragment;
import com.songu.maqiti.fragment.RegisterFragment;
import com.songu.maqiti.fragment.SearchResultFragment;
import com.songu.maqiti.fragment.FavouriteFragment;
import com.songu.maqiti.models.CategoryModel;
import com.songu.maqiti.models.SubCategoryModel;
import com.songu.maqiti.models.DepartModel;
import com.songu.maqiti.models.MenuHolder;
import com.songu.maqiti.models.UserModel;
import com.songu.maqiti.service.IServiceResult;
import com.songu.maqiti.service.ServiceManager;
import com.songu.maqiti.utils.Utils;
import com.songu.maqiti.view.ActionBarDrawerToggle;
import com.songu.maqiti.view.DrawerArrowDrawable;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;

/**
 * Created by Administrator on 2/20/2017.
 */
public class HomeActivity extends FragmentActivity implements View.OnClickListener,IServiceResult{

    public DrawerLayout mDrawerLayout;
    public RelativeLayout mNavView;
    private DrawerArrowDrawable drawerArrow;
    private ActionBarDrawerToggle mDrawerToggle;
    public Fragment currentFragment;
    public ImageView imgPhoto;
    public TextView txtName;

    public LinearLayout layoutHome;
    public LinearLayout layoutFavourite;
    public LinearLayout layoutOrders;
    public LinearLayout layoutCategory;
    public LinearLayout layoutSearchbar;
    public LinearLayout layoutCountry;
    public ImageView imgCart;
    public LinearLayout layoutAccount;
    public EditText editKeyword;
    public ImageView imgSearch;
    public ImageView imgFilter;

    public TextView txtLogout;
    public TextView txtCartCount;

    public LoginManager loginManager;
    private AccessToken mAccessToken;
    public Collection<String> permissions = Arrays.asList("public_profile","email");
    public HashMap<String,List<View>> hmMenus;

    private FacebookCallback<LoginResult> mCallBack = new FacebookCallback<LoginResult>() {
        @Override
        public void onSuccess(LoginResult loginResult) {
            mAccessToken = loginResult.getAccessToken();
            GraphRequest request = GraphRequest.newMeRequest(loginResult.getAccessToken(), new GraphRequest.GraphJSONObjectCallback() {

                @Override
                public void onCompleted(JSONObject object, GraphResponse response) {
                    try {
                        String email = object.getString("email");
                        String mId = object.getString("id");
                        String name = object.getString("first_name") + object.getString("last_name");
                        UserModel mModel = new UserModel();
                        mModel.mEmail = email;
                        mModel.mPassword = mId;
                        mModel.mUser = name;
                        ServiceManager.serviceFacebookLogin(HomeActivity.this,mModel);

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            });
            Bundle parameters = new Bundle();
            parameters.putString("fields", "id, first_name, last_name, email,gender, birthday, location"); // Parámetros que pedimos a facebook
            request.setParameters(parameters);
            request.executeAsync();
        }

        @Override
        public void onCancel() {
            String ss = "";
            int i = 1;
        }

        @Override
        public void onError(FacebookException error) {
            String ss = "";
            int i = 1;
        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        if (!FacebookSdk.isInitialized()) {
            FacebookSdk.sdkInitialize(getApplicationContext());
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            requestPermissions(new String[]{Manifest.permission.CAMERA,Manifest.permission.ACCESS_FINE_LOCATION},
                    100);
        }

        Globals.g_callbackManager = CallbackManager.Factory.create();
        loginManager    =   LoginManager.getInstance();

        loginManager.registerCallback(Globals.g_callbackManager,mCallBack);

        Globals.e_mode = Enums.MODE.HOME;
        initView();
        initActionBar();
        updateCartCount();
        setFragment();
    }

    public void startFacebookLogin()
    {
        loginManager.logInWithReadPermissions(this, permissions);
    }
    public void initView()
    {
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        mNavView = (RelativeLayout) findViewById(R.id.navdrawer);

        drawerArrow = new DrawerArrowDrawable(this)
        {
            @Override
            public boolean isLayoutRtl() {
                return false;
            }
        };
        mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout,
                drawerArrow, R.string.drawer_open,
                R.string.drawer_close) {

            public void onDrawerClosed(View view) {
                super.onDrawerClosed(view);
                invalidateOptionsMenu();
            }

            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
                invalidateOptionsMenu();
            }
        };
        mDrawerLayout.setDrawerListener(mDrawerToggle);
        mDrawerToggle.syncState();
        imgPhoto = (ImageView) this.findViewById(R.id.imgMainProfile);
        txtName  = (TextView) this.findViewById(R.id.txtMenuAccountName);
        txtLogout = (TextView) this.findViewById(R.id.txtMenuLogout);
        editKeyword = (EditText) this.findViewById(R.id.editHomeKeyword);
        imgSearch = (ImageView) this.findViewById(R.id.imgHomeSearch);
        imgFilter = (ImageView) this.findViewById(R.id.imgHomeFilter);
        layoutSearchbar = (LinearLayout) this.findViewById(R.id.layoutHomeSearchbar);

        layoutHome = (LinearLayout) this.findViewById(R.id.layoutMenuHome);
        layoutFavourite = (LinearLayout) this.findViewById(R.id.layoutMenuFavourite);
        layoutOrders = (LinearLayout) this.findViewById(R.id.layoutMenuOrders);
        layoutCategory = (LinearLayout) this.findViewById(R.id.layoutMenuCategory);
        layoutAccount = (LinearLayout) this.findViewById(R.id.layoutMenuAccount);
        layoutCountry = (LinearLayout) this.findViewById(R.id.layoutMenuCountry);

        layoutHome.setOnClickListener(this);
        layoutFavourite.setOnClickListener(this);
        layoutOrders.setOnClickListener(this);
        txtLogout.setOnClickListener(this);
        imgFilter.setOnClickListener(this);
        layoutCountry.setOnClickListener(this);

        updateData();
        imgPhoto.setOnClickListener(this);
        txtName.setOnClickListener(this);
        imgSearch.setOnClickListener(this);

    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        //getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        if (item.getItemId() == android.R.id.home) {
            if (mDrawerLayout.isDrawerOpen(mNavView)) {
                mDrawerLayout.closeDrawer(mNavView);
            } else {
                mDrawerLayout.openDrawer(mNavView);
            }
        }
        return super.onOptionsItemSelected(item);
    }
    public void setFragment()
    {
        switch(Globals.e_mode)
        {
            case HOME:
                layoutSearchbar.setVisibility(View.VISIBLE);
                imgFilter.setVisibility(View.GONE);
                currentFragment = new HomeFragment();
                getSupportFragmentManager().beginTransaction().replace(R.id.frameHome, currentFragment).commit();
                break;
            case PROFILE:
                layoutSearchbar.setVisibility(View.GONE);
                currentFragment = new ProfileFragment();
                getSupportFragmentManager().beginTransaction().replace(R.id.frameHome, currentFragment).commit();
                break;
            case BROWSE:
                layoutSearchbar.setVisibility(View.VISIBLE);
                imgFilter.setVisibility(View.VISIBLE);
                currentFragment = new BrowseFragment();
                getSupportFragmentManager().beginTransaction().replace(R.id.frameHome, currentFragment).commit();
                break;
            case FAVOURITE:
                layoutSearchbar.setVisibility(View.GONE);
                currentFragment = new FavouriteFragment();
                getSupportFragmentManager().beginTransaction().replace(R.id.frameHome, currentFragment).commit();
                break;
            case ORDERS:
                layoutSearchbar.setVisibility(View.GONE);
                currentFragment = new OrderFragment();
                getSupportFragmentManager().beginTransaction().replace(R.id.frameHome, currentFragment).commit();
                break;
            case SEARCHRESULT:
                layoutSearchbar.setVisibility(View.VISIBLE);
                if (Globals.currentCategory == null)
                    imgFilter.setVisibility(View.GONE);
                else
                    imgFilter.setVisibility(View.VISIBLE);
                currentFragment = new SearchResultFragment();
                getSupportFragmentManager().beginTransaction().replace(R.id.frameHome, currentFragment).commit();
                break;
            case LOGIN:
                layoutSearchbar.setVisibility(View.GONE);
                currentFragment = new LoginFragment();
                getSupportFragmentManager().beginTransaction().replace(R.id.frameHome, currentFragment).commit();
                break;
            case REGISTER:
                layoutSearchbar.setVisibility(View.GONE);
                currentFragment = new RegisterFragment();
                getSupportFragmentManager().beginTransaction().replace(R.id.frameHome, currentFragment).commit();
                break;
            case CARTS:
                layoutSearchbar.setVisibility(View.GONE);
                currentFragment = new CartsFragment();
                getSupportFragmentManager().beginTransaction().replace(R.id.frameHome, currentFragment).commit();
                break;
            case FORGETPASSWORD:
                layoutSearchbar.setVisibility(View.GONE);
                currentFragment = new ForgetFragment();
                getSupportFragmentManager().beginTransaction().replace(R.id.frameHome, currentFragment).commit();
                break;
            case FILTER:
                layoutSearchbar.setVisibility(View.GONE);
                currentFragment = new FilterFragment();
                getSupportFragmentManager().beginTransaction().replace(R.id.frameHome, currentFragment).commit();
                break;

        }
    }
    public void updateData() {
        if (Globals.mAccount != null) {
            layoutAccount.setVisibility(View.VISIBLE);
            layoutOrders.setVisibility(View.VISIBLE);
            layoutFavourite.setVisibility(View.VISIBLE);
            txtName.setText(Globals.mAccount.mUser);
            if (Globals.mAccount.mPhoto != null && !Globals.mAccount.mPhoto.equals("")) {
                Picasso.with(this)
                        .load(Config.mImageBaseUrl + Globals.mAccount.mPhoto)
                        .resize(150, 150)
                        .into(imgPhoto);
            }
            else imgPhoto.setImageDrawable(getResources().getDrawable(R.drawable.accounticon));

            txtLogout.setText(Utils.getResourceString(this,R.string.menu_logout));
        }
        else
        {
            layoutAccount.setVisibility(View.GONE);
            layoutOrders.setVisibility(View.GONE);
            layoutFavourite.setVisibility(View.GONE);
            txtLogout.setText(Utils.getResourceString(this,R.string.menu_login));
        }
    }
    public void setBackFragment()
    {
        switch (Globals.e_mode)
        {
            case HOME:
                finish();
                break;
            case PROFILE:
                Globals.e_mode = Enums.MODE.HOME;
                setFragment();
                break;
            case BROWSE:
                Globals.e_mode = Enums.MODE.HOME;
                setFragment();
                break;
            case FAVOURITE:
                Globals.e_mode = Enums.MODE.HOME;
                setFragment();
                break;
            case ORDERS:
                Globals.e_mode = Enums.MODE.HOME;
                setFragment();
                break;
            case LOGIN:
                Globals.e_mode = Enums.MODE.HOME;
                setFragment();
                break;
            case REGISTER:
                Globals.e_mode = Enums.MODE.LOGIN;
                setFragment();
                break;
            case CARTS:
                Globals.e_mode = Enums.MODE.HOME;
                setFragment();
                break;
            case SEARCHRESULT:
                Globals.e_mode = Enums.MODE.HOME;
                setFragment();
                break;
            case FORGETPASSWORD:
                Globals.e_mode = Enums.MODE.LOGIN;
                setFragment();
                break;
            case FILTER:
                Globals.e_mode = Enums.MODE.SEARCHRESULT;
                setFragment();
                break;
        }
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        updateCartCount();
        if (Globals.g_callbackManager != null) {
            Globals.g_callbackManager.onActivityResult(requestCode, resultCode, data);
            return;
        }
        if (Globals.e_mode == Enums.MODE.HOME)
        {
            ((HomeFragment) currentFragment).onActivityResult(requestCode, resultCode, data);
        }
        if (Globals.e_mode == Enums.MODE.PROFILE) {
            ((ProfileFragment) currentFragment).onActivityResult(requestCode, resultCode, data);
        }
    }
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            setBackFragment();
        }
        return false;
    }
    public void initActionBar()
    {
        try{
            ActionBar ab = getActionBar();
            ab.setDisplayHomeAsUpEnabled(true);
            ab.setHomeButtonEnabled(true);
            ab.setBackgroundDrawable(new ColorDrawable(0xffffffff));
            ab.setCustomView(R.layout.layout_actionbar);
            ab.setDisplayShowCustomEnabled(true);

            imgCart = (ImageView)ab.getCustomView().findViewById(R.id.imgMenuCart);
            txtCartCount = (TextView) ab.getCustomView().findViewById(R.id.txtMenuCountCart);
            imgCart.setOnClickListener(this);
        }catch(Exception e){}
    }
    public void updateCartCount()
    {
        txtCartCount.setText(String.valueOf(Globals.lstCarts.size()));
    }
    @Override
    public void onClick(View v) {
        switch (v.getId())
        {
            case R.id.imgMainProfile:
            case R.id.txtMenuAccount:
                Globals.e_mode = Enums.MODE.PROFILE;
                setFragment();
                break;
            case R.id.layoutMenuHome:
                Globals.e_mode = Enums.MODE.HOME;
                setFragment();
                break;
            case R.id.imgMenuCart:
                Globals.e_mode = Enums.MODE.CARTS;
                setFragment();
                break;
            case R.id.layoutMenuFavourite:
                Globals.e_mode = Enums.MODE.FAVOURITE;
                setFragment();
                break;
            case R.id.layoutMenuOrders:
                Globals.e_mode = Enums.MODE.ORDERS;
                setFragment();
                break;
            case R.id.layoutMenuCountry:
                Intent m = new Intent(this,CountrySelectActivity.class);
                this.startActivity(m);
                break;
            case R.id.txtMenuLogout:
                if (Globals.mAccount == null)
                {
                    Globals.e_mode = Enums.MODE.LOGIN;
                    setFragment();
                }
                else {
                    Globals.mAccount = null;
                    updateData();
                    Utils.clearPreference(this);
                }
                break;
            case R.id.imgHomeSearch:
                Globals.g_FilterModel.clear();
                Globals.e_mode = Enums.MODE.SEARCHRESULT;
                setFragment();
                break;
            case R.id.imgHomeFilter:
                Globals.g_FilterModel.clear();
                Globals.e_mode = Enums.MODE.FILTER;
                setFragment();
                break;
        }
        mDrawerLayout.closeDrawer(mNavView);
    }
    public void addMenuViews()
    {
        hmMenus = new HashMap<>();
        layoutCategory.removeAllViews();
        for (int i = 0;i < Globals.lstDeparts.size();i++) {
            addDepartView(Globals.lstDeparts.get(i));
            for (int j = 0; j < Globals.lstDeparts.get(i).lstCategory.size(); j++) {

                List<View> lstCategoryViews = new ArrayList<>();
                View vwCategory = addBategoryView(Globals.lstDeparts.get(i).lstCategory.get(j),j);
                layoutCategory.addView(vwCategory);
                for (int k = 0;k < Globals.lstDeparts.get(i).lstCategory.get(j).lstSubs.size();k++) {
                    View vwCategory1 = addCategoryView(Globals.lstDeparts.get(i).lstCategory.get(j).lstSubs.get(k));
                    vwCategory1.setVisibility(View.GONE);
                    layoutCategory.addView(vwCategory1);
                    lstCategoryViews.add(vwCategory1);
                }
                hmMenus.put(Globals.lstDeparts.get(i).lstCategory.get(j).mNo,lstCategoryViews);
            }
        }
    }
    public void addDepartView(final DepartModel mModel)
    {
        View localView;
        final MenuHolder localViewHolder = new MenuHolder();
        localView = LayoutInflater.from(this).inflate(R.layout.item_depart, null);
        localViewHolder.txtMenu = (TextView) localView.findViewById(R.id.txtMenuDepartment);
        //localViewHolder.txtExpand = (TextView) localView.findViewById(R.id.txtMenuDepartmentExpand);
        localView.setTag(localViewHolder);
        localViewHolder.txtMenu.setText(mModel.mName);
        /*localViewHolder.txtExpand.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (localViewHolder.txtExpand.getText().toString().equals("+"))
                {
                    localViewHolder.txtExpand.setText("-");
                    for (int i = 0;i < hmMenus.get(index).size();i++)
                    {
                        hmMenus.get(index).get(i).setVisibility(View.VISIBLE);
                    }
                }
                else {
                    localViewHolder.txtExpand.setText("+");
                    for (int i = 0;i < hmMenus.get(index).size();i++)
                    {
                        hmMenus.get(index).get(i).setVisibility(View.GONE);
                    }
                }
            }
        });*/
        localView.setTag(localViewHolder);
        layoutCategory.addView(localView);
    }
    public View addBategoryView(final CategoryModel mModel,final int index)
    {
        View localView;
        final MenuHolder localViewHolder = new MenuHolder();
        localView = LayoutInflater.from(this).inflate(R.layout.item_bategory, null);

        localViewHolder.txtMenu = (TextView) localView.findViewById(R.id.txtMenuBategory);
        localViewHolder.txtExpand = (TextView) localView.findViewById(R.id.txtMenuBategoryExpand);
        localView.setTag(localViewHolder);

        localViewHolder.txtMenu.setText(mModel.mName);

        localViewHolder.txtExpand.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (localViewHolder.txtExpand.getText().toString().equals("+"))
                {
                    localViewHolder.txtExpand.setText("-");
                    for (int i = 0;i < hmMenus.get(mModel.mNo).size();i++)
                    {
                        hmMenus.get(mModel.mNo).get(i).setVisibility(View.VISIBLE);
                    }
                }
                else {
                    localViewHolder.txtExpand.setText("+");
                    for (int i = 0;i < hmMenus.get(mModel.mNo).size();i++)
                    {
                        hmMenus.get(mModel.mNo).get(i).setVisibility(View.GONE);
                    }
                }
            }
        });

        localView.setTag(localViewHolder);
        return localView;
    }
    public View addCategoryView(final SubCategoryModel mModel)
    {
        View localView;
        MenuHolder localViewHolder = null;
        localView = LayoutInflater.from(this).inflate(R.layout.layout_menu, null);

        localViewHolder = new MenuHolder();
        localViewHolder.txtMenu = (TextView) localView.findViewById(R.id.txtMenuCategory);
        localView.setTag(localViewHolder);

        localViewHolder.txtMenu.setText(mModel.mName);
        localView.setTag(localViewHolder);
        localView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Globals.currentCategory = mModel;
                Globals.e_mode = Enums.MODE.BROWSE;
                Globals.lstItems.clear();
                Globals.lstSearchItems.clear();
                HomeActivity.this.setFragment();
                mDrawerLayout.closeDrawer(mNavView);
            }
        });
        return localView;
    }

    @Override
    public void onResponse(int code) {
        if (currentFragment instanceof LoginFragment)
        {
            ((LoginFragment)currentFragment).progressDialog.dismiss();
        }
        switch (code)
        {
            case 200:
            {
                Utils.savePreference(this);
                Globals.e_mode = Enums.MODE.HOME;
                setFragment();
                updateData();
            }
            break;
            case 400:
            {
                new MaterialDialog.Builder(this)
                        .title(Utils.getResourceString(this,R.string.message_error))
                        .content(Utils.getResourceString(this,R.string.message_signinfail))
                        .show();
            }
            break;
        }
    }
}
