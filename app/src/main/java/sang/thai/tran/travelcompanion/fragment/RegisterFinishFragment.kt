package sang.thai.tran.travelcompanion.fragment

import android.os.Bundle
import android.view.KeyEvent
import android.view.View
import kotlinx.android.synthetic.main.fragment_register_finish.*
import sang.thai.tran.travelcompanion.R
import sang.thai.tran.travelcompanion.activity.MainActivity


@Suppress("IMPLICIT_BOXING_IN_IDENTITY_EQUALS")
class RegisterFinishFragment : BaseFragment() {

    override fun layoutId(): Int {
        return R.layout.fragment_register_finish
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        email_sign_in_button.setOnClickListener { openDepartureDate() }
        view?.isFocusableInTouchMode = true
        view?.requestFocus()
        view?.setOnKeyListener(pressKeyListener)
    }

    fun openDepartureDate() {
        clearBackStack()
    }

    private fun clearBackStack() {
        if (activity == null) {
            return
        }
        val fragmentManager = activity?.getSupportFragmentManager()
        while (fragmentManager?.backStackEntryCount!! > 1) {
            fragmentManager?.popBackStackImmediate()
        }
    }

    private val pressKeyListener = object : View.OnKeyListener {
        override fun onKey(v: View, keyCode: Int, event: KeyEvent): Boolean {
            if (keyCode == KeyEvent.KEYCODE_BACK) {//只监听返回键
                if (event.action == KeyEvent.ACTION_UP) {
//                    return true
                }
                openDepartureDate()
                return true
            }
            return false
        }
    }
    companion object {

        fun newInstance(update: Boolean): RegisterFinishFragment {
            val infoRegisterFragment = RegisterFinishFragment()
            val bundle = Bundle()
            bundle.putBoolean(MainActivity.NEED_SUPPORT, update)
            infoRegisterFragment.arguments = bundle
            return infoRegisterFragment
        }
    }
}
