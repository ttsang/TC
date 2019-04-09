package sang.thai.tran.travelcompanion.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;

import butterknife.BindView;
import butterknife.OnClick;
import sang.thai.tran.travelcompanion.R;
import sang.thai.tran.travelcompanion.view.EditTextViewLayout;

import static sang.thai.tran.travelcompanion.activity.MainActivity.NEED_SUPPORT;
import static sang.thai.tran.travelcompanion.utils.AppUtils.openDatePicker;
import static sang.thai.tran.travelcompanion.utils.AppUtils.openTimePicker;
import static sang.thai.tran.travelcompanion.utils.AppUtils.openWeb;

public class RegisterFinishFragment extends BaseFragment {

    public static RegisterFinishFragment newInstance(boolean update) {
        RegisterFinishFragment infoRegisterFragment = new RegisterFinishFragment();
        Bundle bundle = new Bundle();
        bundle.putBoolean(NEED_SUPPORT, update);
        infoRegisterFragment.setArguments(bundle);
        return infoRegisterFragment;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

    }

    @Override
    protected int layoutId() {
        return R.layout.fragment_register_finish;
    }


    @OnClick(R.id.email_sign_in_button)
    protected void openDepartureDate() {
        if (getActivity() == null) {
            return;
        }
        getActivity().finish();
        openWeb(getActivity());
    }
}
