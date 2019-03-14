package sang.thai.tran.travelcompanion.fragment;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;

import butterknife.BindView;
import butterknife.ButterKnife;
import sang.thai.tran.travelcompanion.R;
import sang.thai.tran.travelcompanion.activity.LoginActivity;
import sang.thai.tran.travelcompanion.model.UserInfo;
import sang.thai.tran.travelcompanion.utils.ApplicationSingleton;
import sang.thai.tran.travelcompanion.utils.DialogUtils;
import sang.thai.tran.travelcompanion.view.EditTextViewLayout;

import static sang.thai.tran.travelcompanion.activity.MainActivity.UPDATE_INFO;

public class RegisterFlightFragment extends BaseFragment {

    @BindView(R.id.email_sign_in_button)
    Button email_sign_in_button;

    @BindView(R.id.et_date)
    EditTextViewLayout et_date;

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
        bundle.putBoolean(UPDATE_INFO, update);
        infoRegisterFragment.setArguments(bundle);
        return infoRegisterFragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_register_flight, container, false);
        ButterKnife.bind(this, view);
        email_sign_in_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            }
        });
        return view;
    }

}
