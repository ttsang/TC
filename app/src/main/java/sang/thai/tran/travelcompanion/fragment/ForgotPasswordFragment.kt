package sang.thai.tran.travelcompanion.fragment

import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.widget.Toast

import butterknife.OnClick
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.fragment_forgot_pass.*
import kotlinx.android.synthetic.main.fragment_forgot_pass.email_sign_in_button
import kotlinx.android.synthetic.main.fragment_forgot_pass.et_email
import kotlinx.android.synthetic.main.fragment_register_user_info.*
import sang.thai.tran.travelcompanion.R
import sang.thai.tran.travelcompanion.activity.LoginActivity
import sang.thai.tran.travelcompanion.model.Response
import sang.thai.tran.travelcompanion.retrofit.BaseObserver
import sang.thai.tran.travelcompanion.retrofit.HttpRetrofitClientBase
import sang.thai.tran.travelcompanion.utils.AppConstant
import sang.thai.tran.travelcompanion.utils.ApplicationSingleton
import sang.thai.tran.travelcompanion.utils.DialogUtils
import java.util.HashMap

class ForgotPasswordFragment : BaseFragment() {

    override fun layoutId(): Int {
        return R.layout.fragment_forgot_pass
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        email_sign_in_button.setOnClickListener { login() }
    }


    @OnClick(R.id.email_sign_in_button)
    fun login() {
        showProgressDialog();
        val data = HashMap<String, String>()
        data["email"] = et_email.text.toString()
        data["language"] = "vn"
        HttpRetrofitClientBase.getInstance().executePost(AppConstant.API_FORGOT_PASSWORD, data, object : BaseObserver<Response>(true) {
            override fun onSuccess(result: Response, response: String) {

                hideProgressDialog()
                if (activity == null) {
                    return
                }
                if (result != null) {
                    if (result.statusCode == AppConstant.SUCCESS_CODE) {
                        Log.d("Sang", " API_FORGOT_PASSWORD:  ${result.message}")
                        activity!!.runOnUiThread {
                            DialogUtils.showAlertDialogWithTile(activity, getString(R.string.label_forgot_check_mail_title), getString(R.string.label_forgot_check_mail_label))
                            { dialog, _ ->
                                run {
                                    dialog.dismiss()
                                    if (activity != null) {
                                        (activity as LoginActivity).changePass()
                                    }
                                }
                            }
                        }
                    }
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

    companion object {

        fun newInstance(): ForgotPasswordFragment {
            return ForgotPasswordFragment()
        }
    }
}
