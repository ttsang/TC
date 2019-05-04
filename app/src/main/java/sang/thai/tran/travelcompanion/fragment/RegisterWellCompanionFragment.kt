package sang.thai.tran.travelcompanion.fragment

import android.os.Bundle
import butterknife.OnClick
import kotlinx.android.synthetic.main.fragment_register_flight.*
import sang.thai.tran.travelcompanion.R

class RegisterWellCompanionFragment : BaseFragment() {

    override fun layoutId(): Int {
        return R.layout.fragment_register_well_companion
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        email_sign_in_button.setOnClickListener {
            openDepartureDate()
        }
    }

    @OnClick(R.id.email_sign_in_button)
    internal fun openDepartureDate() {
        if (activity == null) {
            return
        }
        activity!!.onBackPressed()
    }

    companion object {

        fun newInstance(): RegisterWellCompanionFragment {
            return RegisterWellCompanionFragment()
        }
    }
}
