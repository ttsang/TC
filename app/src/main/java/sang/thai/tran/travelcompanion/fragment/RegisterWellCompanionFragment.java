package sang.thai.tran.travelcompanion.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import sang.thai.tran.travelcompanion.R;
import sang.thai.tran.travelcompanion.view.EditTextViewLayout;

import static sang.thai.tran.travelcompanion.activity.MainActivity.NEED_SUPPORT;
import static sang.thai.tran.travelcompanion.utils.AppUtils.openWeb;

public class RegisterWellCompanionFragment extends BaseFragment {

    public static RegisterWellCompanionFragment newInstance(boolean update) {
        RegisterWellCompanionFragment infoRegisterFragment = new RegisterWellCompanionFragment();
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
        return R.layout.fragment_register_well_companion;
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
