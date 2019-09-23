package sang.thai.tran.travelcompanion.activity

import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.view.inputmethod.InputMethodManager
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentTransaction
import butterknife.OnClick
import kotlinx.android.synthetic.main.layout_action_bar.*
import sang.thai.tran.travelcompanion.R
import sang.thai.tran.travelcompanion.fragment.BaseFragment
import sang.thai.tran.travelcompanion.utils.LocaleHelper
import sang.thai.tran.travelcompanion.utils.PreferenceHelper

open class BaseActivity : AppCompatActivity() {

    private var mCurrentFragment: BaseFragment? = null

    internal open val getChildClass: Class<*>
        get() = BaseActivity::class.java

    internal open val layoutId: Int
        get() = R.layout.activity_login

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(layoutId)
//        ButterKnife.bind(this)
        iv_en_flag.setOnClickListener {
            setLocale("en") }
        iv_vn_flag.setOnClickListener {
            setLocale("vi")
        }
        tv_log_out.setOnClickListener {
            startActivity(Intent(this,  LoginActivity::class.java))
            finish()
        }
    }

    override fun attachBaseContext(newBase: Context) {
        super.attachBaseContext(LocaleHelper.onAttach(newBase, PreferenceHelper.getInstance(newBase).getLanguage("vi")))
    }

    fun replaceFragment(containerId: Int, fragment: BaseFragment?) {
        hideSoftKeyboard()
        val fragmentManager = supportFragmentManager
        val ft = fragmentManager.beginTransaction()
        if (fragment != null) {
            ft.setCustomAnimations(android.R.anim.slide_in_left,
                    android.R.anim.slide_out_right,
                    android.R.anim.slide_in_left,
                    android.R.anim.slide_out_right)
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
//        myLocale = Locale(lang)
//        Locale.setDefault(myLocale)
//        val res = resources
//        val dm = res.displayMetrics
//        val conf = res.configuration
//        conf.locale = myLocale
//        res.updateConfiguration(conf, dm)
//
//        // https://github.com/akexorcist/Android-LocalizationActivity
//
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
//            //            recreate();
//            //            finish();
//            //            EventBus.getDefault().post(new MessageEvent());
//        }
        LocaleHelper.setLocale(this, lang)
        val refresh = Intent(this, getChildClass)
        refresh.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
        startActivity(refresh)
        finish()
//        finish();
//        recreate()
    }
}
