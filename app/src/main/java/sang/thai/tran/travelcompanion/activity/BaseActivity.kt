package sang.thai.tran.travelcompanion.activity

import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import androidx.fragment.app.FragmentTransaction
import androidx.appcompat.app.AppCompatActivity
import android.view.inputmethod.InputMethodManager
import butterknife.ButterKnife
import butterknife.OnClick
import sang.thai.tran.travelcompanion.R
import sang.thai.tran.travelcompanion.fragment.BaseFragment
import sang.thai.tran.travelcompanion.utils.LocaleHelper
import java.util.*

open class BaseActivity : AppCompatActivity() {

    private var mCurrentFragment: BaseFragment? = null

    private var myLocale: Locale? = null

    internal open val getChildClass: Class<*>
        get() = BaseActivity::class.java

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        ButterKnife.bind(this)
    }

    override fun attachBaseContext(newBase: Context) {
        super.attachBaseContext(LocaleHelper.onAttach(newBase))
    }

    protected fun replaceFragment(containerId: Int, fragment: BaseFragment?) {
        hideSoftKeyboard()
        val fragmentManager = supportFragmentManager
        val ft = fragmentManager.beginTransaction()
        if (fragment != null) {
            ft.replace(containerId, fragment, fragment.javaClass.simpleName)
            ft.addToBackStack(fragment.javaClass.simpleName)
            mCurrentFragment = fragment
        }
        ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
        ft.commit()
    }

    //    private void replaceFragmentWithAnimation(containerId: Int, fragment: BaseFragment?, enter: Int, exit: Int, popEnter: Int, popExit: Int) {
    //        val fragmentManager = supportFragmentManager
    //        val ft = fragmentManager.beginTransaction()
    //        if (enter > 0 || exit > 0 || popEnter > 0 || popExit > 0) {
    //            ft.setCustomAnimations(enter, exit, popEnter, popExit)
    //        }
    //
    //    }


    override fun onBackPressed() {
        hideSoftKeyboard()
        super.onBackPressed()
        if (supportFragmentManager.backStackEntryCount == 0) {
            finish()
        }
        //        if (mCurrentFragment.getClass().isInstance(LoginFragment.class)) {
        //        }
    }

    internal fun hideSoftKeyboard() {
        val view = currentFocus
        if (view != null) {
            val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(view.windowToken, 0)
        }
    }

    @OnClick(R.id.iv_en_flag)
    internal fun setEnglish() {
        //        setLocale("en");
        if (!LocaleHelper.getLanguage(this@BaseActivity).equals("en", ignoreCase = true)) {
            //            LocaleHelper.setLocale(BaseActivity.this, "en");
        }
        setLocale("en")
    }

    @OnClick(R.id.iv_vn_flag)
    internal fun setVietNamese() {
        if (!LocaleHelper.getLanguage(this@BaseActivity).equals("vi", ignoreCase = true)) {
            //            LocaleHelper.setLocale(BaseActivity.this, "vi");
        }
        setLocale("vi")
    }

    private fun setLocale(lang: String) {
        myLocale = Locale(lang)
        Locale.setDefault(myLocale)
        val res = resources
        val dm = res.displayMetrics
        val conf = res.configuration
        conf.locale = myLocale
        res.updateConfiguration(conf, dm)

        val refresh = Intent(this, getChildClass)
        refresh.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
        startActivity(refresh)
        finish()
        // https://github.com/akexorcist/Android-LocalizationActivity

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            //            recreate();
            //            finish();
            //            EventBus.getDefault().post(new MessageEvent());
        }
    }
}
