package sang.thai.tran.travelcompanion.activity

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import sang.thai.tran.travelcompanion.R
import sang.thai.tran.travelcompanion.fragment.*

class MainActivity : BaseActivity() {

    override val getChildClass: Class<*>
        get() = MainActivity::class.java


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        showInfo()
    }

    private fun showInfo() {
        val intent = intent
        if (intent != null) {
            val bundle = Bundle()
            bundle.putString(USER_TYPE_EXTRA, intent.getStringExtra(USER_TYPE_EXTRA))
            bundle.putString(WORK_TITLE_EXTRA, intent.getStringExtra(WORK_TITLE_EXTRA))
            replaceFragment(R.id.fl_content, DisplayUserInfoFragment.newInstance(bundle))
        }
    }

    fun registerFlight(isNeed: Boolean) {
        replaceFragment(R.id.fl_content, if (isNeed) RegisterFlightNeedFragment.newInstance() else RegisterFlightFragment.newInstance(isNeed))
    }

    fun registerGuide(isNeed: Boolean) {
        replaceFragment(R.id.fl_content, if (isNeed) RegisterGuideNeedFragment.newInstance() else RegisterGuideFragment.newInstance())
    }

    fun registerWell(isNeed: Boolean) {
        replaceFragment(R.id.fl_content, if (isNeed) RegisterHourlyServiceFragment.newInstance() else RegisterWellCompanionFragment.newInstance())
    }

    fun finishRegister() {
        replaceFragment(R.id.fl_content, RegisterFinishFragment.newInstance(true))
    }

    fun showListNeedSupport(type: String?) {
        replaceFragment(R.id.fl_content, ListOfNeedSupportFragment.newInstance(type!!))
    }

    companion object {
        const val TAG = "MainActivity"

        const val WORK_TITLE_EXTRA = "WORK_TITLE_EXTRA"
        const val USER_TYPE_EXTRA = "USER_TYPE_EXTRA"
        const val UPDATE_INFO = "UPDATE_INFO"
        const val UPDATE_AVATAR = "UPDATE_AVATAR"
        const val NEED_SUPPORT = "NEED_SUPPORT"

        fun startMainActivity(activity: Activity?, userType: String) {
            val intent = Intent(activity, MainActivity::class.java)
            intent.putExtra(USER_TYPE_EXTRA, userType)
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK)
            activity?.startActivity(intent)
            activity?.finish()
        }
    }
}
