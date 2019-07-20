package sang.thai.tran.travelcompanion.fragment

//import kotlinx.android.synthetic.main.fragment_register_flight_need.*
import android.graphics.Color
import android.os.Bundle
import android.text.TextUtils
import android.view.View
import androidx.appcompat.widget.LinearLayoutCompat
import butterknife.OnClick
import butterknife.Optional
import com.nj.imagepicker.ImagePicker
import com.nj.imagepicker.listener.ImageResultListener
import com.nj.imagepicker.utils.DialogConfiguration
import kotlinx.android.synthetic.main.fragment_register_flight.*
import sang.thai.tran.travelcompanion.R
import sang.thai.tran.travelcompanion.activity.MainActivity
import sang.thai.tran.travelcompanion.model.RegisterModel
import sang.thai.tran.travelcompanion.model.Response
import sang.thai.tran.travelcompanion.retrofit.BaseObserver
import sang.thai.tran.travelcompanion.retrofit.HttpRetrofitClientBase
import sang.thai.tran.travelcompanion.utils.AppConstant
import sang.thai.tran.travelcompanion.utils.AppConstant.API_UPDATE_ON_FLIGHT
import sang.thai.tran.travelcompanion.utils.AppUtils.openDatePicker
import sang.thai.tran.travelcompanion.utils.AppUtils.openTimePicker
import sang.thai.tran.travelcompanion.utils.ApplicationSingleton
import sang.thai.tran.travelcompanion.utils.DialogUtils
import sang.thai.tran.travelcompanion.utils.Log

open class RegisterFlightFragment : BaseFragment() {

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        et_departure_date!!.editText.onFocusChangeListener = View.OnFocusChangeListener { v, hasFocus ->
            if (hasFocus) {
                openDepartureDate()
            }
        }
        et_departure_date.setOnClickListener { openDepartureDate() }

        if (et_departure_hour != null) {
            et_departure_hour!!.editText.onFocusChangeListener = View.OnFocusChangeListener { v, hasFocus ->
                if (hasFocus) {
                    openDepartureTime()
                }
            }
            et_departure_hour.setOnClickListener { openDepartureTime() }
        }
        email_sign_in_button.setOnClickListener {
//            (activity as MainActivity).finishRegister()
            register()
        }
        if (fl_card_id != null) {
            fl_card_id.setOnClickListener { uploadCardId() }
        }
        if (fl_flight_ticket_id != null)
            fl_flight_ticket_id.setOnClickListener { uploadFlightTicket() }
    }

    override fun layoutId(): Int {
        return R.layout.fragment_register_flight
    }

    fun register() {
        showProgressDialog()
        HttpRetrofitClientBase.getInstance().postRegisterFeature(
                API_UPDATE_ON_FLIGHT,
                ApplicationSingleton.getInstance().token,
                createRegisterFlight(),
                object : BaseObserver<Response>(true) {
                    override fun onSuccess(result: Response, response: String) {
                        hideProgressDialog()
                        if (activity == null) {
                            return
                        }
                        if (result.statusCode == AppConstant.SUCCESS_CODE) {
                            Log.d("Sang", "response: $response")
//                            if (result.result?.data != null)
//                                ApplicationSingleton.getInstance().token = result.result?.data?.token
//                            (activity as LoginActivity).replaceFragment(R.id.fl_content, ButtonRegisterFragment.newInstance(isUpdate, cameraFilePath), false)
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

    private fun openDepartureDate() {
        if (activity == null) {
            return
        }
        openDatePicker(activity, et_departure_date)
    }

    @Optional
    @OnClick(R.id.et_departure_hour)
    fun openDepartureTime() {
        if (activity == null) {
            return
        }
        openTimePicker(activity, et_departure_hour)
    }

    @Optional
    @OnClick(R.id.fl_card_id)
    fun uploadCardId() {
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
            if (tv_card_id != null) {
                tv_card_id!!.visibility = View.GONE
            }
            if (iv_card_id != null) {
                iv_card_id!!.setImageBitmap(imageResult.bitmap)
            }
        }).show(fragmentManager!!)
    }

    @Optional
    @OnClick(R.id.fl_flight_ticket_id)
    fun uploadFlightTicket() {
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
            if (iv_flight_ticket_id != null) {
                iv_flight_ticket_id!!.setImageBitmap(imageResult.bitmap)
            }
            if (tv_flight_ticket_id != null) {
                tv_flight_ticket_id!!.visibility = View.GONE
            }
        }).show(fragmentManager!!)
    }

    companion object {

        fun newInstance(update: Boolean): RegisterFlightFragment {
            val infoRegisterFragment = RegisterFlightFragment()
            val bundle = Bundle()
            bundle.putBoolean(MainActivity.NEED_SUPPORT, update)
            infoRegisterFragment.arguments = bundle
            return infoRegisterFragment
        }
    }

    private fun createRegisterFlight(): RegisterModel {
        var registerModel = ApplicationSingleton.getInstance().registerModel
        if (registerModel == null) {
            registerModel = RegisterModel()
        }

        registerModel.departureDateFrom = et_departure_date!!.text.toString() + " " + et_departure_hour!!.text.toString()
        registerModel.id = ApplicationSingleton.getInstance().userInfo.code
        registerModel.airlineOption = et_airline!!.text
        registerModel.flightNumber = et_flight_number!!.text
        registerModel.departureAirport = et_airport_departure!!.text
        registerModel.arrivalAirport = et_arrival_airport!!.text
//        registerModel.note = et_gender!!.text
//        registerModel.password = et_pass!!.text
//        if (!TextUtils.isEmpty(serverPath)) {
//            userInfo.image = serverPath
//        }
//        Log.d("Sang", "serverPath: " + serverPath)
        return registerModel
    }

}
