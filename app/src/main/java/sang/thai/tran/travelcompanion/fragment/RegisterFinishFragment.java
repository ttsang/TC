package sang.thai.tran.travelcompanion.fragment;

import android.os.Bundle;

import butterknife.OnClick;
import sang.thai.tran.travelcompanion.R;
import sang.thai.tran.travelcompanion.activity.MainActivity;

import static sang.thai.tran.travelcompanion.utils.AppUtils.openWeb;

public class RegisterFinishFragment extends BaseFragment {

    public static RegisterFinishFragment newInstance(boolean update) {
        RegisterFinishFragment infoRegisterFragment = new RegisterFinishFragment();
        Bundle bundle = new Bundle();
        bundle.putBoolean(MainActivity.NEED_SUPPORT, update);
        infoRegisterFragment.setArguments(bundle);
        return infoRegisterFragment;
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
