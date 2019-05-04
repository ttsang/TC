package sang.thai.tran.travelcompanion.activity

import android.os.Bundle
import android.view.WindowManager
import sang.thai.tran.travelcompanion.R
import sang.thai.tran.travelcompanion.activity.MainActivity.Companion.UPDATE_INFO
import sang.thai.tran.travelcompanion.fragment.BaseFragment
import sang.thai.tran.travelcompanion.fragment.ForgotPasswordFragment
import sang.thai.tran.travelcompanion.fragment.LoginFragment
import sang.thai.tran.travelcompanion.fragment.RegisterUserInfoFragment

/**
 * A login screen that offers login via email/password.
 */
class LoginActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //        text = "<i style=\"color:#e67e22\"> This is some text </i>";
        window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_MASK_ADJUST)
        if (intent != null) {
            val update = intent.getBooleanExtra(UPDATE_INFO, false)
            if (update) {
                replaceFragment(R.id.fl_content, RegisterUserInfoFragment.newInstance(true))
                return
            }
        }
        replaceFragment(R.id.fl_content, LoginFragment())
    }

    fun replaceFragment(containerId: Int, fragment: BaseFragment, isCheck: Boolean) {
        replaceFragment(containerId, fragment)
    }

    fun register() {
        replaceFragment(R.id.fl_content, RegisterUserInfoFragment.newInstance(false))
    }

    fun forgotPass() {
        replaceFragment(R.id.fl_content, ForgotPasswordFragment.newInstance())
    }

    override val getChildClass: Class<*>
        get() = LoginActivity::class.java
}

