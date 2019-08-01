package sang.thai.tran.travelcompanion.fragment

import android.os.Bundle
import android.text.TextUtils
import android.view.View
import android.view.inputmethod.EditorInfo
import android.widget.TextView
import kotlinx.android.synthetic.main.fragment_login.*
import sang.thai.tran.travelcompanion.BuildConfig
import sang.thai.tran.travelcompanion.R
import sang.thai.tran.travelcompanion.activity.LoginActivity
import sang.thai.tran.travelcompanion.model.Response
import sang.thai.tran.travelcompanion.retrofit.BaseObserver
import sang.thai.tran.travelcompanion.retrofit.HttpRetrofitClientBase
import sang.thai.tran.travelcompanion.utils.AppConstant.API_LOGIN
import sang.thai.tran.travelcompanion.utils.AppConstant.SUCCESS_CODE
import sang.thai.tran.travelcompanion.utils.AppUtils
import sang.thai.tran.travelcompanion.utils.ApplicationSingleton
import sang.thai.tran.travelcompanion.utils.DialogUtils
import java.util.*

class LoginFragment : BaseFragment() {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        password.setOnEditorActionListener(TextView.OnEditorActionListener { textView, id, keyEvent ->
            if (id == EditorInfo.IME_ACTION_DONE || id == EditorInfo.IME_NULL) {
                attemptLogin()
                return@OnEditorActionListener true
            }
            false
        })
        email_sign_in_button.setOnClickListener { attemptLogin() }
        tv_register_account.setOnClickListener { register() }
        tv_forgot_pass.setOnClickListener { forgotPass() }
        ApplicationSingleton.getInstance().reset()
    }

    private fun register() {
        if (activity != null) {
            (activity as LoginActivity).register()
        }
    }

    private fun forgotPass() {
        if (activity != null) {
            (activity as LoginActivity).forgotPass()
        }
    }

    override fun layoutId(): Int {
        return R.layout.fragment_login
    }

    /**
     * Attempts to sign in or register the account specified by the login form.
     * If there are form errors (invalid email, missing fields, etc.), the
     * errors are presented and no actual login attempt is made.
     */
    private fun attemptLogin() {

        // Reset errors.
        et_phone!!.error = null
        password!!.error = null

        // Store values at the time of the login attempt.
        val email = et_phone!!.text.toString()

        var cancel = false
        var focusView: View? = null

        // Check for a valid email address.
        if (TextUtils.isEmpty(email)) {
            et_phone!!.error = getString(R.string.error_field_required)
            focusView = et_phone
            cancel = true
        } else if (!isEmailValid(email)) {
            et_phone!!.error = getString(R.string.error_invalid_email)
            focusView = et_phone
            cancel = true
        }

        if (BuildConfig.DEBUG) {
            cancel = false
        }
        if (cancel) {
            focusView!!.requestFocus()
        } else {
            showProgressDialog()
            val map = HashMap<String, String>()
            map["email"] = et_phone!!.text.toString()
            map["password"] = password.text.toString()

            // test
            if (BuildConfig.DEBUG && TextUtils.isEmpty(et_phone!!.text.toString())) {
                map["email"] = "lukatrolai@gmail.com"
                map["password"] = "A@123456"
            }
            HttpRetrofitClientBase.getInstance().loginFunction(API_LOGIN, map, object : BaseObserver<Response>(true) {
                override fun onSuccess(result: Response, response: String) {
                    hideProgressDialog()
                    if (result.statusCode == SUCCESS_CODE) {
                        if (result.result?.data != null) {
                            val userInfo = result.result?.data?.userInfo
                            ApplicationSingleton.getInstance().userInfo = userInfo
                            userInfo?.let { startMain(it, result.result?.data?.token.toString() ) }
                        }
                    } else {
                        activity!!.runOnUiThread { DialogUtils.showAlertDialog(activity, result.message) { dialog, which -> dialog.dismiss() } }
                    }
                }

                override fun onFailure(e: Throwable, errorMsg: String) {
                    hideProgressDialog()
                    if (!TextUtils.isEmpty(errorMsg)) {
                        activity!!.runOnUiThread { DialogUtils.showAlertDialog(activity, errorMsg) { dialog, _ -> dialog.dismiss() } }
                    }
                }
            })
        }
    }

    private fun isEmailValid(email: String): Boolean {

        return email.length > 4 && AppUtils.isEmailValid(email)
    }

}
