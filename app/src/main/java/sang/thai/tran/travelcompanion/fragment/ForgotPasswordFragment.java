package sang.thai.tran.travelcompanion.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.Toast;

import butterknife.OnClick;
import sang.thai.tran.travelcompanion.R;

public class ForgotPasswordFragment extends BaseFragment {

    public static ForgotPasswordFragment newInstance(boolean update) {
        ForgotPasswordFragment infoRegisterFragment = new ForgotPasswordFragment();
        return infoRegisterFragment;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    protected int layoutId() {
        return R.layout.fragment_forgot_pass;
    }


    @OnClick(R.id.email_sign_in_button)
    protected void login() {
        Toast.makeText(getActivity(), "Come in soon",Toast.LENGTH_SHORT).show();
    }
}
