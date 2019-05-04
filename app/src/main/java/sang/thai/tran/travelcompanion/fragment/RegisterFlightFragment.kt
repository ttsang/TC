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
//import kotlinx.android.synthetic.main.fragment_register_flight_need.*
import sang.thai.tran.travelcompanion.R
import sang.thai.tran.travelcompanion.activity.MainActivity
import sang.thai.tran.travelcompanion.utils.AppUtils.openDatePicker
import sang.thai.tran.travelcompanion.utils.AppUtils.openTimePicker

open class RegisterFlightFragment : BaseFragment() {

//    @BindView(R.id.email_sign_in_button)
//    internal var email_sign_in_button: Button? = null
//
//    @BindView(R.id.et_date)
//    internal var et_date: EditTextViewLayout? = null
//
//    @BindView(R.id.et_hour)
//    internal var et_hour: EditTextViewLayout? = null
//
//    @BindView(R.id.et_flight_number)
//    internal var et_flight_number: EditTextViewLayout? = null
//
//    @BindView(R.id.et_airline)
//    internal var et_airline: EditTextViewLayout? = null
//
//    @BindView(R.id.et_airport_departure)
//    internal var et_airport_departure: EditTextViewLayout? = null
//
//    @BindView(R.id.et_arrival_airport)
//    internal var et_arrival_airport: EditTextViewLayout? = null
//
//    @BindView(R.id.iv_card_id)
//    internal var iv_card_id: ImageView? = null
//
//    @BindView(R.id.tv_card_id)
//    internal var tv_card_id: TextView? = null
//
//    @BindView(R.id.iv_flight_ticket_id)
//    internal var iv_flight_ticket_id: ImageView? = null
//
//    @BindView(R.id.tv_flight_ticket_id)
//    internal var tv_flight_ticket_id: TextView? = null

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        et_date!!.editText.onFocusChangeListener = View.OnFocusChangeListener { v, hasFocus ->
            if (hasFocus) {
                openDepartureDate()
            }
        }
        et_date.setOnClickListener { openDepartureDate() }

        if (et_hour != null) {
            et_hour!!.editText.onFocusChangeListener = View.OnFocusChangeListener { v, hasFocus ->
                if (hasFocus) {
                    openDepartureTime()
                }
            }
            et_hour.setOnClickListener { openDepartureTime() }
        }
        email_sign_in_button.setOnClickListener {
            (activity as MainActivity).finishRegister()
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

    @OnClick(R.id.email_sign_in_button)
    fun register() {
        (activity as MainActivity).finishRegister()
    }

    fun openDepartureDate() {
        if (activity == null) {
            return
        }
        openDatePicker(activity, et_date)
    }

    @Optional
    @OnClick(R.id.et_hour)
    fun openDepartureTime() {
        if (activity == null) {
            return
        }
        openTimePicker(activity, et_hour)
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

}
