package sang.thai.tran.travelcompanion.fragment;

import android.os.Bundle;

import butterknife.OnClick;
import sang.thai.tran.travelcompanion.R;
import sang.thai.tran.travelcompanion.activity.MainActivity;

public class RegisterWellCompanionFragment extends BaseFragment {

    public static RegisterWellCompanionFragment newInstance(boolean update) {
        RegisterWellCompanionFragment infoRegisterFragment = new RegisterWellCompanionFragment();
        Bundle bundle = new Bundle();
        bundle.putBoolean(MainActivity.NEED_SUPPORT, update);
        infoRegisterFragment.setArguments(bundle);
        return infoRegisterFragment;
    }

    @Override
    protected int layoutId() {
        return R.layout.fragment_register_well_companion;
    }


    @OnClick(R.id.email_sign_in_button)
    void openDepartureDate() {
        if (getActivity() == null) {
            return;
        }
        getActivity().onBackPressed();
//        openWeb(getActivity());
    }
}
