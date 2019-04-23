package sang.thai.tran.travelcompanion.fragment

import android.os.AsyncTask
import android.os.Bundle
import android.provider.ContactsContract
import android.text.TextUtils
import android.view.View
import android.view.inputmethod.EditorInfo
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import butterknife.BindView
import butterknife.OnClick
import sang.thai.tran.travelcompanion.R
import sang.thai.tran.travelcompanion.activity.LoginActivity
import sang.thai.tran.travelcompanion.model.Response
import sang.thai.tran.travelcompanion.retrofit.BaseObserver
import sang.thai.tran.travelcompanion.retrofit.HttpRetrofitClientBase
import sang.thai.tran.travelcompanion.utils.AppConstant.SUCCESS_CODE
import sang.thai.tran.travelcompanion.utils.ApplicationSingleton
import sang.thai.tran.travelcompanion.utils.DialogUtils
import java.util.*

class LoginFragment : BaseFragment() {
    /**
     * Keep track of the login task to ensure we can cancel it if requested.
     */
    private var mAuthTask: UserLoginTask? = null

    // UI references.
    @BindView(R.id.et_phone)
    internal var et_phone: EditText? = null

    @BindView(R.id.password)
    internal var mPasswordView: EditText? = null

    @BindView(R.id.tv_register_account)
    internal var tv_register_account: TextView? = null

    @BindView(R.id.email_sign_in_button)
    internal var email_sign_in_button: Button? = null

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        mPasswordView!!.setOnEditorActionListener(TextView.OnEditorActionListener { textView, id, keyEvent ->
            if (id == EditorInfo.IME_ACTION_DONE || id == EditorInfo.IME_NULL) {
                attemptLogin()
                return@OnEditorActionListener true
            }
            false
        })
    }

    @OnClick(R.id.email_sign_in_button)
    fun login() {
        attemptLogin()
    }

    @OnClick(R.id.tv_register_account)
    fun register() {
        if (activity != null) {
            (activity as LoginActivity).register()
        }
    }

    @OnClick(R.id.tv_forgot_pass)
    fun forgotPass() {
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
        if (mAuthTask != null) {
            return
        }

        // Reset errors.
        et_phone!!.error = null
        mPasswordView!!.error = null

        // Store values at the time of the login attempt.
        val email = et_phone!!.text.toString()
        val password = mPasswordView!!.text.toString()

        var cancel = false
        var focusView: View? = null

        // Check for a valid password, if the user entered one.
        if (!TextUtils.isEmpty(password) && !isPasswordValid(password)) {
            //            mPasswordView.setError(getString(R.string.error_invalid_password));
            //            focusView = mPasswordView;
            //            cancel = true;
        }

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

        if (cancel) {
            // There was an error; don't attempt login and focus the first
            // form field with an error.
            focusView!!.requestFocus()
        } else {
            // Show a progress spinner, and kick off a background task to
            // perform the user login attempt.
            //            showProgress(true);
            //            mAuthTask = new UserLoginTask(email, password);
            //            mAuthTask.execute((Void) null);
            showProgressDialog()
            val map = HashMap<String, String>()
            map["email"] = et_phone!!.text.toString()
            map["password"] = mPasswordView!!.text.toString()
            HttpRetrofitClientBase.getInstance().loginFunction("api/account/login", map, object : BaseObserver<Response>(true) {
                override fun onSuccess(response: Response, responseStr: String) {
                    hideProgressDialog()
                    if (activity == null) {
                        return
                    }
                    if (response.statusCode == SUCCESS_CODE) {
                        if (response.result.data != null) {
                            val userInfo = response.result.data.userInfo
                            ApplicationSingleton.getInstance().userInfo = userInfo
                            startMain(userInfo)
                        }
                    } else {
                        activity!!.runOnUiThread { DialogUtils.showAlertDialog(activity, response.message) { dialog, which -> dialog.dismiss() } }

                    }
                }

                override fun onFailure(e: Throwable, errorMsg: String) {
                    hideProgressDialog()
                }
            })
        }
    }

    private fun isEmailValid(email: String): Boolean {
        //TODO: Replace this with your own logic
        //        return email.contains("@");
        return email.length > 4
    }

    private fun isPasswordValid(password: String): Boolean {
        //TODO: Replace this with your own logic
        return password.length > 4
    }

    //    /**
    //     * Shows the progress UI and hides the login form.
    //     */
    //    @TargetApi(Build.VERSION_CODES.HONEYCOMB_MR2)
    //    private void showProgress(final boolean show) {
    //        // On Honeycomb MR2 we have the ViewPropertyAnimator APIs, which allow
    //        // for very easy animations. If available, use these APIs to fade-in
    //        // the progress spinner.
    //        int shortAnimTime = getResources().getInteger(android.R.integer.config_shortAnimTime);
    //
    //        mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
    //        mProgressView.animate().setDuration(shortAnimTime).alpha(
    //                show ? 1 : 0).setListener(new AnimatorListenerAdapter() {
    //            @Override
    //            public void onAnimationEnd(Animator animation) {
    //                mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
    //            }
    //        });
    //    }


    private fun addEmailsToAutoComplete(emailAddressCollection: List<String>) {
        //Create adapter to tell the AutoCompleteTextView what to show in its dropdown list.
        val adapter = ArrayAdapter(activity!!,
                android.R.layout.simple_dropdown_item_1line, emailAddressCollection)

        //        et_phone.setAdapter(adapter);
    }


    private interface ProfileQuery {
        companion object {
            val PROJECTION = arrayOf(ContactsContract.CommonDataKinds.Email.ADDRESS, ContactsContract.CommonDataKinds.Email.IS_PRIMARY)

            val ADDRESS = 0
            val IS_PRIMARY = 1
        }
    }

    /**
     * Represents an asynchronous login/registration task used to authenticate
     * the user.
     */
    inner class UserLoginTask internal constructor(private val mEmail: String, private val mPassword: String) : AsyncTask<Void, Void, Boolean>() {

        override fun doInBackground(vararg params: Void): Boolean? {
            // TODO: attempt authentication against a network service.

            try {
                // Simulate network access.
                Thread.sleep(2000)
            } catch (e: InterruptedException) {
                return false
            }

            for (credential in DUMMY_CREDENTIALS) {
                val pieces = credential.split(":".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()
                if (pieces[0] == mEmail) {
                    // Account exists, return true if the password matches.
                    return pieces[1] == mPassword
                }
            }

            // TODO: register the new account here.
            return true
        }

        override fun onPostExecute(success: Boolean?) {
            mAuthTask = null
            //            showProgress(false);

            if (success!!) {
                //                finish();
            } else {
                mPasswordView!!.error = getString(R.string.error_incorrect_password)
                mPasswordView!!.requestFocus()
            }
        }

        override fun onCancelled() {
            mAuthTask = null
            //            showProgress(false);
        }
    }

    companion object {

        /**
         * Id to identity READ_CONTACTS permission request.
         */
        private val REQUEST_READ_CONTACTS = 0

        /**
         * A dummy authentication store containing known user names and passwords.
         * TODO: remove after connecting to a real authentication system.
         */
        private val DUMMY_CREDENTIALS = arrayOf("foo@example.com:hello", "bar@example.com:world")
    }
}
