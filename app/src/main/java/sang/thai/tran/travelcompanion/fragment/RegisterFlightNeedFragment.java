package sang.thai.tran.travelcompanion.fragment;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.widget.Button;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import butterknife.BindView;
import butterknife.OnClick;
import sang.thai.tran.travelcompanion.R;
import sang.thai.tran.travelcompanion.model.ItemOptionModel;
import sang.thai.tran.travelcompanion.view.EditTextViewLayout;

import static sang.thai.tran.travelcompanion.utils.AppUtils.listToString;

public class RegisterFlightNeedFragment extends RegisterFlightFragment {

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

//    @Nullable
//    @BindView(R.id.rv_service_pkg)
//    RecyclerView rv_service_pkg;

    @BindView(R.id.et_register_service)
    EditTextViewLayout et_register_service;

    @BindView(R.id.et_register_service_more)
    EditTextViewLayout et_register_service_more;

    @BindView(R.id.email_sign_in_button)
    Button email_sign_in_button;

    private List<ItemOptionModel> itemOptionModelList = new ArrayList<>();
    private static final String SEPARATOR = ",";

    public static RegisterFlightNeedFragment newInstance() {
        return new RegisterFlightNeedFragment();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
//        if (rv_service_pkg != null) {
//            RVAdapterChoiceMulti rvAdapterChoiceMulti = new RVAdapterChoiceMulti(getActivity());
//            rv_service_pkg.setAdapter(rvAdapterChoiceMulti);
//            for (String string : getResources().getStringArray(R.array.service_pkg_list)) {
//                ItemOptionModel itemOptionModel = new ItemOptionModel();
//                itemOptionModel.setChecked(false);
//                itemOptionModel.setService(string);
//                itemOptionModelList.add(itemOptionModel);
//            }
//            rvAdapterChoiceMulti.setItemInfoList(itemOptionModelList);
//            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
//            rv_service_pkg.setLayoutManager(linearLayoutManager);
//        }
        et_register_service.setOnFocusChangeListener(new View.OnFocusChangeListener() {
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

    @OnClick(R.id.et_register_service)
    protected void registerService() {

    }

    @OnClick(R.id.et_register_service_more)
    protected void registerServiceMore() {
        onCreateDialog().show();
    }

    List<String> mSelectedServicePackage;
    public Dialog onCreateDialog() {
        mSelectedServicePackage = new ArrayList<>();
        final CharSequence[] strings = getResources().getTextArray(R.array.service_pkg_more);// Where we track the selected items
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        // Set the dialog title
        builder.setTitle(R.string.label_register_service_package)
                // Specify the list array, the items to be selected by default (null for none),
                // and the listener through which to receive callbacks when items are selected
                .setMultiChoiceItems(strings, null,
                        new DialogInterface.OnMultiChoiceClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which,
                                                boolean isChecked) {
                                // Else, if the item is already in the array, remove it
                                if (isChecked) {
                                    // If the user checked the item, add it to the selected items
                                    mSelectedServicePackage.add(strings[which].toString());
                                } else mSelectedServicePackage.remove(strings[which].toString());
                            }
                        })
                // Set the action buttons
                .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        // User clicked OK, so save the mSelectedItems results somewhere
                        // or return them to the component that opened the dialog
                        String result = listToString(mSelectedServicePackage);
                        et_register_service_more.setText(result);
                        dialog.dismiss();
                    }
                })
                .setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.dismiss();
                    }
                });

        return builder.create();
    }
}
