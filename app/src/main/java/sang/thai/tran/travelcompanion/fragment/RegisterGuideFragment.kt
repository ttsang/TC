package sang.thai.tran.travelcompanion.fragment

import android.graphics.Color
import android.os.Bundle
import androidx.appcompat.widget.LinearLayoutCompat
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.nj.imagepicker.ImagePicker
import com.nj.imagepicker.listener.ImageResultListener
import com.nj.imagepicker.utils.DialogConfiguration

import butterknife.OnClick
import kotlinx.android.synthetic.main.fragment_register_guide.*
import sang.thai.tran.travelcompanion.R
import sang.thai.tran.travelcompanion.activity.MainActivity

class RegisterGuideFragment : BaseFragment() {

//    @BindView(R.id.email_sign_in_button)
//    internal var email_sign_in_button: Button? = null
//
//    @BindView(R.id.et_guide_type)
//    internal var et_guide_type: EditTextViewLayout? = null
//
//    @BindView(R.id.et_city)
//    internal var et_city: EditTextViewLayout? = null
//
//    @BindView(R.id.et_driving_licence)
//    internal var et_driving_licence: EditTextViewLayout? = null
//
//    @BindView(R.id.et_country)
//    internal var et_country: EditTextViewLayout? = null
//
//    @BindView(R.id.et_know_destination)
//    internal var et_know_destination: EditTextViewLayout? = null
//
//    @BindView(R.id.et_fluent)
//    internal var et_fluent: EditTextViewLayout? = null
//
//    @BindView(R.id.et_free_time)
//    internal var et_free_time: EditTextViewLayout? = null
//
//    @BindView(R.id.et_register_place)
//    internal var et_register_place: EditTextViewLayout? = null
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
//
//    @BindView(R.id.iv_driving_licence)
//    internal var iv_driving_licence: ImageView? = null
//
//    @BindView(R.id.tv_driving_licence)
//    internal var tv_driving_licence: TextView? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val layout = R.layout.fragment_register_guide
        val view = inflater.inflate(layout, container, false)
//        ButterKnife.bind(this, view)

        return view
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        email_sign_in_button!!.setOnClickListener { (activity as MainActivity).finishRegister() }
        fl_card_id!!.setOnClickListener { uploadCardId() }
        fl_flight_ticket_id!!.setOnClickListener { uploadFlightTicket() }
        fl_driving_licence!!.setOnClickListener { uploadDrivingLicence() }
    }


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
            tv_card_id!!.visibility = View.GONE
            iv_card_id!!.setImageBitmap(imageResult.bitmap)
        }).show(fragmentManager!!)
    }

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
            iv_flight_ticket_id!!.setImageBitmap(imageResult.bitmap)
            tv_flight_ticket_id!!.visibility = View.GONE
        }).show(fragmentManager!!)
    }

    @OnClick(R.id.fl_driving_licence)
    fun uploadDrivingLicence() {
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

    companion object {

        fun newInstance(): RegisterGuideFragment {
            return RegisterGuideFragment()
        }
    }
}
