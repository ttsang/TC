package sang.thai.tran.travelcompanion.fragment

import android.content.Context
import android.os.Bundle
import android.text.TextUtils
import android.view.inputmethod.InputMethodManager
import kotlinx.android.synthetic.main.fragment_register_flight_need.*
import kotlinx.android.synthetic.main.fragment_register_flight_need.et_departure_date
import kotlinx.android.synthetic.main.fragment_register_flight_need.ll_parent
import kotlinx.android.synthetic.main.fragment_register_flight_need.tv_register_service_more
import kotlinx.android.synthetic.main.fragment_register_hourly_service.*
import kotlinx.android.synthetic.main.layout_back_next.*
import sang.thai.tran.travelcompanion.R
import sang.thai.tran.travelcompanion.activity.BaseActivity
import sang.thai.tran.travelcompanion.activity.MainActivity
import sang.thai.tran.travelcompanion.model.RegisterModel
import sang.thai.tran.travelcompanion.model.Response
import sang.thai.tran.travelcompanion.retrofit.BaseObserver
import sang.thai.tran.travelcompanion.retrofit.HttpRetrofitClientBase
import sang.thai.tran.travelcompanion.utils.AppConstant
import sang.thai.tran.travelcompanion.utils.ApplicationSingleton
import sang.thai.tran.travelcompanion.utils.DialogUtils
import sang.thai.tran.travelcompanion.view.EditTextViewLayout


open class RegisterFlightNeedFragment : RegisterFlightFragment() {


    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        btn_next.setOnClickListener {
            executeRegister()
            getProfessionalRecords(AppConstant.API_GET_PROFESSIONAL_RECORD)
        }

        btn_back.setOnClickListener {
            createRegisterFlight()
            (activity as BaseActivity).onBackPressed()
        }

        fillData()
    }

    //    GET /api/SelectList/getProfessionalRecords
    private fun getProfessionalRecords(url: String) {
        if (activity == null || isMultiClicked()) {
            return
        }
        HttpRetrofitClientBase.getInstance().executeGet(url,
                ApplicationSingleton.getInstance().token, object : BaseObserver<Response>(true) {
            override fun onSuccess(result: Response, response: String) {
                hideProgressDialog()
                if (activity == null) {
                    return
                }
                if (result.statusCode == AppConstant.SUCCESS_CODE) {
                    ApplicationSingleton.getInstance().data = result.result?.data

                    activity?.runOnUiThread {
                        //                        showMultiChoiceDialog()
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
        val imm = activity?.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(tv_register_service_more?.windowToken, 0)
    }

    override fun getApiUrl(): String {
        return AppConstant.API_UPDATE_GUIDES_PLACE;
    }

    override fun addMoreService(registerModel: RegisterModel) {
        registerModel.departureDateFrom = et_departure_date?.text.toString() + " " + et_departure_hour?.text.toString()
        if (!TextUtils.isEmpty(et_kid_number.text))
            registerModel.childrenNumber = Integer.valueOf(et_kid_number.text)
        if (!TextUtils.isEmpty(et_elders_number.text))
            registerModel.elderlyNumber = Integer.valueOf(et_elders_number.text)
        if (!TextUtils.isEmpty(et_pregnant_number.text))
            registerModel.pregnantNumber = Integer.valueOf(et_pregnant_number.text)
        if (!TextUtils.isEmpty(et_middle_number.text))
            registerModel.middleAgeNumber = Integer.valueOf(et_middle_number.text)
        if (!TextUtils.isEmpty(et_disability_number.text))
            registerModel.disabilityNumber = Integer.valueOf(et_disability_number.text)
    }

    fun fillData() {
        val registerModel = ApplicationSingleton.getInstance().registerModel
        if (registerModel != null) {
            if (registerModel.childrenNumber > 0)
                et_kid_number.text = registerModel.childrenNumber.toString()
            if (registerModel.elderlyNumber > 0)
                et_elders_number.text = registerModel.elderlyNumber.toString()
            if (registerModel.pregnantNumber > 0)
                et_pregnant_number.text = registerModel.pregnantNumber.toString()
            if (registerModel.middleAgeNumber > 0)
                et_middle_number.text = registerModel.middleAgeNumber.toString()
            if (registerModel.disabilityNumber > 0)
                et_disability_number.text = registerModel.disabilityNumber.toString()
        }
    }

    override fun layoutId(): Int {
        return R.layout.fragment_register_flight_need
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
        (activity as MainActivity).registerWell(true)

    }

    override fun onPause() {
        val view = activity?.currentFocus
        view?.let { v ->
            val imm = activity?.getSystemService(Context.INPUT_METHOD_SERVICE) as? InputMethodManager
            imm?.let { it.hideSoftInputFromWindow(v.windowToken, 0) }
        }
        super.onPause()
    }
    companion object {
        fun newInstance(): RegisterFlightNeedFragment {
            return RegisterFlightNeedFragment()
        }
    }


}
