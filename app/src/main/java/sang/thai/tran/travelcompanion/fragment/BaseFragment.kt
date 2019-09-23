package sang.thai.tran.travelcompanion.fragment

import android.graphics.Color
import android.os.Bundle
import android.os.SystemClock
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.widget.LinearLayoutCompat
import androidx.fragment.app.Fragment
import com.nj.imagepicker.ImagePicker
import com.nj.imagepicker.listener.ImageResultListener
import com.nj.imagepicker.utils.DialogConfiguration
import kotlinx.android.synthetic.main.fragment_register_guide.*
import kotlinx.android.synthetic.main.fragment_register_hourly_service.*
import sang.thai.tran.travelcompanion.BuildConfig
import sang.thai.tran.travelcompanion.R
import sang.thai.tran.travelcompanion.activity.MainActivity
import sang.thai.tran.travelcompanion.interfaces.ResultMultiChoiceDialog
import sang.thai.tran.travelcompanion.model.BaseModel
import sang.thai.tran.travelcompanion.model.RegisterModel
import sang.thai.tran.travelcompanion.model.Response
import sang.thai.tran.travelcompanion.model.UserInfo
import sang.thai.tran.travelcompanion.retrofit.BaseObserver
import sang.thai.tran.travelcompanion.retrofit.HttpRetrofitClientBase
import sang.thai.tran.travelcompanion.utils.*
import sang.thai.tran.travelcompanion.utils.AppConstant.*
import sang.thai.tran.travelcompanion.utils.AppUtils.listToString
import sang.thai.tran.travelcompanion.utils.DialogUtils.onCreateOptionDialog
import java.util.*

open class BaseFragment : Fragment() {
    private var progressDialog: AlertDialog? = null
    lateinit var lstAssistance: List<RegisterModel>

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(layoutId(), container, false)
    }

    protected open fun layoutId(): Int {
        return R.layout.activity_login
    }

    protected fun showOptionDialog(tv_register_service_more: TextView?, title: String, option: Array<String>) {
        val tmp = ArrayList<String>()
        onCreateOptionDialog(activity,
                title,
                option,
                tmp
        ) { dialog, _ ->
            val result = title + listToString(tmp)
            tv_register_service_more?.text = result
            dialog.dismiss()
        }
    }

    protected fun showOptionDialog(tv_register_service_more: TextView?, title: String, option: Array<String>, listener: ResultMultiChoiceDialog) {
        val tmp = ArrayList<String>()
        onCreateOptionDialog(activity,
                title,
                option,
                tmp
        ) { dialog, _ ->
            val result = title + listToString(tmp)
            listener.getListSelectedItem(tmp)
            tv_register_service_more?.text = result
            dialog.dismiss()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        hideProgressDialog()
    }

    protected fun showProgressDialog() {
        if (activity != null)
            activity!!.runOnUiThread {
                progressDialog = DialogUtils.showProgressDialog(activity)
                if (progressDialog != null) {
                    progressDialog!!.show()
                }
            }
    }

    protected fun hideProgressDialog() {
        if (activity != null) {
            activity!!.runOnUiThread {
                if (progressDialog != null) {
                    progressDialog!!.dismiss()
                }
            }
        }
    }

    protected fun startMain(userInfo: UserInfo, token: String) {
        ApplicationSingleton.getInstance().userInfo = userInfo
        if (!TextUtils.isEmpty(token) && !"null".equals(token)) {
            ApplicationSingleton.getInstance().token = token
        }
        var parent = "0"
        if (userInfo.type != null && userInfo.type == POSTER) {
            parent = "1"
        }
        var child = "0"
        if (userInfo.job_Type != null) {
            when (userInfo.job_Type) {
                COMPANION_GUIDE -> child = "1"
                WELL_TRAINED_COMPANION -> child = "2"
            }
        }
        if (BuildConfig.APPLICATION_ID.equals("sang.thai.tran.gtn")) {
            child = "2"
        }
        ApplicationSingleton.getInstance().userType = parent + child
        MainActivity.startMainActivity(activity, parent + child)
    }

    fun uploadImage() {
        if (activity == null) {
            return
        }
        val configuration = DialogConfiguration()
                .setTitle("Choose Options")
                .setOptionOrientation(LinearLayoutCompat.VERTICAL)
                .setBackgroundColor(Color.WHITE)
                .setNegativeText("No")
                .setNegativeTextColor(Color.RED)
                .setTitleTextColor(Color.BLUE)

        ImagePicker.build(configuration, ImageResultListener { imageResult ->
            iv_driving_licence!!.setImageBitmap(imageResult.bitmap)
            tv_driving_licence!!.visibility = View.GONE
        }).show(fragmentManager!!)
    }

    open fun getApiUrl(): String {
        return API_UPDATE_ON_FLIGHT
    }

    open fun createRegisterFlight(): RegisterModel {
        return RegisterModel()
    }

    open fun registerApi() {
        if (activity == null || isMultiClicked()) {
            return
        }
        showProgressDialog()
        HttpRetrofitClientBase.getInstance().postRegisterFeature(
                getApiUrl(),
                ApplicationSingleton.getInstance().token,
                createRegisterFlight(),
                object : BaseObserver<Response>(true) {
                    override fun onSuccess(result: Response, response: String) {
                        hideProgressDialog()
                        if (activity == null) {
                            return
                        }
                        if (result.statusCode == SUCCESS_CODE) {
                            Log.d("Sang", "response: $response")
                            activity!!.runOnUiThread {
                                DialogUtils.showAlertDialog(activity, result.message) { dialog, _ ->
                                    run {
                                        dialog.dismiss()
                                        (activity as MainActivity).finishRegister()
                                    }
                                }
                            }
                        } else {
                            activity!!.runOnUiThread { DialogUtils.showAlertDialog(activity, result.message) { dialog, _ -> dialog.dismiss() } }
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

    private var lastClickTime: Long = 0
    // preventing double, using threshold of 700 ms
    protected fun isMultiClicked(): Boolean {
        val now = SystemClock.elapsedRealtime()
        val mLongClickedTime = 850
        if (now - lastClickTime < mLongClickedTime) {
            return true
        }
        lastClickTime = now
        return false
    }

    protected fun showWarningDialog(string: Int) {
        DialogUtils.showAlertDialog(activity, getString(string)) { dialog, which -> dialog.dismiss() }
    }

    protected fun setOnClickAndShowDialog(tv: TextView, list: List<BaseModel>, listener : ResultMultiChoiceDialog) {
        tv.requestFocus()
        tv.setOnClickListener { showDialogList(tv, list, listener) }
    }

    private fun showDialogList(tv: TextView, list: List<BaseModel>?, listener : ResultMultiChoiceDialog) {
        list?.let { it ->
            val listString = Array(it.size) { "$it" }
            for (i in it.indices) {
                if (LocaleHelper.getLanguage(activity).equals("en", ignoreCase = true)) {
                    listString[i] = it[i].text_2.toString()
                } else {
                    listString[i] = it[i].text_1.toString()
                }
            }
            activity?.runOnUiThread {
                showOptionDialog(tv, tv.text.toString(), listString, listener)
            }
        }
    }

    protected fun openFromTime() {
        if (activity == null || isMultiClicked()) {
            return
        }
        AppUtils.openTimePicker(activity, et_from)
    }

    protected fun openToTime() {
        if (activity == null || isMultiClicked()) {
            return
        }
        AppUtils.openTimePicker(activity, et_to)
    }
}
