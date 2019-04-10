package sang.thai.tran.travelcompanion.fragment;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutCompat;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.nj.imagepicker.ImagePicker;
import com.nj.imagepicker.listener.ImageResultListener;
import com.nj.imagepicker.result.ImageResult;
import com.nj.imagepicker.utils.DialogConfiguration;

import butterknife.BindView;
import butterknife.OnClick;
import butterknife.Optional;
import sang.thai.tran.travelcompanion.R;
import sang.thai.tran.travelcompanion.activity.MainActivity;
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

    @BindView(R.id.iv_card_id)
    ImageView iv_card_id;

    @BindView(R.id.tv_card_id)
    TextView tv_card_id;

    @BindView(R.id.iv_flight_ticket_id)
    ImageView iv_flight_ticket_id;

    @BindView(R.id.tv_flight_ticket_id)
    TextView tv_flight_ticket_id;

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
        }
    }

    @Override
    protected int layoutId() {
        return R.layout.fragment_register_flight;
    }

    @OnClick(R.id.email_sign_in_button)
    protected void register() {
        ((MainActivity) getActivity()).finishRegister();
    }

    @OnClick(R.id.et_date)
    protected void openDepartureDate() {
        if (getActivity() == null) {
            return;
        }
        openDatePicker(getActivity(), et_date);
    }

    @Optional
    @OnClick(R.id.et_hour)
    protected void openDepartureTime() {
        if (getActivity() == null) {
            return;
        }
        openTimePicker(getActivity(), et_hour);
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

}
