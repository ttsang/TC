package sang.thai.tran.travelcompanion.fragment;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import sang.thai.tran.travelcompanion.R;

import static sang.thai.tran.travelcompanion.utils.AppUtils.listToString;
import static sang.thai.tran.travelcompanion.utils.DialogUtils.onCreateOptionDialog;

public class BaseFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(layoutId(), container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    protected int layoutId() {
        return 0;
    }

    protected void showOptionDialog(final TextView tv_register_service_more, final String title, final CharSequence[] option) {
        final List<String> tmp = new ArrayList<>();
        onCreateOptionDialog(getActivity(),
                title,
                option,
                tmp,
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String result = title + listToString(tmp);
                        tv_register_service_more.setText(result);
                        dialog.dismiss();
                    }
                });
    }
}
