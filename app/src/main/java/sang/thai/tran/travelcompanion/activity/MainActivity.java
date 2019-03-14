package sang.thai.tran.travelcompanion.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import sang.thai.tran.travelcompanion.R;
import sang.thai.tran.travelcompanion.fragment.DisplayUserInfoFragment;
import sang.thai.tran.travelcompanion.fragment.RegisterFlightFragment;
import sang.thai.tran.travelcompanion.fragment.RegisterUserInfoFragment;
import sang.thai.tran.travelcompanion.model.UserInfo;
import sang.thai.tran.travelcompanion.utils.ApplicationSingleton;
import sang.thai.tran.travelcompanion.view.EditTextViewLayout;

import static sang.thai.tran.travelcompanion.utils.AppConstant.NEED_SUPPORT_COMPANION;
import static sang.thai.tran.travelcompanion.utils.AppConstant.NEED_SUPPORT_COMPANION_GUIDE;
import static sang.thai.tran.travelcompanion.utils.AppConstant.NEED_SUPPORT_COMPANION_WELL;
import static sang.thai.tran.travelcompanion.utils.AppConstant.SUPPORT_COMPANION;
import static sang.thai.tran.travelcompanion.utils.AppConstant.SUPPORT_COMPANION_GUIDE;
import static sang.thai.tran.travelcompanion.utils.AppConstant.SUPPORT_COMPANION_WELL;

public class MainActivity extends BaseActivity {
    public static final String TAG = "MainActivity";

    public static final String WORK_TITLE_EXTRA = "WORK_TITLE_EXTRA";
    public static final String USER_TYPE_EXTRA = "USER_TYPE_EXTRA";
    public static final String UPDATE_INFO = "UPDATE_INFO";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_user_info);
//        ButterKnife.bind(this);

//        initView();
        showInfo();
    }

    private void showInfo() {
        Intent intent = getIntent();
        if (intent != null) {
            Bundle bundle = new Bundle();
            bundle.putString(USER_TYPE_EXTRA, intent.getStringExtra(USER_TYPE_EXTRA));
            bundle.putString(WORK_TITLE_EXTRA, intent.getStringExtra(WORK_TITLE_EXTRA));
            replaceFragment(R.id.fl_content, DisplayUserInfoFragment.newInstance(bundle));
        }
    }

    public void registerFlight() {
        replaceFragment(R.id.fl_content, RegisterFlightFragment.newInstance(false));
    }

}
