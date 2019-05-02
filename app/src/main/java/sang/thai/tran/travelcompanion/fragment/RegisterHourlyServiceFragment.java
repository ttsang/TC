package sang.thai.tran.travelcompanion.fragment;

import android.content.Context;
import android.os.Bundle;
import androidx.annotation.Nullable;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.OnClick;
import sang.thai.tran.travelcompanion.R;
import sang.thai.tran.travelcompanion.activity.MainActivity;
import sang.thai.tran.travelcompanion.view.EditTextViewLayout;

import static sang.thai.tran.travelcompanion.utils.AppUtils.openDatePicker;
import static sang.thai.tran.travelcompanion.utils.AppUtils.openTimePicker;

public class RegisterHourlyServiceFragment extends BaseFragment {

    @BindView(R.id.tv_register_service)
    TextView tv_register_service;

    @BindView(R.id.tv_register_service_more)
    TextView tv_register_service_more;

    @BindView(R.id.et_date)
    EditTextViewLayout et_date;

    @BindView(R.id.et_from)
    EditTextViewLayout et_from;

    @BindView(R.id.et_to)
    EditTextViewLayout et_to;

    public static RegisterHourlyServiceFragment newInstance(boolean update) {
        RegisterHourlyServiceFragment infoRegisterFragment = new RegisterHourlyServiceFragment();
        Bundle bundle = new Bundle();
        bundle.putBoolean(MainActivity.NEED_SUPPORT, update);
        infoRegisterFragment.setArguments(bundle);
        return infoRegisterFragment;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        et_date.getEditText().setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    openDepartureDate();
                }
            }
        });

        et_from.getEditText().setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    openFromTime();
                }
            }
        });

        et_to.getEditText().setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    openToTime();
                }
            }
        });

        tv_register_service_more.requestFocus();
        tv_register_service_more.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    registerServiceMore();
                    tv_register_service_more.clearFocus();
                } else {
                    tv_register_service_more.clearFocus();
                }
            }
        });

        tv_register_service.requestFocus();
        tv_register_service.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    registerService();
                    tv_register_service.clearFocus();
                } else {
                    tv_register_service.clearFocus();
                }
            }
        });
    }

    @Override
    protected int layoutId() {
        return R.layout.fragment_register_hourly_service;
    }


    @OnClick(R.id.tv_register_service)
    protected void registerService() {
        if (getActivity() == null) {
            return;
        }
        showOptionDialog(tv_register_service
                , getString(R.string.label_for)
                , getActivity().getResources().getTextArray(R.array.register_for_list));

        InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(tv_register_service.getWindowToken(), 0);
    }

    @OnClick(R.id.tv_register_service_more)
    protected void registerServiceMore() {
        if (getActivity() == null) {
            return;
        }
        showOptionDialog(tv_register_service_more
                , getString(R.string.label_register_service_package_additional)
                , getActivity().getResources().getTextArray(R.array.hourly_service_pkg));

        InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(tv_register_service_more.getWindowToken(), 0);
    }

    @OnClick(R.id.et_date)
    protected void openDepartureDate() {
        if (getActivity() == null) {
            return;
        }
        openDatePicker(getActivity(), et_date);
    }

    @OnClick(R.id.et_from)
    protected void openFromTime() {
        if (getActivity() == null) {
            return;
        }
        openTimePicker(getActivity(), et_from);
    }

    @OnClick(R.id.et_to)
    protected void openToTime() {
        if (getActivity() == null) {
            return;
        }
        openTimePicker(getActivity(), et_to);
    }

    @OnClick(R.id.email_sign_in_button)
    protected void register() {
        if (getActivity() == null) {
            return;
        }
        ((MainActivity) getActivity()).finishRegister();
    }
}
