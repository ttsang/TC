package sang.thai.tran.travelcompanion.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;

import sang.thai.tran.travelcompanion.R;
import sang.thai.tran.travelcompanion.fragment.BaseFragment;
import sang.thai.tran.travelcompanion.fragment.ForgotPasswordFragment;
import sang.thai.tran.travelcompanion.fragment.LoginFragment;
import sang.thai.tran.travelcompanion.fragment.RegisterUserInfoFragment;

import static sang.thai.tran.travelcompanion.activity.MainActivity.UPDATE_INFO;

/**
 * A login screen that offers login via email/password.
 */
public class LoginActivity extends BaseActivity{

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        text = "<i style=\"color:#e67e22\"> This is some text </i>";
        if (getIntent() != null) {
            boolean update = getIntent().getBooleanExtra(UPDATE_INFO, false);
            if (update) {
                replaceFragment(R.id.fl_content, RegisterUserInfoFragment.newInstance(true));
                return;
            }
        }
        replaceFragment(R.id.fl_content, new LoginFragment());
    }

    public void replaceFragment(int containerId, BaseFragment fragment, boolean isCheck) {
        replaceFragment(containerId, fragment);
    }

    public void register() {
        replaceFragment(R.id.fl_content, RegisterUserInfoFragment.newInstance(false));
    }

    public void forgotPass() {
        replaceFragment(R.id.fl_content, ForgotPasswordFragment.newInstance(false));
    }

    Class getChildClass() {
        return LoginActivity.class;
    }
}

