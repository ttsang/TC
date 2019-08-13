package sang.thai.tran.travelcompanion.adapter

import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import sang.thai.tran.travelcompanion.fragment.DisplayUserInfoFragment
import sang.thai.tran.travelcompanion.fragment.ListOfNeedSupportFragment

class DemoFragmentAdapter(fm: FragmentManager, private val type : String) : FragmentPagerAdapter(fm) {

    override fun getItem(position: Int): Fragment {
        when (position) {
            0 -> return ListOfNeedSupportFragment.newInstance(type)
            1 -> return DisplayUserInfoFragment.newInstance()
            else -> return Fragment()
        }
    }

    override fun getCount(): Int {
        return 2
    }

    override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
        super.destroyItem(container, position, `object`)
    }


}
