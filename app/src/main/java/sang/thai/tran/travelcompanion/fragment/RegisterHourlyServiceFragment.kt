package sang.thai.tran.travelcompanion.fragment

import android.content.Context
import android.os.Bundle
import android.text.TextUtils
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.TextView
import kotlinx.android.synthetic.main.fragment_register_hourly_service.*
import kotlinx.android.synthetic.main.fragment_register_hourly_service.et_from
import kotlinx.android.synthetic.main.fragment_register_hourly_service.et_to
import kotlinx.android.synthetic.main.fragment_register_hourly_service.ll_parent
import kotlinx.android.synthetic.main.fragment_register_well_companion_page_2.*
import kotlinx.android.synthetic.main.layout_back_next.*
import sang.thai.tran.travelcompanion.R
import sang.thai.tran.travelcompanion.activity.BaseActivity
import sang.thai.tran.travelcompanion.activity.MainActivity
import sang.thai.tran.travelcompanion.interfaces.ResultMultiChoiceDialog
import sang.thai.tran.travelcompanion.model.RegisterModel
import sang.thai.tran.travelcompanion.model.Response
import sang.thai.tran.travelcompanion.retrofit.BaseObserver
import sang.thai.tran.travelcompanion.retrofit.HttpRetrofitClientBase
import sang.thai.tran.travelcompanion.utils.AppConstant
import sang.thai.tran.travelcompanion.utils.AppConstant.*
import sang.thai.tran.travelcompanion.utils.AppUtils.openDatePicker
import sang.thai.tran.travelcompanion.utils.ApplicationSingleton
import sang.thai.tran.travelcompanion.utils.DialogUtils
import sang.thai.tran.travelcompanion.utils.Log
import sang.thai.tran.travelcompanion.view.EditTextViewLayout

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

        tv_register_service_more.setOnClickListener { registerServiceMore(API_ADDITIONAL_ASSISTANCE, tv_register_service_more) }

        tv_register_service.setOnClickListener { registerServiceMore(API_SELECTED_ASSISTANCE, tv_register_service) }

        btn_next.setOnClickListener {
            //            openDepartureDate()
            executeRegister()
        }

        btn_back.setOnClickListener {
            //            openDepartureDate()
            createRegisterFlight()
            (activity as BaseActivity).onBackPressed()
        }
        if (tv_register_object != null && ApplicationSingleton.getInstance().data.helperSubjectQualificationList != null)
            setOnClickAndShowDialog(tv_register_object, ApplicationSingleton.getInstance().data.helperSubjectQualificationList!!,
                    object : ResultMultiChoiceDialog {
                        override fun getListSelectedItem(list: List<String>) {
                            ApplicationSingleton.getInstance().professionalRecordsInfoModel?.subject_Qualification_List = list
                        }
                    })
    }

    override fun getApiUrl(): String {
        return API_UPDATE_WELL_TRAINED
    }

    override fun layoutId(): Int {
        return R.layout.fragment_register_hourly_service
    }

    private fun registerServiceMore(url: String, tv: TextView) {
        if (activity == null || isMultiClicked()) {
            return
        }
        val imm = activity?.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(tv.windowToken, 0)
        Log.d("Sang", "registerServiceMore:")
        HttpRetrofitClientBase.getInstance().executeGet(url,
                ApplicationSingleton.getInstance().token, object : BaseObserver<Response>(true) {
            override fun onSuccess(result: Response, response: String) {
                hideProgressDialog()
                if (activity == null) {
                    return
                }
                if (result.statusCode == AppConstant.SUCCESS_CODE) {
                    Log.d("Sang", "response: $response")
                    lstAssistance = result.result?.data?.list!!

                    result.result?.data?.list?.let { it ->
                        val listString = Array(it.size) { "$it" }
                        for (i in 0 until it.size) {
                            listString[i] = it.get(i).text_VN.toString()
                        }
                        activity?.runOnUiThread {
                            showOptionDialog(tv, tv.text.toString(), listString)
                        }
                    }
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
    }

    private fun openDepartureDate() {
        if (activity == null || isMultiClicked()) {
            return
        }
        openDatePicker(activity, et_departure_date)
    }

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

        var wellTrainedObject = tv_register_object?.text.toString()
        wellTrainedObject = wellTrainedObject.substring(wellTrainedObject.length - getString(R.string.label_register_object).length, wellTrainedObject.length)
        registerModel.wellTrainedObject = wellTrainedObject

        var expectAssistance = tv_register_service?.text.toString()
        expectAssistance = expectAssistance.substring(expectAssistance.length - getString(R.string.label_register_service_package).length, expectAssistance.length)
        registerModel.expectAssistance = expectAssistance

        registerModel.departureDateFrom = et_departure_date?.text.toString() + " " + et_departure_time?.text.toString()
        registerModel.pickupPoint = et_pickup_place?.text
        registerModel.visitPlaces = et_visit_place?.text

        var additionalServices = tv_register_service_more?.text.toString()
        additionalServices = additionalServices.substring(additionalServices.length - getString(R.string.label_register_service_package_additional).length, additionalServices.length)
        registerModel.additionalServices = additionalServices

        ApplicationSingleton.getInstance().registerModel = registerModel
        return registerModel
    }


    private fun executeRegister() {
        if (ll_parent == null) {
            return
        }
        val count = ll_parent?.childCount
        for (i in 0 until count!!) {
            val v = ll_parent?.getChildAt(i)
            if (v is EditTextViewLayout) {
                if (TextUtils.isEmpty(v.text)) {
                    showWarningDialog(R.string.label_input_info)
                    return
                }
            }
        }
        registerApi()
    }

    companion object {

        fun newInstance(): RegisterHourlyServiceFragment {
            return RegisterHourlyServiceFragment()
        }
    }
}
