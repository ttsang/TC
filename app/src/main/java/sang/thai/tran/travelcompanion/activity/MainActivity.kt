package sang.thai.tran.travelcompanion.activity

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.viewpager.widget.ViewPager
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.internal.NavigationMenuView
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.layout_action_bar.*
import sang.thai.tran.travelcompanion.R
import sang.thai.tran.travelcompanion.adapter.DemoFragmentAdapter
import sang.thai.tran.travelcompanion.fragment.*
import sang.thai.tran.travelcompanion.utils.AppConstant
import sang.thai.tran.travelcompanion.utils.ApplicationSingleton


class MainActivity : BaseActivity(), BottomNavigationView.OnNavigationItemSelectedListener {

    override val getChildClass: Class<*>
        get() = MainActivity::class.java

    override val layoutId: Int
        get() = R.layout.activity_main

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        tv_log_out.visibility = View.VISIBLE
        showInfo()
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.navigation_home -> {
                viewpager.currentItem = 0
                true
            }
            R.id.navigation_dashboard -> {
                viewpager.currentItem = 1
                true
            }
            else -> false
        }

    }

    private fun showInfo() {
        when (val type = ApplicationSingleton.getInstance().userType) {
            AppConstant.SUPPORT_COMPANION ,
            AppConstant.SUPPORT_COMPANION_GUIDE,
            AppConstant.SUPPORT_COMPANION_WELL -> {
                showBottomView()
                nav_view.setOnNavigationItemSelectedListener(this)

                val adapterViewPager = DemoFragmentAdapter(supportFragmentManager, type)
                viewpager.adapter = adapterViewPager
                viewpager.currentItem = 0
                viewpager.offscreenPageLimit = 1
                viewpager.addOnPageChangeListener(object : ViewPager.OnPageChangeListener {
                    override fun onPageScrollStateChanged(state: Int) {}

                    override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {}

                    override fun onPageSelected(position: Int) {
                        nav_view.menu.getItem(position).isChecked = true
                    }
                })
                onNavigationItemSelected(nav_view.menu.findItem(R.id.navigation_home));
            }
            AppConstant.NEED_SUPPORT_COMPANION,
            AppConstant.NEED_SUPPORT_COMPANION_GUIDE ,
            AppConstant.NEED_SUPPORT_COMPANION_WELL -> {
                hideBottomView()
                replaceFragment(R.id.fl_content, DisplayUserInfoFragment.newInstance())
            }
        }

    }

    fun registerFlight(isNeed: Boolean) {
        hideBottomView()
        replaceFragment(R.id.fl_content, if (isNeed) RegisterFlightNeedFragment.newInstance() else RegisterFlightFragment.newInstance(isNeed))
    }

    fun registerGuide(isNeed: Boolean) {
        hideBottomView()
        replaceFragment(R.id.fl_content, if (isNeed) RegisterGuideNeedFragment.newInstance() else RegisterGuideFragment.newInstance())
    }

    fun registerWell(isNeed: Boolean) {
        hideBottomView()
        replaceFragment(R.id.fl_content, if (isNeed) RegisterHourlyServiceFragment.newInstance() else RegisterWellCompanionFragment.newInstance())
    }


    fun finishRegister() {
        hideBottomView()
        replaceFragment(R.id.fl_content, RegisterFinishFragment.newInstance(true))
    }

    fun showListNeedSupport(type: String?) {
        hideBottomView()
        replaceFragment(R.id.fl_content, ListOfNeedSupportFragment.newInstance(type!!))
    }

    private fun hideBottomView() {
        nav_view.visibility = View.GONE
        fl_content.visibility = View.VISIBLE
        viewpager.visibility = View.GONE
    }


    private fun showBottomView() {
        nav_view.visibility = View.VISIBLE
        fl_content.visibility = View.GONE
        viewpager.visibility = View.VISIBLE
    }

    companion object {

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

    override fun onBackPressed() {
//        super.onBackPressed()
        if (supportFragmentManager.backStackEntryCount == 0) {
            startActivityMain()
        } else {
            showBottomView()
            supportFragmentManager.popBackStack()
        }
    }

    /**
     * Restart this
     */
    private fun startActivityMain() {
        val intent = Intent()
        intent.action = Intent.ACTION_MAIN
        intent.addCategory(Intent.CATEGORY_HOME)
        startActivity(intent)
    }

}


