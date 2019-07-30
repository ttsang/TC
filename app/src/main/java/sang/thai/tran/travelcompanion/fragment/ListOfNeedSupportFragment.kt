package sang.thai.tran.travelcompanion.fragment

import android.os.Bundle
import android.text.TextUtils
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import butterknife.OnClick
import kotlinx.android.synthetic.main.fragment_need_suport_list.*
import kotlinx.android.synthetic.main.fragment_register_flight.*
import sang.thai.tran.travelcompanion.R
import sang.thai.tran.travelcompanion.activity.MainActivity
import sang.thai.tran.travelcompanion.adapter.NeedSupportAdapter
import sang.thai.tran.travelcompanion.model.RegisterModel
import sang.thai.tran.travelcompanion.model.Response
import sang.thai.tran.travelcompanion.retrofit.BaseObserver
import sang.thai.tran.travelcompanion.retrofit.HttpRetrofitClientBase
import sang.thai.tran.travelcompanion.utils.AppConstant
import sang.thai.tran.travelcompanion.utils.AppUtils.openTimePicker
import sang.thai.tran.travelcompanion.utils.ApplicationSingleton
import sang.thai.tran.travelcompanion.utils.DialogUtils
import sang.thai.tran.travelcompanion.utils.Log

open class ListOfNeedSupportFragment : BaseFragment() {

    val tokenText = "69KAOXdQ/5pVvSMybmRJJLLM4jBuzHfrchd8blXA8OpRP4ROSI5LL5Bjwuoz9qNser4CAqiR8jrUZPsLKusIZF7XO9CYsWnpC4ns+rUsqNfBJvcprWNHvS536NOhoPnh";
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        showProgressDialog()
        HttpRetrofitClientBase.getInstance().executeGet(AppConstant.API_GET_LIST_POST,
                ApplicationSingleton.getInstance().token, object : BaseObserver<Response>(true) {
            override fun onSuccess(result: Response, response: String) {
                hideProgressDialog()
                if (activity == null) {
                    return
                }
                if (result.statusCode == AppConstant.SUCCESS_CODE) {
                    Log.d("Sang", "response result.result?.listNeedSupport : ${result.result?.data?.list}")
                    activity?.runOnUiThread { getList(result.result?.data?.list!!)      }
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
//        val list = ArrayList<RegisterModel>(3)
//        list.add(RegisterModel())
//        list.add(RegisterModel())
//        getList(list)
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

    fun getList( locationInfoList : List<RegisterModel>) {
        rv_need_support_list.visibility = View.VISIBLE
        val layoutManager = LinearLayoutManager(activity)
        rv_need_support_list.layoutManager = layoutManager
        val needSupportAdapter = NeedSupportAdapter(activity, locationInfoList)
//        needSupportAdapter.setItemInfoList(locationInfoList)
        rv_need_support_list.adapter = needSupportAdapter
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
