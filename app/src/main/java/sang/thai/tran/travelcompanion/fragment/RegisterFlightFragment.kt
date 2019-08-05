package sang.thai.tran.travelcompanion.fragment

import android.graphics.Color
import android.os.Bundle
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
import sang.thai.tran.travelcompanion.model.FlightJobModel
import sang.thai.tran.travelcompanion.model.RegisterModel
import sang.thai.tran.travelcompanion.utils.AppUtils.openDatePicker
import sang.thai.tran.travelcompanion.utils.AppUtils.openTimePicker
import sang.thai.tran.travelcompanion.utils.ApplicationSingleton

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
//            registerApi()
            ApplicationSingleton.getInstance().userInfo.flightJobModel = createFlightJobModel()
            (activity as MainActivity).onBackPressed()
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

    override fun createRegisterFlight(): RegisterModel {
        var registerModel = ApplicationSingleton.getInstance().registerModel
        if (registerModel == null) {
            registerModel = RegisterModel()
        }

        registerModel.departureDateFrom = et_departure_date?.text.toString() + " " + et_departure_hour?.text.toString()
        registerModel.id = ApplicationSingleton.getInstance().userInfo.code
        registerModel.airlineOption = et_airline?.text
        registerModel.flightNumber = et_flight_number?.text
        registerModel.departureAirport = et_airport_departure?.text
        registerModel.arrivalAirport = et_arrival_airport?.text
        addMoreService(registerModel)
        return registerModel
    }

    private fun createFlightJobModel() : FlightJobModel {
        val registerModel = FlightJobModel()
        registerModel.ticketDeprtDate = et_departure_date?.text.toString() + " " + et_departure_hour?.text.toString()
        registerModel.airline = et_airline?.text
        registerModel.flightNumber = et_flight_number?.text
        registerModel.ticketDeprtAirport = et_airport_departure?.text
        registerModel.ticketArrAirport = et_arrival_airport?.text
        registerModel.ticketImage = "http"
        return registerModel
    }

    protected open fun addMoreService(registerModel: RegisterModel) {

    }
}
