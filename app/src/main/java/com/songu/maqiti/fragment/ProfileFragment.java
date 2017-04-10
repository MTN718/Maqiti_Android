package com.songu.maqiti.fragment;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
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
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.songu.maqiti.R;
import com.songu.maqiti.activity.HomeActivity;
import com.songu.maqiti.doc.Config;
import com.songu.maqiti.doc.Enums;
import com.songu.maqiti.doc.Globals;
import com.songu.maqiti.models.UserModel;
import com.songu.maqiti.service.IServiceResult;
import com.songu.maqiti.service.ServiceManager;
import com.songu.maqiti.utils.Utils;
import com.songu.maqiti.view.OvalImageView;
import com.squareup.picasso.Picasso;

import java.io.File;

/**
 * Created by Administrator on 2/21/2017.
 */
public class ProfileFragment extends Fragment implements View.OnClickListener,IServiceResult{


    public View mView;
    public OvalImageView imgPhoto;
    public EditText editUser;
    public EditText editEmail;
    public EditText editPhone;
    public EditText editAddress;
    public Button btnUpdate;
    public Button btnCancel;
    public File photoFile = null;
    public MaterialDialog progressDialog;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        try {
            mView = inflater.inflate(R.layout.fragment_profile, container, false);
        } catch (InflateException e) {

        }
        initView();
        setData();
        return mView;
    }
    public void initView()
    {
        imgPhoto = (OvalImageView) mView.findViewById(R.id.imgProfilePhoto);
        editEmail = (EditText) mView.findViewById(R.id.editProfileEmail);
        editPhone = (EditText) mView.findViewById(R.id.editProfilePhone);
        editAddress = (EditText) mView.findViewById(R.id.editProfileAddress);
        editUser = (EditText) mView.findViewById(R.id.editProfileUser);
        btnCancel = (Button) mView.findViewById(R.id.btnProfileCancel);
        btnUpdate = (Button) mView.findViewById(R.id.btnProfileUpdate);

        btnCancel.setOnClickListener(this);
        btnUpdate.setOnClickListener(this);
        imgPhoto.setOnClickListener(this);

        progressDialog = new MaterialDialog.Builder(this.getActivity())
                .title(Utils.getResourceString(this.getActivity(),R.string.message_process))
                .content(Utils.getResourceString(this.getActivity(),R.string.message_update_profile))
                .progress(true, 0).build();
        progressDialog.setCancelable(false);

    }

    public void setData()
    {
        editUser.setText(Globals.mAccount.mUser);
        editEmail.setText(Globals.mAccount.mEmail);
        editPhone.setText(Globals.mAccount.mPhone);
        editAddress.setText(Globals.mAccount.mAddress);

        if (Globals.mAccount.mPhoto != null && !Globals.mAccount.mPhoto.equals("")) {
            Picasso.with(this.getContext())
                    .load(Config.mImageBaseUrl + Globals.mAccount.mPhoto)
                    .resize(150,150)
                    .placeholder(R.drawable.accounticon)
                    .into(imgPhoto);
        }
    }
    public void processUpdateProfile()
    {
        String strUser = editUser.getText().toString();
        String strPhone = editPhone.getText().toString();
        String strAddress = editAddress.getText().toString();
        if (strUser.equals(""))
        {
            Toast.makeText(this.getActivity(), Utils.getResourceString(this.getActivity(),R.string.message_empty_user),Toast.LENGTH_SHORT).show();
            return;
        }
        UserModel mModel = new UserModel();
        mModel.mNo = Globals.mAccount.mNo;
        mModel.mUser = strUser;
        mModel.mPhone = strPhone;
        mModel.mAddress = strAddress;
        mModel.mPhotoFile = photoFile;
        ServiceManager.serviceUpdateProfile(this,mModel);
    }
    @Override
    public void onClick(View v) {
        switch (v.getId())
        {
            case R.id.btnProfileCancel:
                Globals.e_mode = Enums.MODE.HOME;
                ((HomeActivity)getActivity()).setFragment();
                break;
            case R.id.btnProfileUpdate:
                progressDialog.show();
                processUpdateProfile();
                break;
            case R.id.imgProfilePhoto:
                new MaterialDialog.Builder(this.getActivity())
                        .content(Utils.getResourceString(this.getActivity(),R.string.profile_take_photo))
                        .positiveText(Utils.getResourceString(this.getActivity(),R.string.profile_take_photo_camera))
                        .negativeText(Utils.getResourceString(this.getActivity(),R.string.profile_take_photo_library))
                        .neutralText(Utils.getResourceString(this.getActivity(),R.string.profile_cancel))
                        .onPositive(new MaterialDialog.SingleButtonCallback() {
                            @Override
                            public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                                photoFile = new File(Environment.getExternalStorageDirectory(),
                                        Utils.getPhotoFileName());
                                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                                intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(photoFile));
                                startActivityForResult(intent, 2);
                            }
                        })
                        .onNeutral(new MaterialDialog.SingleButtonCallback() {
                            @Override
                            public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                                // TODO
                                dialog.dismiss();
                            }
                        })
                        .onNegative(new MaterialDialog.SingleButtonCallback() {
                            @Override
                            public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                                Intent intent = new Intent(
                                        Intent.ACTION_PICK,
                                        android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                                intent.setType("image/*");
                                startActivityForResult(
                                        Intent.createChooser(intent, "Select File"),3);
                            }
                        })
                        .show();
                break;
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch(requestCode)
        {
            case 2:
                if(photoFile != null && photoFile.exists()){

                    Bitmap myBitmap = BitmapFactory.decodeFile(photoFile.getAbsolutePath());
                    imgPhoto.setImageBitmap(myBitmap);
                }
                break;
            case 3:
                if (data != null)
                {
                    Uri m_uri = data.getData();
                    photoFile = new File(Utils.getRealPathFromURI(this.getActivity(),m_uri));
                    if(photoFile != null && photoFile.exists()){

                        Bitmap myBitmap = BitmapFactory.decodeFile(photoFile.getAbsolutePath());
                        imgPhoto.setImageBitmap(myBitmap);
                    }
                }
                break;
        }
    }


    @Override
    public void onResponse(int code) {
        progressDialog.dismiss();
        switch (code)
        {
            case 200:
                new MaterialDialog.Builder(this.getActivity())
                        .title(Utils.getResourceString(this.getActivity(),R.string.message_success))
                        .content(Utils.getResourceString(this.getActivity(),R.string.message_updatesuccess))
                        .show();
                ((HomeActivity)getActivity()).updateData();
                break;
            case 400:
                new MaterialDialog.Builder(this.getActivity())
                        .title(Utils.getResourceString(this.getActivity(),R.string.message_error))
                        .content(Utils.getResourceString(this.getActivity(),R.string.message_updatefail))
                        .show();
                break;
        }
    }
}
