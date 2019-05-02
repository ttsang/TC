package sang.thai.tran.travelcompanion.fragment;

import android.os.Bundle;
import androidx.annotation.Nullable;

import android.widget.Button;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import sang.thai.tran.travelcompanion.R;
import sang.thai.tran.travelcompanion.model.ItemOptionModel;
import sang.thai.tran.travelcompanion.view.EditTextViewLayout;

public class RegisterGuideNeedFragment extends RegisterFlightNeedFragment {

    @BindView(R.id.et_place)
    EditTextViewLayout et_place;

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

    @BindView(R.id.email_sign_in_button)
    Button email_sign_in_button;

    private List<ItemOptionModel> itemOptionModelList = new ArrayList<>();

    public static RegisterGuideNeedFragment newInstance(boolean update) {
        RegisterGuideNeedFragment infoRegisterFragment = new RegisterGuideNeedFragment();
        // Create a storage reference from our app
        return infoRegisterFragment;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
//        if (rv_service_pkg != null) {
//            RVAdapterChoiceMulti rvAdapterChoiceMulti = new RVAdapterChoiceMulti(getActivity());
//            rv_service_pkg.setAdapter(rvAdapterChoiceMulti);
////        rv_service_pkg.setItemInfoList
//            for (String string : getResources().getStringArray(R.array.service_pkg_list)) {
//                ItemOptionModel itemOptionModel = new ItemOptionModel();
//                itemOptionModel.setChecked(false);
//                itemOptionModel.setService(string);
//                itemOptionModelList.add(itemOptionModel);
//            }
//            rvAdapterChoiceMulti.setItemInfoList(itemOptionModelList);
//            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
//            rv_service_pkg.setLayoutManager(linearLayoutManager);
//            ViewCompat.setNestedScrollingEnabled(rv_service_pkg, false);
//        }
    }

    @Override
    protected int layoutId() {
        return R.layout.fragment_register_guide_need;
    }


    @Override
    protected int getServicePkgMoreId() {
        return R.array.service_pkg_list;
    }
}
