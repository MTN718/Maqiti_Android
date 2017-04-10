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
 * Created by Administrator on 3/1/2017.
 */
public class ForgetFragment extends Fragment implements View.OnClickListener,IServiceResult {

    public View mView;
    public EditText editEmail;
    public EditText editCode;
    public EditText editPassword;
    public Button btnSendCode;
    public Button btnCheckCode;
    public Button btnReset;
    public LinearLayout layoutPassword;
    public LinearLayout layoutCode;
    public MaterialDialog progressDialog;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        try {
            mView = inflater.inflate(R.layout.fragment_forget, container, false);
        } catch (InflateException e) {

        }
        initView();
        return mView;
    }

    public void initView() {
        editEmail = (EditText) mView.findViewById(R.id.editForgetEmail);
        editPassword = (EditText) mView.findViewById(R.id.editForgetNewPassword);
        editCode = (EditText) mView.findViewById(R.id.editForgetCode);
        btnSendCode = (Button) mView.findViewById(R.id.btnForgetSendCode);
        btnCheckCode = (Button) mView.findViewById(R.id.btnForgetCheckCode);
        btnReset = (Button) mView.findViewById(R.id.btnForgetReset);
        layoutPassword = (LinearLayout) mView.findViewById(R.id.layoutForgetPassword);
        layoutCode = (LinearLayout) mView.findViewById(R.id.layoutForgetCode);

        layoutPassword.setVisibility(View.GONE);
        layoutCode.setVisibility(View.GONE);

        //editUser.setText("user2@hotmail.com");
        //editPassword.setText("qqqqqq");

        btnSendCode.setOnClickListener(this);
        btnReset.setOnClickListener(this);
        btnCheckCode.setOnClickListener(this);

        progressDialog = new MaterialDialog.Builder(this.getActivity())
                .title(Utils.getResourceString(this.getActivity(),R.string.message_process))
                .content(Utils.getResourceString(this.getActivity(),R.string.message_resetting_password))
                .progress(true, 0).build();
        progressDialog.setCancelable(false);
    }

    @Override
    public void onResponse(int code) {
        switch (code)
        {
            case 200:
                Toast.makeText(this.getActivity(), Utils.getResourceString(this.getActivity(),R.string.message_forget_sent_code),Toast.LENGTH_SHORT).show();
                Toast.makeText(this.getActivity(), Globals.g_verifyCode,Toast.LENGTH_SHORT).show();
                layoutCode.setVisibility(View.VISIBLE);
                break;
            case 201:
                progressDialog.dismiss();
                Toast.makeText(this.getActivity(), Utils.getResourceString(this.getActivity(),R.string.message_forget_reset),Toast.LENGTH_SHORT).show();
                Globals.e_mode = Enums.MODE.LOGIN;
                ((HomeActivity)getActivity()).setFragment();
                break;
            case 401:
                progressDialog.dismiss();
                Toast.makeText(this.getActivity(), Utils.getResourceString(this.getActivity(),R.string.message_forget_reset_fail),Toast.LENGTH_SHORT).show();
                break;
            case 400:
                Toast.makeText(this.getActivity(), Utils.getResourceString(this.getActivity(),R.string.message_forget_wrong_email),Toast.LENGTH_SHORT).show();
                break;
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId())
        {
            case R.id.btnForgetCheckCode:
                String code = editCode.getText().toString();
                if (!code.equals("") && code.equals(Globals.g_verifyCode))
                {
                    layoutPassword.setVisibility(View.VISIBLE);
                }
                else layoutPassword.setVisibility(View.GONE);
                break;
            case R.id.btnForgetSendCode:
                String email = editEmail.getText().toString();
                if (!email.equals(""))
                    ServiceManager.serviceSendCode(this,email);
                else
                    Toast.makeText(this.getActivity(), Utils.getResourceString(this.getActivity(),R.string.message_forget_empty_email),Toast.LENGTH_SHORT).show();
                break;
            case R.id.btnForgetReset:
                email = editEmail.getText().toString();
                String password = editPassword.getText().toString();
                progressDialog.show();
                ServiceManager.serviceUpdatePassword(this,email,password);
                break;
        }
    }
}