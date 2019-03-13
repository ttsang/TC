package sang.thai.tran.travelcompanion;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.text.HtmlCompat;
import android.text.Html;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import sang.thai.tran.travelcompanion.fragment.BaseFragment;
import sang.thai.tran.travelcompanion.fragment.ButtonRegisterFragment;
import sang.thai.tran.travelcompanion.fragment.LoginFragment;

/**
 * A login screen that offers login via email/password.
 */
public class LoginActivity extends BaseActivity{

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
//        text = "<i style=\"color:#e67e22\"> This is some text </i>";
        replaceFragment(R.id.fl_content, new LoginFragment());
    }

    public void startActivity() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    public void replaceFragment(int containerId, BaseFragment fragment, boolean isCheck) {
        replaceFragment(containerId, fragment);
    }
}

