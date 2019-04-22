package sang.thai.tran.travelcompanion.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;

import com.google.gson.Gson;

import java.util.HashMap;
import java.util.Map;

import butterknife.ButterKnife;
import sang.thai.tran.travelcompanion.R;
import sang.thai.tran.travelcompanion.activity.MainActivity;
import sang.thai.tran.travelcompanion.adapter.ExpandableListAdapter;
import sang.thai.tran.travelcompanion.model.Response;
import sang.thai.tran.travelcompanion.retrofit.BaseObserver;
import sang.thai.tran.travelcompanion.retrofit.HttpRetrofitClientBase;
import sang.thai.tran.travelcompanion.utils.ApplicationSingleton;

public class ButtonRegisterFragment extends BaseFragment {

    ExpandableListAdapter listAdapter;
    ExpandableListView expListView;
    String[] listDataHeader;
    HashMap<String, String[]> listDataChild;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_button, container, false);
        ButterKnife.bind(this, view);

        expListView = view.findViewById(R.id.lvExp);
        expListView.setIndicatorBounds(0, 20);
        // setOnChildClickListener listener for child row click or song name click
        expListView.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {
                //get the group header
                //display it or do something with it
                Log.d("Sang", "groupPosition: " + groupPosition + " - childPosition: " + childPosition);
                startUserInfo(groupPosition, childPosition);
                return false;
            }
        });
        // setOnGroupClickListener listener for group Song List click
        expListView.setOnGroupClickListener(new ExpandableListView.OnGroupClickListener() {
            @Override
            public boolean onGroupClick(ExpandableListView parent, View v, int groupPosition, long id) {
                //get the group header

//                if (parent.isGroupExpanded(groupPosition)) {
////                    expListView.setDividerHeight(20);
//                    // Do your Staff
//                } else {
////                    expListView.setDividerHeight(0);
//                    // Expanded ,Do your Staff
//
//                }

                return false;
            }
        });
        prepareListData();

        listAdapter = new ExpandableListAdapter(getActivity(), listDataHeader, listDataChild, expListView);

        // setting list adapter
        expListView.setAdapter(listAdapter);

        DisplayMetrics metrics = new DisplayMetrics();
        getActivity().getWindowManager().getDefaultDisplay().getMetrics(metrics);
        int width = metrics.widthPixels;

        expListView.setIndicatorBounds(width - GetPixelFromDips(50), width - GetPixelFromDips(10));
        return view;
    }

    private void startUserInfo(int groupPosition, int childPosition) {
        String text = listDataChild.get(listDataHeader[groupPosition])[childPosition];
        Log.d("Sang", "text: " + text);
        ApplicationSingleton.getInstance().getUserInfo()
                .setType(String.valueOf(groupPosition) + String.valueOf(childPosition));
        if (groupPosition == 0) {
            String job_type = "escortee";
            switch (childPosition) {
                case 0:
                    job_type = "escortee";
                    break;
                case 1:
                    job_type = "companion guide";
                    break;
                case 2:
                    job_type = "well trained companion";
                    break;

            }
            ApplicationSingleton.getInstance().getUserInfo()
                    .setJob_Type(job_type);
            executeRegister();
        } else {
            return;
        }
        if (getActivity() != null) {
            if (childPosition == 2) {
                text = getActivity().getString(R.string.label_well_trained_companion);
            }

        }
    }

    /*
     * Preparing the list data
     */
    private void prepareListData() {
        listDataHeader = getResources().getStringArray(R.array.list_header);
        listDataChild = new HashMap<>();


//        // Adding child data
//        List<String> top250 = new ArrayList<>();
//        top250.add("Trợ giúp đi cùng chuyến bay");
//        top250.add("Chỉ dẫn hành trình tại điểm đến");
//        top250.add("Trợ giúp người cao tuổi/ khuyết tật/ trẻ em");
//
//        List<String> nowShowing = new ArrayList<>();
//        nowShowing.add("Trợ giúp đi cùng chuyến bay");
//        nowShowing.add("Chỉ dẫn hành trình tại điểm đến");
//        nowShowing.add("Trợ giúp người cao tuổi/ khuyết tật/ trẻ em");


        listDataChild.put(getResources().getStringArray(R.array.list_header)[0], getResources().getStringArray(R.array.list_item)); // Header, Child data
        listDataChild.put(getResources().getStringArray(R.array.list_header)[1], getResources().getStringArray(R.array.list_item));
    }

    public int GetPixelFromDips(float pixels) {
        // Get the screen's density scale
        final float scale = getResources().getDisplayMetrics().density;
        // Convert the dps to pixels, based on density scale
        return (int) (pixels * scale + 0.5f);
    }

    private void executeRegister() {
        String model = new Gson().toJson(ApplicationSingleton.getInstance().getUserInfo());
        showProgressDialog();
        Map<String, String> map = new HashMap<>();
        map.put("model", model);
        Log.d("Sang","model: " + model);
//        map.put("password",mPasswordView.getText().toString());
        HttpRetrofitClientBase.getInstance().executePost("api/account/register", ApplicationSingleton.getInstance().getUserInfo(), new BaseObserver<Response>(true) {
            @Override
            public void onSuccess(Response response, String responseStr) {
                hideProgressDialog();
                Log.d("Sang","responseStr: " + responseStr);
                if (response.getResult().getData() != null) {
                    ApplicationSingleton.getInstance().setUserInfo(response.getResult().getData().getUserInfo());
                    if (getActivity() != null) {
//                        MainActivity.startMainActivity(getActivity(), "", et_phone.getText().toString());
                    }
                }
                MainActivity.startMainActivity(getActivity(), "1", String.valueOf(1) + String.valueOf(1));
            }

            @Override
            public void onFailure(Throwable e, String errorMsg) {
                hideProgressDialog();
            }
        });
    }
}
