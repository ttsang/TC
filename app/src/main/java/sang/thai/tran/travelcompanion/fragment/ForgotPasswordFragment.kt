package sang.thai.tran.travelcompanion.fragment

import android.os.Bundle
import android.widget.Toast

import butterknife.OnClick
import kotlinx.android.synthetic.main.fragment_forgot_pass.*
import sang.thai.tran.travelcompanion.R

class ForgotPasswordFragment : BaseFragment() {

    override fun layoutId(): Int {
        return R.layout.fragment_forgot_pass
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        email_sign_in_button.setOnClickListener { login() }
    }


    @OnClick(R.id.email_sign_in_button)
    fun login() {
        Toast.makeText(activity, "Come in soon", Toast.LENGTH_SHORT).show()
    }

    companion object {

        fun newInstance(): ForgotPasswordFragment {
            return ForgotPasswordFragment()
        }
    }
}
