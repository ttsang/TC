package sang.thai.tran.travelcompanion.fragment;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import sang.thai.tran.travelcompanion.R;
import sang.thai.tran.travelcompanion.view.EditTextViewLayout;

import static sang.thai.tran.travelcompanion.utils.AppUtils.listToString;
import static sang.thai.tran.travelcompanion.utils.DialogUtils.onCreateOptionDialog;

public class RegisterFlightNeedFragment extends RegisterFlightFragment {

    @BindView(R.id.et_flight_number)
    EditTextViewLayout et_flight_number;

    @BindView(R.id.et_airline)
    EditTextViewLayout et_airline;

    @BindView(R.id.et_airport_departure)
    EditTextViewLayout et_airport_departure;

    @BindView(R.id.et_arrival_airport)
    EditTextViewLayout et_arrival_airport;

//    @Nullable
//    @BindView(R.id.rv_service_pkg)
//    RecyclerView rv_service_pkg;

    @BindView(R.id.tv_register_service)
    TextView tv_register_service;

    @BindView(R.id.tv_register_service_more)
    TextView tv_register_service_more;

    @BindView(R.id.email_sign_in_button)
    Button email_sign_in_button;

    public static RegisterFlightNeedFragment newInstance() {
        return new RegisterFlightNeedFragment();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        tv_register_service_more.requestFocus();
        tv_register_service_more.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    registerServiceMore();
                }
            }
        });

        tv_register_service.requestFocus();
        tv_register_service.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    registerService();
                }
            }
        });
    }

    @Override
    protected int layoutId() {
        return R.layout.fragment_register_flight_need;
    }

    @OnClick(R.id.tv_register_service)
    protected void registerService() {
        if (getActivity() == null) {
            return;
        }
        showOptionDialog(tv_register_service
                , getString(R.string.label_register_service_package)
                , getActivity().getResources().getTextArray(R.array.service_pkg));
    }

    @OnClick(R.id.tv_register_service_more)
    protected void registerServiceMore() {
        if (getActivity() == null) {
            return;
        }
        showOptionDialog(tv_register_service_more
                , getString(R.string.label_register_service_package_additional)
                , getActivity().getResources().getTextArray(getServicePkgMoreId()));
    }

    protected int getServicePkgMoreId() {
        return R.array.service_pkg_more;
    }


}
