package sang.thai.tran.travelcompanion.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Optional;
import sang.thai.tran.travelcompanion.R;
import sang.thai.tran.travelcompanion.adapter.RVAdapterChoiceMulti;
import sang.thai.tran.travelcompanion.model.ItemOptionModel;
import sang.thai.tran.travelcompanion.view.EditTextViewLayout;

import static sang.thai.tran.travelcompanion.activity.MainActivity.NEED_SUPPORT;
import static sang.thai.tran.travelcompanion.activity.MainActivity.UPDATE_INFO;

public class RegisterGuideFragment extends BaseFragment {

    @BindView(R.id.email_sign_in_button)
    Button email_sign_in_button;

    @Nullable
    @BindView(R.id.et_guide_type)
    EditTextViewLayout et_guide_type;

    @Nullable
    @BindView(R.id.et_city)
    EditTextViewLayout et_city;

    @Nullable
    @BindView(R.id.et_driving_licence)
    EditTextViewLayout et_driving_licence;

    @Nullable
    @BindView(R.id.et_country)
    EditTextViewLayout et_country;

    @Nullable
    @BindView(R.id.et_know_destination)
    EditTextViewLayout et_know_destination;

    @Nullable
    @BindView(R.id.et_fluent)
    EditTextViewLayout et_fluent;

    @Nullable
    @BindView(R.id.et_free_time)
    EditTextViewLayout et_free_time;

    @Nullable
    @BindView(R.id.et_register_place)
    EditTextViewLayout et_register_place;

    public static RegisterGuideFragment newInstance(boolean update) {
        RegisterGuideFragment infoRegisterFragment = new RegisterGuideFragment();
        Bundle bundle = new Bundle();
        bundle.putBoolean(NEED_SUPPORT, update);
        infoRegisterFragment.setArguments(bundle);
        return infoRegisterFragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        int layout = getArguments() != null && getArguments().getBoolean(NEED_SUPPORT) ? R.layout.fragment_register_guide_need : R.layout.fragment_register_guide;
        View view = inflater.inflate(layout, container, false);
        ButterKnife.bind(this, view);
        email_sign_in_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            }
        });
        return view;
    }


}