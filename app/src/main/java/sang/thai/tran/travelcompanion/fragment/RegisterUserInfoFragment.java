package sang.thai.tran.travelcompanion.fragment;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.FileProvider;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.countrypicker.CountryPicker;
import com.countrypicker.CountryPickerListener;
import com.vansuita.pickimage.bean.PickResult;
import com.vansuita.pickimage.bundle.PickSetup;
import com.vansuita.pickimage.dialog.PickImageDialog;
import com.vansuita.pickimage.listeners.IPickCancel;
import com.vansuita.pickimage.listeners.IPickClick;
import com.vansuita.pickimage.listeners.IPickResult;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import de.hdodenhof.circleimageview.CircleImageView;
import sang.thai.tran.travelcompanion.BuildConfig;
import sang.thai.tran.travelcompanion.activity.LoginActivity;
import sang.thai.tran.travelcompanion.R;
import sang.thai.tran.travelcompanion.activity.MainActivity;
import sang.thai.tran.travelcompanion.model.UserInfo;
import sang.thai.tran.travelcompanion.utils.ApplicationSingleton;
import sang.thai.tran.travelcompanion.utils.DialogUtils;
import sang.thai.tran.travelcompanion.view.EditTextViewLayout;

import static sang.thai.tran.travelcompanion.activity.MainActivity.UPDATE_INFO;

public class RegisterUserInfoFragment extends BaseFragment {

    @BindView(R.id.email_sign_in_button)
    Button email_sign_in_button;

    @BindView(R.id.et_full_name)
    EditTextViewLayout et_full_name;

    @BindView(R.id.et_year_of_birth)
    EditTextViewLayout et_year_of_birth;

    @BindView(R.id.et_gender)
    EditTextViewLayout et_gender;

    @BindView(R.id.et_nationality)
    EditTextViewLayout et_nationality;

    @BindView(R.id.et_phone)
    EditTextViewLayout et_phone;

    @BindView(R.id.et_email)
    EditTextViewLayout et_email;

    @BindView(R.id.et_address)
    EditTextViewLayout et_address;

    @BindView(R.id.et_pass)
    EditTextViewLayout et_pass;

    @BindView(R.id.ll_parent)
    LinearLayout ll_parent;

    @BindView(R.id.rlAdminAvatar)
    CircleImageView rlAdminAvatar;


    public static RegisterUserInfoFragment newInstance(boolean update) {
        RegisterUserInfoFragment infoRegisterFragment = new RegisterUserInfoFragment();
        Bundle bundle = new Bundle();
        bundle.putBoolean(UPDATE_INFO, update);
        infoRegisterFragment.setArguments(bundle);
        return infoRegisterFragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_register_user_info, container, false);
        ButterKnife.bind(this, view);
        email_sign_in_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                executeRegister();
            }
        });
        updateData();
        et_nationality.getEditText().setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus)
                onCLickNationality();
            }
        });
        et_year_of_birth.getEditText().setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus)
                onCLickYears();
            }
        });
        return view;
    }

    @OnClick(R.id.et_year_of_birth)
    protected void onCLickYears() {
        MonthYearPickerDialog dialog = new MonthYearPickerDialog();
        dialog.show(getFragmentManager(), "dialog");
        dialog.setListener(new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                et_year_of_birth.setText(String.valueOf(year));
            }
        });
    }

    @OnClick(R.id.et_nationality)
    protected void onCLickNationality() {
        CountryPicker picker = CountryPicker.newInstance("Select Country");
        picker.show(getFragmentManager(), "COUNTRY_PICKER");
        picker.setListener(new CountryPickerListener() {

            @Override
            public void onSelectCountry(String name, String code, String nationality) {
                // Invoke your function here
                et_nationality.setText(nationality);
            }
        });
    }

    @OnClick(R.id.rlAdminAvatar)
    protected void choseGallery() {
        PickImageDialog.build(new PickSetup())
                .setOnClick(new IPickClick() {
                    @Override
                    public void onGalleryClick() {
                        //Create an Intent with action as ACTION_PICK
                        Intent intent=new Intent(Intent.ACTION_PICK);
                        // Sets the type as image/*. This ensures only components of type image are selected
                        intent.setType("image/*");
                        //We pass an extra array with the accepted mime types. This will ensure only components with these MIME types as targeted.
                        String[] mimeTypes = {"image/jpeg", "image/png"};
                        intent.putExtra(Intent.EXTRA_MIME_TYPES,mimeTypes);
                        // Launching the Intent
                        startActivityForResult(intent,2);
                        Toast.makeText(getActivity(), "Gallery Click!", Toast.LENGTH_LONG).show();
                    }

                    @Override
                    public void onCameraClick() {
                        try {
                            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                            intent.putExtra(MediaStore.EXTRA_OUTPUT, FileProvider.getUriForFile(getActivity(), BuildConfig.APPLICATION_ID + ".provider", createImageFile()));
                            startActivityForResult(intent, 1);
                        } catch (IOException ex) {
                            ex.printStackTrace();
                        }
                        Toast.makeText(getActivity(), "Camera Click!", Toast.LENGTH_LONG).show();
                    }
                })
                .setOnPickResult(new IPickResult() {
                    @Override
                    public void onPickResult(PickResult r) {

                    }
                })
                .setOnPickCancel(new IPickCancel() {
                    @Override
                    public void onCancelClick() {
                    }
                }).show(getFragmentManager());
    }

    private void updateData() {
        Bundle bundle = getArguments();
        if (bundle != null) {
            boolean update = bundle.getBoolean(UPDATE_INFO);
            if (update) {
                UserInfo userInfo = ApplicationSingleton.getInstance().getUserInfo();
                if (userInfo != null) {
                    et_full_name.setText(userInfo.getName());
                    et_year_of_birth.setText(userInfo.getYear_of_birth());
                    et_gender.setText(userInfo.getGender());
                    et_phone.setText(userInfo.getPhone());
                    et_email.setText(userInfo.getEmail());
                    et_address.setText(userInfo.getAddress());
                    et_nationality.setText(userInfo.getNationality());
                }
            }
        }
    }


    private void executeRegister() {
        if (ll_parent == null) {
            return;
        }
        int count = ll_parent.getChildCount();
        for (int i = 0; i < count; i++) {
            View v = ll_parent.getChildAt(i);
            if (v instanceof EditTextViewLayout) {
                if (TextUtils.isEmpty(((EditTextViewLayout) v).getText())) {
                    showWarningDialog();
                    return;
                }
            }
        }
//        if (!isEmailValid(et_email.getText())) {
//            showWarningDialog(R.string.label_email_invalid);
//            return;
//        }
//        if (!isPassValid(et_pass.getText())) {
//            showWarningDialog(R.string.error_invalid_password);
//            return;
//        }
//        if (!isPhoneValid(et_phone.getText())) {
//            showWarningDialog(R.string.label_phone_invalid);
//            return;
//        }
        ApplicationSingleton.getInstance().setUserInfo(createAccount());
        ((LoginActivity) getActivity()).replaceFragment(R.id.fl_content, new ButtonRegisterFragment(), false);
    }

    private UserInfo createAccount() {
        UserInfo userInfo = new UserInfo();
        userInfo.setAddress(et_address.getText());
        userInfo.setName(et_full_name.getText());
        userInfo.setNationality(et_nationality.getText());
        userInfo.setPhone(et_phone.getText());
        userInfo.setYear_of_birth(et_year_of_birth.getText());
        userInfo.setEmail(et_email.getText());
        userInfo.setGender(et_gender.getText());
        return userInfo;
    }

    private void showWarningDialog() {
        DialogUtils.showAlertDialog(getActivity(), getString(R.string.label_input_info), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        // Result code is RESULT_OK only if the user selects an Image
        if (resultCode == Activity.RESULT_OK)
            switch (requestCode){
                case 2:
                    //data.getData returns the content URI for the selected Image
                    Uri selectedImage = data.getData();
                    rlAdminAvatar.setImageURI(selectedImage);
                    break;
                case 1:
                    rlAdminAvatar.setImageURI(Uri.parse(cameraFilePath));
                    break;
            }

    }

    private String cameraFilePath;
    private File createImageFile() throws IOException {
        // Create an image file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
        //This is the directory in which the file will be created. This is the default location of Camera photos
        File storageDir = new File(Environment.getExternalStoragePublicDirectory(
                Environment.DIRECTORY_DCIM), "Camera");
        File image = File.createTempFile(
                imageFileName,  /* prefix */
                ".jpg",         /* suffix */
                storageDir      /* directory */
        );
        // Save a file: path for using again
        cameraFilePath = "file://" + image.getAbsolutePath();
        return image;
    }
}
