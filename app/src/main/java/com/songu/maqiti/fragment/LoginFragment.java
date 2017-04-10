package com.songu.maqiti.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.InflateException;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.afollestad.materialdialogs.MaterialDialog;
import com.songu.maqiti.R;
import com.songu.maqiti.activity.HomeActivity;
import com.songu.maqiti.doc.Enums;
import com.songu.maqiti.doc.Globals;
import com.songu.maqiti.service.IServiceResult;
import com.songu.maqiti.service.ServiceManager;
import com.songu.maqiti.utils.Utils;

/**
 * Created by Administrator on 2/25/2017.
 */
public class LoginFragment extends Fragment implements View.OnClickListener,IServiceResult{

    public View mView;
    public EditText editUser;
    public EditText editPassword;
    public Button btnLogin;
    public Button btnRegister;
    public LinearLayout layoutFacebook;
    public TextView txtLoginForget;
    public MaterialDialog progressDialog;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        try {
            mView = inflater.inflate(R.layout.fragment_login, container, false);
        } catch (InflateException e) {

        }
        initView();
        return mView;
    }
    public void initView()
    {
        editUser = (EditText) mView.findViewById(R.id.editLoginUser);
        editPassword = (EditText) mView.findViewById(R.id.editLoginPassword);
        txtLoginForget = (TextView) mView.findViewById(R.id.txtLoginForget);
        btnLogin = (Button) mView.findViewById(R.id.btnLoginLogin);
        btnRegister = (Button) mView.findViewById(R.id.btnLoginSignup);
        layoutFacebook = (LinearLayout) mView.findViewById(R.id.layoutLoginFacebook);


        btnLogin.setOnClickListener(this);
        txtLoginForget.setOnClickListener(this);
        btnRegister.setOnClickListener(this);
        layoutFacebook.setOnClickListener(this);

        progressDialog = new MaterialDialog.Builder(this.getActivity())
                .title(Utils.getResourceString(this.getActivity(),R.string.message_process))
                .content(Utils.getResourceString(this.getActivity(),R.string.message_wait))
                .progress(true, 0).build();
        progressDialog.setCancelable(true);
    }

    public void processSignIn()
    {
        String strUser = editUser.getText().toString();
        String strPassword = editPassword.getText().toString();
        if (strUser.equals(""))
        {
            Toast.makeText(this.getActivity(), Utils.getResourceString(this.getActivity(),R.string.message_empty_user),Toast.LENGTH_SHORT).show();
            return;
        }
        if (strPassword.equals(""))
        {
            Toast.makeText(this.getActivity(), Utils.getResourceString(this.getActivity(),R.string.message_empty_password),Toast.LENGTH_SHORT).show();
            return;
        }
        ServiceManager.serviceLogin(this,strUser,strPassword);
    }
    @Override
    public void onClick(View v) {
        switch (v.getId())
        {
            case R.id.btnLoginLogin:
                processSignIn();
                break;
            case R.id.btnLoginSignup:
                Globals.e_mode = Enums.MODE.REGISTER;
                ((HomeActivity)getActivity()).setFragment();
                break;
            case R.id.layoutLoginFacebook:
                progressDialog.show();
                ((HomeActivity)getActivity()).startFacebookLogin();
                break;
            case R.id.txtLoginForget:
                Globals.e_mode = Enums.MODE.FORGETPASSWORD;
                ((HomeActivity)getActivity()).setFragment();
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
                    .content(Utils.getResourceString(this.getActivity(),R.string.message_signinfail))
                    .show();
        }
    }
}
