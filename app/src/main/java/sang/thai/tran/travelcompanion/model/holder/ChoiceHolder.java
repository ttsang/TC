package sang.thai.tran.travelcompanion.model.holder;

import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.RecyclerView;

import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import sang.thai.tran.travelcompanion.BuildConfig;
import sang.thai.tran.travelcompanion.R;
import sang.thai.tran.travelcompanion.TravelCompanionApplication;
import sang.thai.tran.travelcompanion.model.RegisterModel;
import sang.thai.tran.travelcompanion.utils.AppUtils;

public class ChoiceHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

//    @BindView(R.id.tv_time)
//    public TextView tv_time;

    @BindView(R.id.tv_time_value)
    public TextView tv_time_value;

    @BindView(R.id.tv_passenger_value)
    public TextView tv_passenger_value;

    @BindView(R.id.tv_from_place_value)
    public TextView tv_from_place_value;

    @BindView(R.id.tv_to_place_value)
    public TextView tv_to_place_value;

    @BindView(R.id.tv_note_value)
    public TextView tv_note_value;

    @BindView(R.id.tv_status_value)
    public TextView tv_status_value;

    public ChoiceHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
    }

    public void bindView(RegisterModel itemOptionModel, OnClickReceiver onClickReceiver) {
        tv_time_value.setText(itemOptionModel.getDepartureDateFrom());
        tv_from_place_value.setText(itemOptionModel.getPickupPoint());
        tv_to_place_value.setText(itemOptionModel.getVisitPlaces());
        tv_note_value.setText(itemOptionModel.getNote());
        String passenger_value = (itemOptionModel.getElderlyNumber() > 0) ? String.format(itemView.getContext().getString(R.string.label_elderly), itemOptionModel.getElderlyNumber()) + ", " : "";
        passenger_value += itemOptionModel.getMiddleAgeNumber() > 0 ? String.format(itemView.getContext().getString(R.string.label_middle_age), itemOptionModel.getMiddleAgeNumber()) + ", " : "";
        passenger_value += itemOptionModel.getChildrenNumber() > 0 ? String.format(itemView.getContext().getString(R.string.label_children), itemOptionModel.getChildrenNumber()) + ", " : "";
        passenger_value += itemOptionModel.getPregnantNumber() > 0 ? String.format(itemView.getContext().getString(R.string.label_pregnant), itemOptionModel.getPregnantNumber()) + ", " : "";
        passenger_value += itemOptionModel.getDisabilityNumber() > 0 ? String.format(itemView.getContext().getString(R.string.label_disability), itemOptionModel.getDisabilityNumber()) + ", " : "";
        tv_passenger_value.setText(passenger_value);
        tv_status_value.setText(itemOptionModel.getStatus());
        if ("Ready".equals(itemOptionModel.getStatus())) {
            tv_status_value.setTextColor(AppUtils.getColor(itemView.getContext(), R.color.color_orange));
            tv_status_value.setBackground(AppUtils.getDrawable(itemView.getContext(), R.drawable.bg_green_border_white_filled_rounded_corner));
            tv_status_value.setOnClickListener(view -> {
                if (onClickReceiver != null) {
                    onClickReceiver.onClick(itemOptionModel, getAdapterPosition());
                }
//                if (itemView.getContext() != null) {
//                    Toast.makeText(itemView.getContext(), "Coming soon!", Toast.LENGTH_SHORT).show();
//                }
            });
        } else {
            tv_status_value.setTextColor(AppUtils.getColor(itemView.getContext(), R.color.color_green));
        }
        if (BuildConfig.DEBUG) {
            tv_status_value.setOnClickListener(view -> {
                if (onClickReceiver != null) {
                    onClickReceiver.onClick(itemOptionModel, getAdapterPosition());
                }
//                if (itemView.getContext() != null) {
//                    Toast.makeText(itemView.getContext(), "Coming soon!", Toast.LENGTH_SHORT).show();
//                }
            });
        }
    }

    @Override
    public void onClick(View v) {
    }


    //for upload audio
    public interface OnClickReceiver {
        void onClick(RegisterModel itemOptionModel, int position);
    }
}
