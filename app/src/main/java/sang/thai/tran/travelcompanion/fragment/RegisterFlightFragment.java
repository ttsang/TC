package sang.thai.tran.travelcompanion.fragment;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import sang.thai.tran.travelcompanion.R;
import sang.thai.tran.travelcompanion.activity.LoginActivity;
import sang.thai.tran.travelcompanion.adapter.RVAdapterChoiceMulti;
import sang.thai.tran.travelcompanion.model.ItemOptionModel;
import sang.thai.tran.travelcompanion.model.UserInfo;
import sang.thai.tran.travelcompanion.utils.ApplicationSingleton;
import sang.thai.tran.travelcompanion.utils.DialogUtils;
import sang.thai.tran.travelcompanion.view.EditTextViewLayout;

import static sang.thai.tran.travelcompanion.activity.MainActivity.NEED_SUPPORT;
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

    @Nullable
    @BindView(R.id.rv_service_pkg)
    RecyclerView rv_service_pkg;

    private List<ItemOptionModel> itemOptionModelList = new ArrayList<>();

    public static RegisterFlightFragment newInstance(boolean update) {
        RegisterFlightFragment infoRegisterFragment = new RegisterFlightFragment();
        Bundle bundle = new Bundle();
        bundle.putBoolean(NEED_SUPPORT, update);
        infoRegisterFragment.setArguments(bundle);
        return infoRegisterFragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        int layout = getArguments() != null && getArguments().getBoolean(NEED_SUPPORT) ? R.layout.fragment_register_flight_need: R.layout.fragment_register_flight;
        View view = inflater.inflate(layout, container, false);
        ButterKnife.bind(this, view);
        email_sign_in_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            }
        });
        if (rv_service_pkg != null) {
            RVAdapterChoiceMulti rvAdapterChoiceMulti = new RVAdapterChoiceMulti(getActivity());
            rv_service_pkg.setAdapter(rvAdapterChoiceMulti);
//        rv_service_pkg.setItemInfoList
            for (String string : getResources().getStringArray(R.array.service_pkg_list)) {
                ItemOptionModel itemOptionModel = new ItemOptionModel();
                itemOptionModel.setChecked(false);
                itemOptionModel.setService(string);
                itemOptionModelList.add(itemOptionModel);
            }
            rvAdapterChoiceMulti.setItemInfoList(itemOptionModelList);
            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
            rv_service_pkg.setLayoutManager(linearLayoutManager);
        }
        return view;
    }

}
