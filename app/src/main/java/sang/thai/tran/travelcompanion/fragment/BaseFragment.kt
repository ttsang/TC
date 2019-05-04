package sang.thai.tran.travelcompanion.fragment

import android.graphics.Color
import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.appcompat.app.AlertDialog
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.widget.LinearLayoutCompat
import butterknife.ButterKnife
import com.nj.imagepicker.ImagePicker
import com.nj.imagepicker.listener.ImageResultListener
import com.nj.imagepicker.utils.DialogConfiguration
import kotlinx.android.synthetic.main.fragment_register_guide.*
import sang.thai.tran.travelcompanion.R
import sang.thai.tran.travelcompanion.activity.MainActivity
import sang.thai.tran.travelcompanion.model.UserInfo
import sang.thai.tran.travelcompanion.utils.AppConstant.*
import sang.thai.tran.travelcompanion.utils.AppUtils.listToString
import sang.thai.tran.travelcompanion.utils.ApplicationSingleton
import sang.thai.tran.travelcompanion.utils.DialogUtils
import sang.thai.tran.travelcompanion.utils.DialogUtils.onCreateOptionDialog
import java.util.*

open class BaseFragment : Fragment() {
    private var progressDialog: AlertDialog? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view =  inflater.inflate(layoutId(), container, false)
        ButterKnife.bind(view)
        return view;
    }

    protected open fun layoutId(): Int {
        return R.layout.activity_login
    }

    protected fun showOptionDialog(tv_register_service_more: TextView, title: String, option: Array<CharSequence>) {
        val tmp = ArrayList<String>()
        onCreateOptionDialog(activity,
                title,
                option,
                tmp
        ) { dialog, which ->
            val result = title + listToString(tmp)
            tv_register_service_more.text = result
            dialog.dismiss()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        hideProgressDialog()
    }

    protected fun showProgressDialog() {
        if (activity != null)
            activity!!.runOnUiThread {
                progressDialog = DialogUtils.showProgressDialog(activity)
                if (progressDialog != null) {
                    progressDialog!!.show()
                }
            }
    }

    protected fun hideProgressDialog() {
        if (activity != null) {
            activity!!.runOnUiThread {
                if (progressDialog != null) {
                    progressDialog!!.dismiss()
                }
            }
        }
    }

    protected fun startMain(userInfo: UserInfo) {
        ApplicationSingleton.getInstance().userInfo = userInfo
        var parent = "0"
        if (userInfo.type != null && userInfo.type == POSTER) {
            parent = "1"
        }
        var child = "0"
        if (userInfo.job_Type != null) {
            when (userInfo.job_Type) {
                COMPANION_GUIDE -> child = "1"
                WELL_TRAINED_COMPANION -> child = "2"
            }
        }
        MainActivity.startMainActivity(activity!!, "", parent + child)
    }

    fun uploadImage() {
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
}
