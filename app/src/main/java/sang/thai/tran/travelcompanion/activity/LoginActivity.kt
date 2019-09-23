package sang.thai.tran.travelcompanion.activity

import android.os.Bundle
import android.view.WindowManager
import sang.thai.tran.travelcompanion.R
import sang.thai.tran.travelcompanion.activity.MainActivity.Companion.UPDATE_INFO
import sang.thai.tran.travelcompanion.fragment.*

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

    public fun replaceFragment(containerId: Int, fragment: BaseFragment, isCheck: Boolean) {
        replaceFragment(containerId, fragment)
    }

    fun register() {
        replaceFragment(R.id.fl_content, RegisterUserInfoFragment.newInstance(false))
    }

    fun forgotPass() {
        replaceFragment(R.id.fl_content, ForgotPasswordFragment.newInstance())
    }

    fun changePass() {
        replaceFragment(R.id.fl_content, ResetPasswordFragment.newInstance())
    }

    override val getChildClass: Class<*>
        get() = LoginActivity::class.java

}

