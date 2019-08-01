package sang.thai.tran.travelcompanion.adapter;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import sang.thai.tran.travelcompanion.R;
import sang.thai.tran.travelcompanion.model.RegisterModel;
import sang.thai.tran.travelcompanion.model.holder.ChoiceHolder;


public class NeedSupportAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private List<RegisterModel> lstRecordData;
    private Activity activity;
    private String otherType = "-1";
    private List<String> arrAnsweredId;
    private int commentIndex = 0;
    private List<String> arrAnsweredOthers;
    private boolean isLearning = false;
    private int currentlyFocusedRow = -1;
    ChoiceHolder.OnClickReceiver onClickReceiver;

    public NeedSupportAdapter(Activity activity, List<RegisterModel> locationInfoList, ChoiceHolder.OnClickReceiver onClickReceiver) {
        this.activity = activity;
        lstRecordData = locationInfoList;
        this.onClickReceiver = onClickReceiver;
    }

    public List<RegisterModel> getLocationInfoList() {
        return lstRecordData;
    }

    public void setItemInfoList(List<RegisterModel> locationInfoList) {
        if (locationInfoList != null) {
            this.lstRecordData.addAll(locationInfoList);
            notifyDataSetChanged();
        }
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(activity).inflate(R.layout.item_check, parent, false);
        return new ChoiceHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final RecyclerView.ViewHolder viewHolder, @SuppressLint("RecyclerView") final int position) {
        if (viewHolder instanceof ChoiceHolder) {
            final ChoiceHolder holder = (ChoiceHolder) viewHolder;
            RegisterModel itemOptionModel = lstRecordData.get(position);
            if (itemOptionModel != null) {
                holder.bindView(itemOptionModel, onClickReceiver);
            }
        }
    }


    @Override
    public int getItemCount() {
        if (lstRecordData != null)
            return lstRecordData.size();
        return 5;
    }

    public RegisterModel getItem(int position) {
        if (lstRecordData.size() != 0)
            return lstRecordData.get(position);
        return null;
    }

    private void selectedItem(int position) {
        if (position < lstRecordData.size()) {
        }
    }

    public void onItemClickListener(int position, View view, String input) {
        if (isLearning) {
            return;
        }
        selectedItem(position);
    }
}
