package sang.thai.tran.travelcompanion.fragment

import android.content.Context
import android.os.Bundle
import android.text.TextUtils
import android.view.View
import android.view.inputmethod.InputMethodManager
import butterknife.OnClick
import kotlinx.android.synthetic.main.fragment_register_hourly_service.*
import sang.thai.tran.travelcompanion.R
import sang.thai.tran.travelcompanion.activity.MainActivity
import sang.thai.tran.travelcompanion.model.RegisterModel
import sang.thai.tran.travelcompanion.model.Response
import sang.thai.tran.travelcompanion.retrofit.BaseObserver
import sang.thai.tran.travelcompanion.retrofit.HttpRetrofitClientBase
import sang.thai.tran.travelcompanion.utils.AppConstant
import sang.thai.tran.travelcompanion.utils.AppConstant.API_UPDATE_WELL_TRAINED
import sang.thai.tran.travelcompanion.utils.AppUtils.openDatePicker
import sang.thai.tran.travelcompanion.utils.AppUtils.openTimePicker
import sang.thai.tran.travelcompanion.utils.ApplicationSingleton
import sang.thai.tran.travelcompanion.utils.DialogUtils
import sang.thai.tran.travelcompanion.utils.Log

class RegisterHourlyServiceFragment : BaseFragment() {

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        et_departure_date?.editText?.onFocusChangeListener = View.OnFocusChangeListener { v, hasFocus ->
            if (hasFocus) {
                openDepartureDate()
            }
        }
        et_departure_date.setOnClickListener { openDepartureDate() }

        et_from?.editText?.onFocusChangeListener = View.OnFocusChangeListener { v, hasFocus ->
            if (hasFocus) {
                openFromTime()
            }
        }
        et_from.setOnClickListener { openFromTime() }

        et_to?.editText?.onFocusChangeListener = View.OnFocusChangeListener { v, hasFocus ->
            if (hasFocus) {
                openToTime()
            }
        }
        et_to.setOnClickListener { openToTime() }

        tv_register_service_more?.requestFocus()
        tv_register_service_more?.onFocusChangeListener = View.OnFocusChangeListener { v, hasFocus ->
            if (hasFocus) {
                registerServiceMore()
            }
            tv_register_service_more?.clearFocus()
        }
        tv_register_service_more.setOnClickListener { registerServiceMore() }

        tv_register_service?.requestFocus()
        tv_register_service?.onFocusChangeListener = View.OnFocusChangeListener { v, hasFocus ->
            if (hasFocus) {
                registerService()
                tv_register_service?.clearFocus()
            } else {
                tv_register_service?.clearFocus()
            }
        }
        tv_register_service_more.setOnClickListener { registerService() }

        email_sign_in_button.setOnClickListener {
            registerApi()
        }
    }

    override fun getApiUrl(): String {
        return API_UPDATE_WELL_TRAINED
    }

    override fun layoutId(): Int {
        return R.layout.fragment_register_hourly_service
    }

    @OnClick(R.id.tv_register_service)
    fun registerService() {
        if (activity == null) {
            return
        }
//        showOptionDialog(tv_register_service?, getString(R.string.label_for), activity?.resources.getTextArray(R.array.register_for_list))
        HttpRetrofitClientBase.getInstance().executeGet(AppConstant.API_SELECTED_ASSISTANCE,
                ApplicationSingleton.getInstance().token, object : BaseObserver<Response>(true) {
            override fun onSuccess(result: Response, response: String) {
                hideProgressDialog()
                if (activity == null) {
                    return
                }
                if (result.statusCode == AppConstant.SUCCESS_CODE) {
                    Log.d("Sang", "response: $response")
                    result.result?.data?.list?.let { it ->
                        val listString  = Array(it.size) { "$it" }
                        for ( i in 0 until it.size) {
                            listString[i] = it[i].text_VN.toString()
                        }
                        activity?.runOnUiThread { showOptionDialog(tv_register_service, getString(R.string.label_register_service_package), listString)
                    } }
                } else {
                    activity?.runOnUiThread { DialogUtils.showAlertDialog(activity, result.message) { dialog, _ -> dialog.dismiss() } }
                }
            }

            override fun onFailure(e: Throwable, errorMsg: String) {
                hideProgressDialog()
                if (!TextUtils.isEmpty(errorMsg)) {
                    activity?.runOnUiThread { DialogUtils.showAlertDialog(activity, errorMsg) { dialog, _ -> dialog.dismiss() } }
                }
            }
        })

        val imm = activity?.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(tv_register_service?.windowToken, 0)
    }

    @OnClick(R.id.tv_register_service_more)
    fun registerServiceMore() {
        if (activity == null) {
            return
        }
        HttpRetrofitClientBase.getInstance().executeGet(AppConstant.API_ADDITIONAL_ASSISTANCE,
                ApplicationSingleton.getInstance().token, object : BaseObserver<Response>(true) {
            override fun onSuccess(result: Response, response: String) {
                hideProgressDialog()
                if (activity == null) {
                    return
                }
                if (result.statusCode == AppConstant.SUCCESS_CODE) {
                    Log.d("Sang", "response: $response")
                    result.result?.data?.list?.let { it ->
                        val listString  = Array(it.size) { "$it" }
                        for ( i in 0 until it.size) {
                            listString[i] = it.get(i).text_VN.toString()
                        }
                        activity?.runOnUiThread { showOptionDialog(tv_register_service_more, getString(R.string.label_register_service_package_additional), listString)
                    } }
                } else {
                    activity?.runOnUiThread { DialogUtils.showAlertDialog(activity, result.message) { dialog, _ -> dialog.dismiss() } }
                }
            }

            override fun onFailure(e: Throwable, errorMsg: String) {
                hideProgressDialog()
                if (!TextUtils.isEmpty(errorMsg)) {
                    activity?.runOnUiThread { DialogUtils.showAlertDialog(activity, errorMsg) { dialog, _ -> dialog.dismiss() } }
                }
            }
        })
//        showOptionDialog(tv_register_service_more!!, getString(R.string.label_register_service_package_additional), activity!!.resources.getTextArray(R.array.hourly_service_pkg))

        val imm = activity?.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(tv_register_service_more?.windowToken, 0)
    }

    fun openDepartureDate() {
        if (activity == null) {
            return
        }
        openDatePicker(activity, et_departure_date)
    }

    fun openFromTime() {
        if (activity == null) {
            return
        }
        openTimePicker(activity, et_from)
    }

    fun openToTime() {
        if (activity == null) {
            return
        }
        openTimePicker(activity, et_to)
    }

    @OnClick(R.id.email_sign_in_button)
    fun register() {
        if (activity == null) {
            return
        }
        (activity as MainActivity).finishRegister()
    }

    override fun createRegisterFlight(): RegisterModel {
        var registerModel = ApplicationSingleton.getInstance().registerModel
        if (registerModel == null) {
            registerModel = RegisterModel()
        }

        registerModel.id = ApplicationSingleton.getInstance().userInfo.code
        registerModel.contactName = et_full_name?.text
        registerModel.wellTrainedObject = tv_register_service?.text.toString()
        registerModel.departureDateFrom = et_departure_date?.text.toString() + " " + et_departure_time?.text.toString()
        registerModel.pickupPoint = et_pickup_place?.text
        registerModel.visitPlaces = et_visit_place?.text
        registerModel.additionalServices = tv_register_service_more?.text.toString()
        return registerModel
    }

    companion object {

        fun newInstance(): RegisterHourlyServiceFragment {
            return RegisterHourlyServiceFragment()
        }
    }
}
