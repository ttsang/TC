package sang.thai.tran.travelcompanion.fragment;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.nj.imagepicker.ImagePicker;
import com.nj.imagepicker.listener.ImageResultListener;
import com.nj.imagepicker.result.ImageResult;
import com.nj.imagepicker.utils.DialogConfiguration;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
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

    @BindView(R.id.iv_card_id)
    ImageView iv_card_id;

    @BindView(R.id.tv_card_id)
    TextView tv_card_id;

    @BindView(R.id.iv_flight_ticket_id)
    ImageView iv_flight_ticket_id;

    @BindView(R.id.tv_flight_ticket_id)
    TextView tv_flight_ticket_id;

    @BindView(R.id.iv_driving_licence)
    ImageView iv_driving_licence;

    @BindView(R.id.tv_driving_licence)
    TextView tv_driving_licence;

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


    @OnClick(R.id.fl_card_id)
    protected void uploadCardId() {
        if (getActivity() == null) {
            return;
        }
        DialogConfiguration configuration = new DialogConfiguration()
                .setTitle("Choose Options")
                .setOptionOrientation(LinearLayoutCompat.VERTICAL)
                .setBackgroundColor(Color.WHITE)
                .setNegativeText("No")
                .setNegativeTextColor(Color.RED)
                .setTitleTextColor(Color.BLUE);

        ImagePicker.build(configuration, new ImageResultListener() {
            @Override
            public void onImageResult(ImageResult imageResult) {
                tv_card_id.setVisibility(View.GONE);
                iv_card_id.setImageBitmap(imageResult.getBitmap());
            }
        }).show(getFragmentManager());
    }

    @OnClick(R.id.fl_flight_ticket_id)
    protected void uploadFlightTicket() {
        if (getActivity() == null) {
            return;
        }
        DialogConfiguration configuration = new DialogConfiguration()
                .setTitle("Choose Options")
                .setOptionOrientation(LinearLayoutCompat.VERTICAL)
                .setBackgroundColor(Color.WHITE)
                .setNegativeText("No")
                .setNegativeTextColor(Color.RED)
                .setTitleTextColor(Color.BLUE);

        ImagePicker.build(configuration, new ImageResultListener() {
            @Override
            public void onImageResult(ImageResult imageResult) {
                iv_flight_ticket_id.setImageBitmap(imageResult.getBitmap());
                tv_flight_ticket_id.setVisibility(View.GONE);
            }
        }).show(getFragmentManager());
    }

    @OnClick(R.id.fl_driving_licence)
    protected void uploadDrivingLicence() {
        if (getActivity() == null) {
            return;
        }
        DialogConfiguration configuration = new DialogConfiguration()
                .setTitle("Choose Options")
                .setOptionOrientation(LinearLayoutCompat.VERTICAL)
                .setBackgroundColor(Color.WHITE)
                .setNegativeText("No")
                .setNegativeTextColor(Color.RED)
                .setTitleTextColor(Color.BLUE);

        ImagePicker.build(configuration, new ImageResultListener() {
            @Override
            public void onImageResult(ImageResult imageResult) {
                iv_driving_licence.setImageBitmap(imageResult.getBitmap());
                tv_driving_licence.setVisibility(View.GONE);
            }
        }).show(getFragmentManager());
    }
}
