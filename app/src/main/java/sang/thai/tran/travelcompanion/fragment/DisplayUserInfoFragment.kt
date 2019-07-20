package sang.thai.tran.travelcompanion.fragment

import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import butterknife.OnClick
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.fragment_display_info.*
import sang.thai.tran.travelcompanion.R
import sang.thai.tran.travelcompanion.activity.LoginActivity
import sang.thai.tran.travelcompanion.activity.MainActivity
import sang.thai.tran.travelcompanion.utils.AppConstant.*
import sang.thai.tran.travelcompanion.utils.ApplicationSingleton
import sang.thai.tran.travelcompanion.utils.DialogUtils.showAlertDialog

class DisplayUserInfoFragment : BaseFragment() {

    private var type: String? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_display_info, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        tv_update_info.setOnClickListener { onClickUpdateInfo() }
        tv_register_flight_or_guide.setOnClickListener { onClickRegisterFlight() }
        tv_register_well.setOnClickListener { onClickRegisterGuide() }
        tv_done.setOnClickListener { onDone() }
    }

    override fun onResume() {
        super.onResume()
        initView()
    }

    private fun initView() {
        val intent = arguments
        if (intent != null) {
            var title = intent.getString(MainActivity.WORK_TITLE_EXTRA)
            type = intent.getString(MainActivity.USER_TYPE_EXTRA)
            if (!TextUtils.isEmpty(type)) {
                when (type) {
                    SUPPORT_COMPANION -> {
                        //                        title = getResources().getStringArray(R.array.list_item)[0];
                        title = getString(R.string.label_support)
                        tv_register_well!!.visibility = View.GONE
                    }
                    SUPPORT_COMPANION_GUIDE -> {
                        //                        title = getResources().getStringArray(R.array.list_item)[1];
                        title = getString(R.string.label_support_guide)
                        tv_register_well!!.visibility = View.GONE
                        tv_register_flight_or_guide!!.text = getString(R.string.label_register_guide)
                    }
                    SUPPORT_COMPANION_WELL -> {
                        title = getString(R.string.label_support_well)
                        //                        title = getResources().getStringArray(R.array.list_item)[2];
                        tv_register_flight_or_guide!!.text = getString(R.string.label_register_flight_companion_domestic)
                        tv_register_well!!.text = getString(R.string.label_register_well_trained_companion)
                    }
                    NEED_SUPPORT_COMPANION -> {
                        title = getString(R.string.label_need_support)
                        tv_register_well!!.visibility = View.GONE
                        ll_final_button!!.visibility = View.GONE
                    }
                    NEED_SUPPORT_COMPANION_GUIDE -> {
                        title = getString(R.string.label_need_support_guide)
                        tv_register_well!!.visibility = View.GONE
                        ll_final_button!!.visibility = View.GONE
                        tv_register_flight_or_guide!!.text = getString(R.string.label_register_guide)
                    }
                    NEED_SUPPORT_COMPANION_WELL -> {
                        title = getString(R.string.label_need_support_well)
                        ll_final_button!!.visibility = View.GONE
                        tv_register_flight_or_guide!!.text = getString(R.string.label_register_flight_companion)
                        tv_register_well!!.text = getString(R.string.label_register_for_hour)
                    }
                }
            }

            tv_update_info!!.text = getString(R.string.label_update_info)
            tv_work_title!!.text = title
        }
        updateUserInfo()
    }

    private fun updateUserInfo() {
        val userInfo = ApplicationSingleton.getInstance().userInfo
        if (userInfo != null) {
            et_full_name!!.text = userInfo.first_Name
            et_year_of_birth!!.text = userInfo.birthday
            et_gender!!.text = userInfo.gender
            et_phone!!.text = userInfo.phone
            et_email!!.text = userInfo.email
            if (activity != null)
                Glide.with(activity!!).load(userInfo.image).into(rlAdminAvatar!!)
        }
    }

    @OnClick(R.id.tv_update_info)
    fun onClickUpdateInfo() {
        val intent = Intent(activity, LoginActivity::class.java)
        intent.putExtra(MainActivity.UPDATE_INFO, true)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK)
        startActivity(intent)
    }

    @OnClick(R.id.tv_register_flight_or_guide)
    fun onClickRegisterFlight() {
        when (type) {
            SUPPORT_COMPANION -> (activity as MainActivity).registerFlight(false)
            SUPPORT_COMPANION_GUIDE -> (activity as MainActivity).registerGuide(false)
            SUPPORT_COMPANION_WELL -> (activity as MainActivity).registerFlight(false)
            NEED_SUPPORT_COMPANION -> (activity as MainActivity).registerFlight(true)
            NEED_SUPPORT_COMPANION_GUIDE -> (activity as MainActivity).registerGuide(true)
            NEED_SUPPORT_COMPANION_WELL -> (activity as MainActivity).registerFlight(true)
        }
    }

    @OnClick(R.id.tv_register_well)
    fun onClickRegisterGuide() {
        (activity as MainActivity).registerWell(type == NEED_SUPPORT_COMPANION_WELL)
    }

    @OnClick(R.id.tv_done)
    fun onDone() {
        showAlertDialog(activity, getString(R.string.msg_finish_dialog)) { dialog, which -> dialog.dismiss() }
    }

    companion object {
        fun newInstance(bundle: Bundle): DisplayUserInfoFragment {
            val infoRegisterFragment = DisplayUserInfoFragment()
            infoRegisterFragment.arguments = bundle
            return infoRegisterFragment
        }
    }

}
