package sang.thai.tran.travelcompanion.fragment

import android.os.Bundle
import sang.thai.tran.travelcompanion.R

class RegisterGuideNeedFragment : RegisterFlightNeedFragment() {

    override val servicePkgMoreId: Int
        get() = R.array.service_pkg_list

//    override fun onActivityCreated(savedInstanceState: Bundle?) {
//        super.onActivityCreated(savedInstanceState)
//    }

    override fun layoutId(): Int {
        return R.layout.fragment_register_guide_need
    }

    companion object {
        fun newInstance(): RegisterGuideNeedFragment {
            return RegisterGuideNeedFragment()
        }
    }
}
