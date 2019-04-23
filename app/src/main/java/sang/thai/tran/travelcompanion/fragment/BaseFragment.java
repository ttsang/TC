package sang.thai.tran.travelcompanion.fragment;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import sang.thai.tran.travelcompanion.activity.MainActivity;
import sang.thai.tran.travelcompanion.model.UserInfo;
import sang.thai.tran.travelcompanion.utils.ApplicationSingleton;
import sang.thai.tran.travelcompanion.utils.DialogUtils;

import static sang.thai.tran.travelcompanion.utils.AppConstant.COMPANION_GUIDE;
import static sang.thai.tran.travelcompanion.utils.AppConstant.POSTER;
import static sang.thai.tran.travelcompanion.utils.AppConstant.WELL_TRAINED_COMPANION;
import static sang.thai.tran.travelcompanion.utils.AppUtils.listToString;
import static sang.thai.tran.travelcompanion.utils.DialogUtils.onCreateOptionDialog;

public class BaseFragment extends Fragment {
    AlertDialog progressDialog;

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

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        hideProgressDialog();
    }

    protected void showProgressDialog() {
        if (getActivity() != null)
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                progressDialog = DialogUtils.showProgressDialog(getActivity());
                if (progressDialog != null) {
                    progressDialog.show();
                }
            }
        });
    }

    protected void hideProgressDialog() {
        if (getActivity() != null) {
            getActivity().runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    if (progressDialog != null) {
                        progressDialog.dismiss();
                    }
                }
            });
        }
    }

    protected void startMain(UserInfo userInfo) {
        ApplicationSingleton.getInstance().setUserInfo(userInfo);
        String parent = "0";
        if (userInfo.getType() != null && userInfo.getType().equals(POSTER)) {
            parent = "1";
        }
        String child = "0";
        if (userInfo.getJob_Type() != null) {
            switch (userInfo.getJob_Type()) {
                case COMPANION_GUIDE:
                    child = "1";
                    break;
                case WELL_TRAINED_COMPANION:
                    child = "2";
                    break;
            }
        }
        MainActivity.startMainActivity(getActivity(), "", parent + child);
    }
}
