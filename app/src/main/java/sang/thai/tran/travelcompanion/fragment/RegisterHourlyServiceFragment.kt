package sang.thai.tran.travelcompanion.fragment

import android.content.Context
import android.os.Bundle
import android.view.View
import android.view.inputmethod.InputMethodManager
import butterknife.OnClick
import kotlinx.android.synthetic.main.fragment_register_flight.*
import kotlinx.android.synthetic.main.fragment_register_hourly_service.*
import kotlinx.android.synthetic.main.fragment_register_hourly_service.email_sign_in_button
import kotlinx.android.synthetic.main.fragment_register_hourly_service.et_date
import sang.thai.tran.travelcompanion.R
import sang.thai.tran.travelcompanion.activity.MainActivity
import sang.thai.tran.travelcompanion.utils.AppUtils.openDatePicker
import sang.thai.tran.travelcompanion.utils.AppUtils.openTimePicker

class RegisterHourlyServiceFragment : BaseFragment() {

//    @BindView(R.id.tv_register_service)
//    internal var tv_register_service: TextView? = null
//
//    @BindView(R.id.tv_register_service_more)
//    internal var tv_register_service_more: TextView? = null
//
//    @BindView(R.id.et_date)
//    internal var et_date: EditTextViewLayout? = null
//
//    @BindView(R.id.et_from)
//    internal var et_from: EditTextViewLayout? = null
//
//    @BindView(R.id.et_to)
//    internal var et_to: EditTextViewLayout? = null

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        et_date!!.editText.onFocusChangeListener = View.OnFocusChangeListener { v, hasFocus ->
            if (hasFocus) {
                openDepartureDate()
            }
        }
        et_date.setOnClickListener { openDepartureDate() }

        et_from!!.editText.onFocusChangeListener = View.OnFocusChangeListener { v, hasFocus ->
            if (hasFocus) {
                openFromTime()
            }
        }
        et_from.setOnClickListener { openFromTime() }

        et_to!!.editText.onFocusChangeListener = View.OnFocusChangeListener { v, hasFocus ->
            if (hasFocus) {
                openToTime()
            }
        }
        et_to.setOnClickListener { openToTime() }

        tv_register_service_more!!.requestFocus()
        tv_register_service_more!!.onFocusChangeListener = View.OnFocusChangeListener { v, hasFocus ->
            if (hasFocus) {
                registerServiceMore()
                tv_register_service_more!!.clearFocus()
            } else {
                tv_register_service_more!!.clearFocus()
            }
        }
        tv_register_service_more.setOnClickListener { registerServiceMore() }

        tv_register_service!!.requestFocus()
        tv_register_service!!.onFocusChangeListener = View.OnFocusChangeListener { v, hasFocus ->
            if (hasFocus) {
                registerService()
                tv_register_service!!.clearFocus()
            } else {
                tv_register_service!!.clearFocus()
            }
        }
        tv_register_service_more.setOnClickListener { registerService() }

        email_sign_in_button.setOnClickListener {
            (activity as MainActivity).finishRegister()
        }
    }

    override fun layoutId(): Int {
        return R.layout.fragment_register_hourly_service
    }


    @OnClick(R.id.tv_register_service)
    fun registerService() {
        if (activity == null) {
            return
        }
        showOptionDialog(tv_register_service!!, getString(R.string.label_for), activity!!.resources.getTextArray(R.array.register_for_list))

        val imm = activity!!.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(tv_register_service!!.windowToken, 0)
    }

    @OnClick(R.id.tv_register_service_more)
    fun registerServiceMore() {
        if (activity == null) {
            return
        }
        showOptionDialog(tv_register_service_more!!, getString(R.string.label_register_service_package_additional), activity!!.resources.getTextArray(R.array.hourly_service_pkg))

        val imm = activity!!.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(tv_register_service_more!!.windowToken, 0)
    }

    @OnClick(R.id.et_date)
    fun openDepartureDate() {
        if (activity == null) {
            return
        }
        openDatePicker(activity, et_date)
    }

    @OnClick(R.id.et_from)
    fun openFromTime() {
        if (activity == null) {
            return
        }
        openTimePicker(activity, et_from)
    }

    @OnClick(R.id.et_to)
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

    companion object {

        fun newInstance(): RegisterHourlyServiceFragment {
            return RegisterHourlyServiceFragment()
        }
    }
}
