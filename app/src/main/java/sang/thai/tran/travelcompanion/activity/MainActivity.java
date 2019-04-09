package sang.thai.tran.travelcompanion.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import sang.thai.tran.travelcompanion.R;
import sang.thai.tran.travelcompanion.fragment.DisplayUserInfoFragment;
import sang.thai.tran.travelcompanion.fragment.RegisterFinishFragment;
import sang.thai.tran.travelcompanion.fragment.RegisterFlightFragment;
import sang.thai.tran.travelcompanion.fragment.RegisterFlightNeedFragment;
import sang.thai.tran.travelcompanion.fragment.RegisterGuideFragment;
import sang.thai.tran.travelcompanion.fragment.RegisterGuideNeedFragment;
import sang.thai.tran.travelcompanion.fragment.RegisterHourlyServiceFragment;
import sang.thai.tran.travelcompanion.fragment.RegisterWellCompanionFragment;

public class MainActivity extends BaseActivity {
    public static final String TAG = "MainActivity";

    public static final String WORK_TITLE_EXTRA = "WORK_TITLE_EXTRA";
    public static final String USER_TYPE_EXTRA = "USER_TYPE_EXTRA";
    public static final String UPDATE_INFO = "UPDATE_INFO";
    public static final String NEED_SUPPORT = "NEED_SUPPORT";


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

    public void registerFlight(boolean isNeed) {
        replaceFragment(R.id.fl_content, isNeed ? RegisterFlightNeedFragment.newInstance() : RegisterFlightFragment.newInstance(isNeed));
    }

    public void registerGuide(boolean isNeed) {
        replaceFragment(R.id.fl_content, isNeed ? RegisterGuideNeedFragment.newInstance(isNeed) : RegisterGuideFragment.newInstance(isNeed));
    }

    public void registerWell(boolean isNeed) {
        replaceFragment(R.id.fl_content, isNeed ? RegisterHourlyServiceFragment.newInstance(isNeed) : RegisterWellCompanionFragment.newInstance(true) );
    }

    public void finishRegister() {
        replaceFragment(R.id.fl_content, RegisterFinishFragment.newInstance(true));
    }

    public static void startMainActivity(Activity activity, String title, String userType) {
        Intent intent = new Intent(activity, MainActivity.class);
        intent.putExtra(WORK_TITLE_EXTRA, title);
        intent.putExtra(USER_TYPE_EXTRA, userType);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
        activity.startActivity(intent);
    }

    Class getChildClass() {
        return MainActivity.class;
    }
}
