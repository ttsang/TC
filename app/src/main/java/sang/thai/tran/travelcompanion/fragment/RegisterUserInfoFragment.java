package sang.thai.tran.travelcompanion.fragment;

import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.LinearLayout;

import com.countrypicker.CountryPicker;
import com.countrypicker.CountryPickerListener;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import sang.thai.tran.travelcompanion.activity.LoginActivity;
import sang.thai.tran.travelcompanion.R;
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
//            showWarningDialog(R.string.label_pass_invalid);
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
}
