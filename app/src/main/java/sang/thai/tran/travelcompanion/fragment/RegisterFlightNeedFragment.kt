package sang.thai.tran.travelcompanion.fragment

import android.os.Bundle
import android.text.TextUtils
import android.view.View
import butterknife.OnClick
import kotlinx.android.synthetic.main.fragment_register_flight_need.*
import sang.thai.tran.travelcompanion.R
import sang.thai.tran.travelcompanion.model.RegisterModel
import sang.thai.tran.travelcompanion.model.Response
import sang.thai.tran.travelcompanion.retrofit.BaseObserver
import sang.thai.tran.travelcompanion.retrofit.HttpRetrofitClientBase
import sang.thai.tran.travelcompanion.utils.AppConstant
import sang.thai.tran.travelcompanion.utils.AppConstant.API_ADDITIONAL_ASSISTANCE
import sang.thai.tran.travelcompanion.utils.AppConstant.API_SELECTED_ASSISTANCE
import sang.thai.tran.travelcompanion.utils.ApplicationSingleton
import sang.thai.tran.travelcompanion.utils.DialogUtils
import sang.thai.tran.travelcompanion.utils.Log

open class RegisterFlightNeedFragment : RegisterFlightFragment() {


    protected open val servicePkgMoreId: Int
        get() = R.array.service_pkg_more

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        tv_register_service_more?.requestFocus()
        tv_register_service_more?.onFocusChangeListener = View.OnFocusChangeListener { v, hasFocus ->
            if (hasFocus) {
                registerServiceMore()
            }
        }
        tv_register_service_more.setOnClickListener {
            registerServiceMore()
        }

        tv_register_service?.requestFocus()
        tv_register_service?.onFocusChangeListener = View.OnFocusChangeListener { v, hasFocus ->
            if (hasFocus) {
                registerService()
            }
        }
        tv_register_service.setOnClickListener {
            registerService()
        }
//        email_sign_in_button.setOnClickListener {
//            (activity as MainActivity).finishRegister()
//        }
    }

    override fun getApiUrl(): String {
        return AppConstant.API_UPDATE_GUIDES_PLACE;
    }

    override fun addMoreService(registerModel: RegisterModel) {
        registerModel.additionalServices = tv_register_service_more?.text.toString()
        registerModel.note = et_msg?.text
        registerModel.childrenNumber = Integer.valueOf(et_kid_number.text)
        registerModel.elderlyNumber = Integer.valueOf(et_elders_number.text)

    }

    override fun layoutId(): Int {
        return R.layout.fragment_register_flight_need
    }

    @OnClick(R.id.tv_register_service)
    fun registerService() {
        if (activity == null) {
            return
        }
        HttpRetrofitClientBase.getInstance().executeGet(API_SELECTED_ASSISTANCE,
                ApplicationSingleton.getInstance().token, object : BaseObserver<Response>(true) {
            override fun onSuccess(result: Response, response: String) {
                hideProgressDialog()
                if (activity == null) {
                    return
                }
                if (result.statusCode == AppConstant.SUCCESS_CODE) {
                    Log.d("Sang", "response result.result?.data : ${result.result?.data?.list}")
                    result.result?.data?.list?.let { it ->
                        val listString = Array(it.size) { "$it" }
                        for (i in 0 until it.size) {
                            listString[i] = it.get(i).text_VN.toString()
                        }
                        activity!!.runOnUiThread {
                            showOptionDialog(tv_register_service!!, getString(R.string.label_register_service_package), listString)
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
//        activity?.resources?.getTextArray(R.array.service_pkg)?.let { showOptionDialog(tv_register_service!!, getString(R.string.label_register_service_package), it) }
    }

    @OnClick(R.id.tv_register_service_more)
    fun registerServiceMore() {
        if (activity == null) {
            return
        }
        HttpRetrofitClientBase.getInstance().executeGet(API_ADDITIONAL_ASSISTANCE,
                ApplicationSingleton.getInstance().token, object : BaseObserver<Response>(true) {
            override fun onSuccess(result: Response, response: String) {
                hideProgressDialog()
                if (activity == null) {
                    return
                }
                if (result.statusCode == AppConstant.SUCCESS_CODE) {
                    Log.d("Sang", "response: $response")
                    result.result?.data?.list?.let { it ->
                        val listString = Array(it.size) { "$it" }
                        for (i in 0 until it.size) {
                            listString[i] = it.get(i).text_VN.toString()
                        }
                        activity!!.runOnUiThread {
                            showOptionDialog(tv_register_service_more!!, getString(R.string.label_register_service_package_additional), listString)
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
//        activity?.resources?.getTextArray(servicePkgMoreId)?.let { showOptionDialog(tv_register_service_more!!, getString(R.string.label_register_service_package_additional), it) }
    }

    companion object {
        fun newInstance(): RegisterFlightNeedFragment {
            return RegisterFlightNeedFragment()
        }
    }


}
