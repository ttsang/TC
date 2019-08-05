package sang.thai.tran.travelcompanion.fragment

import android.os.Bundle
import android.text.TextUtils
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import butterknife.OnClick
import kotlinx.android.synthetic.main.fragment_display_info.*
import kotlinx.android.synthetic.main.fragment_display_info.et_email
import kotlinx.android.synthetic.main.fragment_forgot_pass.*
import kotlinx.android.synthetic.main.fragment_need_suport_list.*
import kotlinx.android.synthetic.main.fragment_register_flight.*
import sang.thai.tran.travelcompanion.R
import sang.thai.tran.travelcompanion.activity.MainActivity
import sang.thai.tran.travelcompanion.adapter.NeedSupportAdapter
import sang.thai.tran.travelcompanion.model.FlightJobModel
import sang.thai.tran.travelcompanion.model.RegisterModel
import sang.thai.tran.travelcompanion.model.Response
import sang.thai.tran.travelcompanion.model.holder.ChoiceHolder
import sang.thai.tran.travelcompanion.retrofit.BaseObserver
import sang.thai.tran.travelcompanion.retrofit.HttpRetrofitClientBase
import sang.thai.tran.travelcompanion.utils.AppConstant
import sang.thai.tran.travelcompanion.utils.AppConstant.*
import sang.thai.tran.travelcompanion.utils.AppUtils.openTimePicker
import sang.thai.tran.travelcompanion.utils.ApplicationSingleton
import sang.thai.tran.travelcompanion.utils.DialogUtils
import sang.thai.tran.travelcompanion.utils.Log
import java.util.HashMap

open class ListOfNeedSupportFragment : BaseFragment() {

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        showProgressDialog()
        HttpRetrofitClientBase.getInstance().executeGet(API_GET_LIST_POST,
                ApplicationSingleton.getInstance().token, object : BaseObserver<Response>(true) {
            override fun onSuccess(result: Response, response: String) {
                hideProgressDialog()
                if (activity == null) {
                    return
                }
                if (result.statusCode == AppConstant.SUCCESS_CODE) {
                    Log.d("Sang", "response result.result?.listNeedSupport : ${result.result?.data?.list}")
                    activity?.runOnUiThread { getList(result.result?.data?.list!!) }
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

    override fun layoutId(): Int {
        return R.layout.fragment_need_suport_list
    }


    @OnClick(R.id.et_departure_hour)
    fun openDepartureTime() {
        if (activity == null) {
            return
        }
        openTimePicker(activity, et_departure_hour)
    }

    fun getList(locationInfoList: List<RegisterModel>) {
        var isOnFlight = false
        var filterInfoList: List<RegisterModel> = mutableListOf()
        when (ApplicationSingleton.getInstance().userType) {
            SUPPORT_COMPANION -> {
                isOnFlight = true
                filterInfoList = locationInfoList.filter { TYPE_ON_FLIGHT.equals(it.type) }
            }
            SUPPORT_COMPANION_GUIDE -> {
                isOnFlight = false
                filterInfoList = locationInfoList.filter { TYPE_GUIDES_TO_PLACES.equals(it.type) }
            }
            SUPPORT_COMPANION_WELL -> {
                isOnFlight = false
                filterInfoList = locationInfoList.filter { TYPE_WELL_TRAINED_HELPS.equals(it.type) }
            }
        }
        rv_need_support_list.visibility = View.VISIBLE
        val layoutManager = LinearLayoutManager(activity)
        rv_need_support_list.layoutManager = layoutManager
        val needSupportAdapter = NeedSupportAdapter(activity, filterInfoList, ChoiceHolder.OnClickReceiver { itemOptionModel, position -> receiveJob(itemOptionModel, isOnFlight) })
        rv_need_support_list.adapter = needSupportAdapter
    }

    private fun receiveJob(itemOptionModel: RegisterModel, onFlight: Boolean) {
        if (onFlight) {
            if (ApplicationSingleton.getInstance().userInfo.flightJobModel == null) {
                DialogUtils.showAlertDialog(activity, "Vui lòng đăng ký thông tin máy bay") { dialog, _ ->
                    run {
                        dialog.dismiss()
                        (activity as MainActivity).registerFlight(false)
                    }
                }
                return
            }
            val flightJobModel: FlightJobModel = ApplicationSingleton.getInstance().userInfo.flightJobModel
            flightJobModel.jobCode = itemOptionModel.code
            showProgressDialog()
            HttpRetrofitClientBase.getInstance().executeTakeJobOnFlightPost(API_TAKE_THE_ON_FLIGHT_JOB,
                    ApplicationSingleton.getInstance().token, flightJobModel, object : BaseObserver<Response>(true) {
                override fun onSuccess(result: Response, response: String) {
                    hideProgressDialog()
                    if (activity == null) {
                        return
                    }
                    if (result.statusCode == SUCCESS_CODE) {
                        Log.d("Sang", "response result.result?.receiveJob onFlight: ${result.result?.data?.list}")
//                    activity?.runOnUiThread { getList(result.result?.data?.list!!) }
                        (activity as MainActivity).onBackPressed()
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
            return
        }
        val data = HashMap<String, String>()
        data["accessToken"] = ApplicationSingleton.getInstance().token
        data["jobCode"] = itemOptionModel.code
        showProgressDialog()
        HttpRetrofitClientBase.getInstance().executePost(API_TAKE_THE_JOB,
                data, object : BaseObserver<Response>(true) {
            override fun onSuccess(result: Response, response: String) {
                hideProgressDialog()
                if (activity == null) {
                    return
                }
                if (result.statusCode == SUCCESS_CODE) {
                    Log.d("Sang", "response result.result?.receiveJob : ${result.result?.data?.list}")
//                    activity?.runOnUiThread { getList(result.result?.data?.list!!) }
                    (activity as MainActivity).onBackPressed()
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

    companion object {
        fun newInstance(type: String): ListOfNeedSupportFragment {
            val infoRegisterFragment = ListOfNeedSupportFragment()
            val bundle = Bundle()
            bundle.putString(MainActivity.NEED_SUPPORT, type)
            infoRegisterFragment.arguments = bundle
            return infoRegisterFragment
        }
    }

}
