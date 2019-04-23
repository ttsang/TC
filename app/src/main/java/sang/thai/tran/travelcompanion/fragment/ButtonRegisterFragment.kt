package sang.thai.tran.travelcompanion.fragment

import android.os.Bundle
import android.util.DisplayMetrics
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ExpandableListView
import butterknife.ButterKnife
import com.google.gson.Gson
import sang.thai.tran.travelcompanion.R
import sang.thai.tran.travelcompanion.activity.MainActivity
import sang.thai.tran.travelcompanion.adapter.ExpandableListAdapter
import sang.thai.tran.travelcompanion.model.Response
import sang.thai.tran.travelcompanion.retrofit.BaseObserver
import sang.thai.tran.travelcompanion.retrofit.HttpRetrofitClientBase
import sang.thai.tran.travelcompanion.utils.AppConstant.*
import sang.thai.tran.travelcompanion.utils.ApplicationSingleton
import sang.thai.tran.travelcompanion.utils.DialogUtils
import java.util.*

class ButtonRegisterFragment : BaseFragment() {

    internal lateinit var listAdapter: ExpandableListAdapter
    internal lateinit var expListView: ExpandableListView
    internal lateinit var listDataHeader: Array<String>
    internal lateinit var listDataChild: HashMap<String, Array<String>>

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_button, container, false)
        ButterKnife.bind(this, view)

        expListView = view.findViewById(R.id.lvExp)
        expListView.setIndicatorBounds(0, 20)
        // setOnChildClickListener listener for child row click or song name click
        expListView.setOnChildClickListener { parent, v, groupPosition, childPosition, id ->
            //get the group header
            //display it or do something with it
            Log.d("Sang", "groupPosition: $groupPosition - childPosition: $childPosition")
            startUserInfo(groupPosition, childPosition)
            false
        }
        // setOnGroupClickListener listener for group Song List click
        expListView.setOnGroupClickListener { parent, v, groupPosition, id ->
            //get the group header

            //                if (parent.isGroupExpanded(groupPosition)) {
            ////                    expListView.setDividerHeight(20);
            //                    // Do your Staff
            //                } else {
            ////                    expListView.setDividerHeight(0);
            //                    // Expanded ,Do your Staff
            //
            //                }

            false
        }
        prepareListData()

        listAdapter = ExpandableListAdapter(activity, listDataHeader, listDataChild, expListView)

        // setting list adapter
        expListView.setAdapter(listAdapter)

        val metrics = DisplayMetrics()
        activity!!.windowManager.defaultDisplay.getMetrics(metrics)
        val width = metrics.widthPixels

        expListView.setIndicatorBounds(width - GetPixelFromDips(50f), width - GetPixelFromDips(10f))
        return view
    }

    private fun startUserInfo(groupPosition: Int, childPosition: Int) {
        val text = listDataChild[listDataHeader[groupPosition]]!![childPosition]
        Log.d("Sang", "text: $text")
        var type = POSTER
        if (groupPosition == 0) {
            type = RECEIVER
        }

        var job_type = ESCORTEE
        when (childPosition) {
            0 -> job_type = ESCORTEE
            1 -> job_type = COMPANION_GUIDE
            2 -> job_type = WELL_TRAINED_COMPANION
        }
        ApplicationSingleton.getInstance().userInfo.type = type
        ApplicationSingleton.getInstance().userInfo.job_Type = job_type
        executeRegister()
    }

    /*
     * Preparing the list data
     */
    private fun prepareListData() {
        listDataHeader = resources.getStringArray(R.array.list_header)
        listDataChild = HashMap()


        //        // Adding child data
        //        List<String> top250 = new ArrayList<>();
        //        top250.add("Trợ giúp đi cùng chuyến bay");
        //        top250.add("Chỉ dẫn hành trình tại điểm đến");
        //        top250.add("Trợ giúp người cao tuổi/ khuyết tật/ trẻ em");
        //
        //        List<String> nowShowing = new ArrayList<>();
        //        nowShowing.add("Trợ giúp đi cùng chuyến bay");
        //        nowShowing.add("Chỉ dẫn hành trình tại điểm đến");
        //        nowShowing.add("Trợ giúp người cao tuổi/ khuyết tật/ trẻ em");


        listDataChild[resources.getStringArray(R.array.list_header)[0]] = resources.getStringArray(R.array.list_item) // Header, Child data
        listDataChild[resources.getStringArray(R.array.list_header)[1]] = resources.getStringArray(R.array.list_item)
    }

    fun GetPixelFromDips(pixels: Float): Int {
        // Get the screen's density scale
        val scale = resources.displayMetrics.density
        // Convert the dps to pixels, based on density scale
        return (pixels * scale + 0.5f).toInt()
    }

    private fun executeRegister() {
        val userInfo = ApplicationSingleton.getInstance().userInfo
        val model = Gson().toJson(userInfo)
        showProgressDialog()
        val map = HashMap<String, String>()
        map["model"] = model
        Log.d("Sang", "model: $model")
        var url = "api/account/register"
        val isUpdate = arguments != null && arguments!!.getBoolean(MainActivity.UPDATE_INFO)
        if (isUpdate) {
            url = "api/account/update"
        }
        HttpRetrofitClientBase.getInstance().executePost(url, ApplicationSingleton.getInstance().userInfo, object : BaseObserver<Response>(true) {
            override fun onSuccess(response: Response, responseStr: String) {
                hideProgressDialog()
                if (activity == null) {
                    return
                }
                if (response.statusCode == SUCCESS_CODE) {
                    startMain(userInfo)
                } else {
                    activity!!.runOnUiThread { DialogUtils.showAlertDialog(activity, response.message) { dialog, which -> dialog.dismiss() } }
                }
            }

            override fun onFailure(e: Throwable, errorMsg: String) {
                hideProgressDialog()
            }
        })
    }

    companion object {

        fun newInstance(update: Boolean): ButtonRegisterFragment {
            val infoRegisterFragment = ButtonRegisterFragment()
            val bundle = Bundle()
            bundle.putBoolean(MainActivity.UPDATE_INFO, update)
            infoRegisterFragment.arguments = bundle
            return infoRegisterFragment
        }
    }
}
