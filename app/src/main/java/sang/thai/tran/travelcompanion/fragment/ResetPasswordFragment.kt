package sang.thai.tran.travelcompanion.fragment

import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import butterknife.OnClick
import kotlinx.android.synthetic.main.fragment_forgot_pass.email_sign_in_button
import kotlinx.android.synthetic.main.fragment_reset_pass.*
import sang.thai.tran.travelcompanion.R
import sang.thai.tran.travelcompanion.model.Response
import sang.thai.tran.travelcompanion.retrofit.BaseObserver
import sang.thai.tran.travelcompanion.retrofit.HttpRetrofitClientBase
import sang.thai.tran.travelcompanion.utils.AppConstant
import sang.thai.tran.travelcompanion.utils.DialogUtils
import java.util.*

class ResetPasswordFragment : BaseFragment() {

    override fun layoutId(): Int {
        return R.layout.fragment_reset_pass
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        email_sign_in_button.setOnClickListener { login() }
    }


    @OnClick(R.id.email_sign_in_button)
    fun login() {
        showProgressDialog()
        val data = HashMap<String, String>()
        data["email"] = et_reset_email.text.toString()
        data["resetPasswordCode"] = et_reset_code.text.toString()
        data["newPassword"] = et_reset_new_pass.text.toString()
        HttpRetrofitClientBase.getInstance().executePost(AppConstant.API_CHANGE_PASSWORD, data, object : BaseObserver<Response>(true) {
            override fun onSuccess(result: Response, response: String) {

                hideProgressDialog()
                if (activity == null) {
                    return
                }
                if (result.statusCode == AppConstant.SUCCESS_CODE) {
                    Log.d("Sang", " API_CHANGE_PASSWORD:  ${result.message}")
                    activity!!.runOnUiThread { DialogUtils.showAlertDialog(activity, result.result?.data?.message) { dialog, which ->
                        run {
                            dialog.dismiss()
                            while (fragmentManager?.backStackEntryCount!! > 1) {
                                fragmentManager?.popBackStackImmediate()
                            }

                        }
                    } }
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

        fun newInstance(): ResetPasswordFragment {
            return ResetPasswordFragment()
        }
    }
}
