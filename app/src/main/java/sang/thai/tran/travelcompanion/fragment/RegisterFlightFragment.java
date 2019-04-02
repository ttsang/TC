package sang.thai.tran.travelcompanion.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;

import butterknife.BindView;
import butterknife.OnClick;
import sang.thai.tran.travelcompanion.R;
import sang.thai.tran.travelcompanion.view.EditTextViewLayout;

import static sang.thai.tran.travelcompanion.activity.MainActivity.NEED_SUPPORT;
import static sang.thai.tran.travelcompanion.utils.AppUtils.openDatePicker;
import static sang.thai.tran.travelcompanion.utils.AppUtils.openTimePicker;

public class RegisterFlightFragment extends BaseFragment {

    @BindView(R.id.email_sign_in_button)
    Button email_sign_in_button;

    @BindView(R.id.et_date)
    EditTextViewLayout et_date;

    @Nullable
    @BindView(R.id.et_hour)
    EditTextViewLayout et_hour;

    @BindView(R.id.et_flight_number)
    EditTextViewLayout et_flight_number;

    @BindView(R.id.et_airline)
    EditTextViewLayout et_airline;

    @BindView(R.id.et_airport_departure)
    EditTextViewLayout et_airport_departure;

    @BindView(R.id.et_arrival_airport)
    EditTextViewLayout et_arrival_airport;

    public static RegisterFlightFragment newInstance(boolean update) {
        RegisterFlightFragment infoRegisterFragment = new RegisterFlightFragment();
        Bundle bundle = new Bundle();
        bundle.putBoolean(NEED_SUPPORT, update);
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

        if (et_hour != null) {
            et_hour.getEditText().setOnFocusChangeListener(new View.OnFocusChangeListener() {
                @Override
                public void onFocusChange(View v, boolean hasFocus) {
                    if (hasFocus) {
                        openDepartureTime();
                    }
                }
            });
            et_hour.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    openDepartureTime();
                }
            });
        }
    }

    @Override
    protected int layoutId() {
        return R.layout.fragment_register_flight;
    }

    @OnClick(R.id.email_sign_in_button)
    protected void register() {
    }

    @OnClick(R.id.et_date)
    protected void openDepartureDate() {
        if (getActivity() == null) {
            return;
        }
        openDatePicker(getActivity(), et_date);
    }

//    @OnClick(R.id.et_hour)
    protected void openDepartureTime() {
        if (getActivity() == null) {
            return;
        }
        openTimePicker(getActivity(), et_hour);
    }

}
