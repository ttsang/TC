package sang.thai.tran.travelcompanion.fragment

import android.os.Bundle
import android.view.View
import butterknife.OnClick
import kotlinx.android.synthetic.main.fragment_register_flight_need.*
import sang.thai.tran.travelcompanion.R
import sang.thai.tran.travelcompanion.model.RegisterModel

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

    override fun addMoreService(registerModel : RegisterModel) {
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
        activity?.resources?.getTextArray(R.array.service_pkg)?.let { showOptionDialog(tv_register_service!!, getString(R.string.label_register_service_package), it) }
    }

    @OnClick(R.id.tv_register_service_more)
    fun registerServiceMore() {
        if (activity == null) {
            return
        }
        activity?.resources?.getTextArray(servicePkgMoreId)?.let { showOptionDialog(tv_register_service_more!!, getString(R.string.label_register_service_package_additional), it) }
    }

    companion object {
        fun newInstance(): RegisterFlightNeedFragment {
            return RegisterFlightNeedFragment()
        }
    }


}
