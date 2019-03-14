package sang.thai.tran.travelcompanion.activity;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

import sang.thai.tran.travelcompanion.fragment.BaseFragment;
import sang.thai.tran.travelcompanion.fragment.LoginFragment;

public class BaseActivity extends AppCompatActivity {

    private BaseFragment mCurrentFragment;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
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
        if (mCurrentFragment.getClass().isInstance(LoginFragment.class)) {
            finish();
        }
    }

    void hideSoftKeyboard() {
        View view = getCurrentFocus();
        if (view != null) {
            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }
}
