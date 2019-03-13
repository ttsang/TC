package sang.thai.tran.travelcompanion.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.ButterKnife;
import sang.thai.tran.travelcompanion.R;
import sang.thai.tran.travelcompanion.MainActivity;
import sang.thai.tran.travelcompanion.adapter.ExpandableListAdapter;

import static sang.thai.tran.travelcompanion.MainActivity.USER_TYPE_EXTRA;
import static sang.thai.tran.travelcompanion.MainActivity.WORK_TITLE_EXTRA;

public class ButtonRegisterFragment extends BaseFragment {


    ExpandableListAdapter listAdapter;
    ExpandableListView expListView;
    List<String> listDataHeader;
    HashMap<String, List<String>> listDataChild;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_button, container, false);
        ButterKnife.bind(this, view);

        // get the listview
        expListView = (ExpandableListView) view.findViewById(R.id.lvExp);
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

                if (parent.isGroupExpanded(groupPosition)) {
//                    expListView.setDividerHeight(20);
                    // Do your Staff
                } else {
//                    expListView.setDividerHeight(0);
                    // Expanded ,Do your Staff

                }

                return false;
            }
        });
        int width = getResources().getDisplayMetrics().widthPixels;
        if (android.os.Build.VERSION.SDK_INT < android.os.Build.VERSION_CODES.JELLY_BEAN_MR2) {
//            expListView.setIndicatorBounds(width - getPixelValue(getActivity(), 40), width - getPixelValue(getActivity(), 10));
        } else {
//            expListView.setIndicatorBoundsRelative(width - getPixelValue(getActivity() , 40), width - getPixelValue(getActivity(), 10));
        }
        // preparing list data
        prepareListData();

        listAdapter = new ExpandableListAdapter(getActivity(), listDataHeader, listDataChild, expListView);

        // setting list adapter
        expListView.setAdapter(listAdapter);

        DisplayMetrics metrics = new DisplayMetrics();
        getActivity().getWindowManager().getDefaultDisplay().getMetrics(metrics);
        width = metrics.widthPixels;

        expListView.setIndicatorBounds(width - GetPixelFromDips(50), width - GetPixelFromDips(10));
        return view;
    }

    private void startUserInfo(int groupPosition, int childPosition) {
        String text = listDataChild.get(listDataHeader.get(groupPosition)).get(
                childPosition);
        Log.d("Sang", "text: " + text);
        if (getActivity() != null) {
            if (childPosition == 2) {
                text = getActivity().getString(R.string.label_well_trained_companion);
            }
            Intent intent = new Intent(getActivity(), MainActivity.class);
            intent.putExtra(WORK_TITLE_EXTRA, text);
            intent.putExtra(USER_TYPE_EXTRA, String.valueOf(groupPosition) + String.valueOf(childPosition));
            startActivity(intent);
        }
    }

    /*
     * Preparing the list data
     */
    private void prepareListData() {
        listDataHeader = new ArrayList<>();
        listDataChild = new HashMap<>();

        // Adding child data
        listDataHeader.add("NGƯỜI TRỢ GIÚP/ CHỈ DẪN");
        listDataHeader.add("NGƯỜI CẦN TRỢ GIÚP/ CHỈ DẪN");

        // Adding child data
        List<String> top250 = new ArrayList<>();
        top250.add("Trợ giúp đi cùng chuyến bay");
        top250.add("Chỉ dẫn hành trình tại điểm đến");
        top250.add("Trợ giúp người cao tuổi/ khuyết tật/ trẻ em");

        List<String> nowShowing = new ArrayList<>();
        nowShowing.add("Trợ giúp đi cùng chuyến bay");
        nowShowing.add("Chỉ dẫn hành trình tại điểm đến");
        nowShowing.add("Trợ giúp người cao tuổi/ khuyết tật/ trẻ em");


        listDataChild.put(listDataHeader.get(0), top250); // Header, Child data
        listDataChild.put(listDataHeader.get(1), nowShowing);
    }

    public int GetPixelFromDips(float pixels) {
        // Get the screen's density scale
        final float scale = getResources().getDisplayMetrics().density;
        // Convert the dps to pixels, based on density scale
        return (int) (pixels * scale + 0.5f);
    }
}
