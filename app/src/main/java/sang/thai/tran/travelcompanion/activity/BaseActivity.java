package sang.thai.tran.travelcompanion.activity;

import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import sang.thai.tran.travelcompanion.R;
import sang.thai.tran.travelcompanion.fragment.BaseFragment;
import sang.thai.tran.travelcompanion.utils.LocaleHelper;
import sang.thai.tran.travelcompanion.view.EditTextViewLayout;

public class BaseActivity extends AppCompatActivity {

    private BaseFragment mCurrentFragment;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
    }

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(LocaleHelper.onAttach(newBase));
    }

    protected void replaceFragment(int containerId, BaseFragment fragment) {
        hideSoftKeyboard();
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction ft = fragmentManager.beginTransaction();
        if (fragment != null) {
            ft.replace(containerId, fragment, fragment.getClass().getSimpleName());
            ft.addToBackStack(fragment.getClass().getSimpleName());
            mCurrentFragment = fragment;
        }
        ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        ft.commit();
    }

//    private void replaceFragmentWithAnimation(containerId: Int, fragment: BaseFragment?, enter: Int, exit: Int, popEnter: Int, popExit: Int) {
//        val fragmentManager = supportFragmentManager
//        val ft = fragmentManager.beginTransaction()
//        if (enter > 0 || exit > 0 || popEnter > 0 || popExit > 0) {
//            ft.setCustomAnimations(enter, exit, popEnter, popExit)
//        }
//
//    }


    @Override
    public void onBackPressed() {
        hideSoftKeyboard();
        super.onBackPressed();
        if (getSupportFragmentManager().getBackStackEntryCount() == 0) {
            finish();
        }
//        if (mCurrentFragment.getClass().isInstance(LoginFragment.class)) {
//        }
    }

    void hideSoftKeyboard() {
        View view = getCurrentFocus();
        if (view != null) {
            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }

    @OnClick(R.id.iv_en_flag)
    void setEnglish() {
//        setLocale("en");
        if (!LocaleHelper.getLanguage(BaseActivity.this).equalsIgnoreCase("en")) {
//            LocaleHelper.setLocale(BaseActivity.this, "en");
        }
        setLocale("en");
    }

    @OnClick(R.id.iv_vn_flag)
    void setVietNamese() {
        if (!LocaleHelper.getLanguage(BaseActivity.this).equalsIgnoreCase("vi")) {
//            LocaleHelper.setLocale(BaseActivity.this, "vi");
        }
        setLocale("vi");
    }

    private Locale myLocale;
    public void setLocale(String lang) {
        myLocale = new Locale(lang);
        Locale.setDefault(myLocale);
        Resources res = getResources();
        DisplayMetrics dm = res.getDisplayMetrics();
        Configuration conf = res.getConfiguration();
        conf.locale = myLocale;
        res.updateConfiguration(conf, dm);

        Intent refresh = new Intent(this, getChildClass());
        refresh.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(refresh);
        finish();

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
//            recreate();
//            finish();
//            EventBus.getDefault().post(new MessageEvent());
        }
    }

    Class getChildClass() {
        return BaseActivity.class;
    }
}
