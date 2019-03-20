package sang.thai.tran.travelcompanion.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;

import butterknife.ButterKnife;
import sang.thai.tran.travelcompanion.R;
import sang.thai.tran.travelcompanion.fragment.BaseFragment;
import sang.thai.tran.travelcompanion.fragment.RegisterFlightFragment;
import sang.thai.tran.travelcompanion.fragment.RegisterFlightNeedFragment;
import sang.thai.tran.travelcompanion.fragment.RegisterUserInfoFragment;
import sang.thai.tran.travelcompanion.fragment.LoginFragment;

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
        replaceFragment(R.id.fl_content, new RegisterFlightNeedFragment());
    }

    public void startMainActivity() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    public void replaceFragment(int containerId, BaseFragment fragment, boolean isCheck) {
        replaceFragment(containerId, fragment);
    }

    public void register() {
        replaceFragment(R.id.fl_content, RegisterUserInfoFragment.newInstance(false));
    }
}

