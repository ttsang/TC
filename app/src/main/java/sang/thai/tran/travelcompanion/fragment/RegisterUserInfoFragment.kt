package sang.thai.tran.travelcompanion.fragment

import android.app.Activity
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.text.Selection
import android.text.TextUtils
import android.util.Log
import android.view.View
import androidx.appcompat.widget.LinearLayoutCompat
import com.bumptech.glide.Glide
import com.countrypicker.CountryPicker
import com.google.gson.Gson
import com.nj.imagepicker.ImagePicker
import com.nj.imagepicker.listener.ImageResultListener
import com.nj.imagepicker.utils.DialogConfiguration
import kotlinx.android.synthetic.main.fragment_register_user_info.*
import sang.thai.tran.travelcompanion.R
import sang.thai.tran.travelcompanion.activity.LoginActivity
import sang.thai.tran.travelcompanion.activity.MainActivity.Companion.UPDATE_INFO
import sang.thai.tran.travelcompanion.model.Response
import sang.thai.tran.travelcompanion.model.UserInfo
import sang.thai.tran.travelcompanion.retrofit.BaseObserver
import sang.thai.tran.travelcompanion.retrofit.HttpRetrofitClientBase
import sang.thai.tran.travelcompanion.utils.AppConstant
import sang.thai.tran.travelcompanion.utils.AppUtils.*
import sang.thai.tran.travelcompanion.utils.ApplicationSingleton
import sang.thai.tran.travelcompanion.utils.DialogUtils
import sang.thai.tran.travelcompanion.utils.DialogUtils.onCreateSingleChoiceDialog
import sang.thai.tran.travelcompanion.utils.DialogUtils.showTermOfService
import sang.thai.tran.travelcompanion.view.EditTextViewLayout

class RegisterUserInfoFragment : BaseFragment() {

    private var cameraFilePath: String? = null
    private var serverPath: String? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        email_sign_in_button.setOnClickListener { executeRegister() }
        updateData()
        et_nationality.editText.onFocusChangeListener = View.OnFocusChangeListener { _, hasFocus ->
            if (hasFocus)
                onCLickNationality()
        }
        et_nationality.setOnClickListener { onCLickNationality() }
        et_year_of_birth.editText.onFocusChangeListener = View.OnFocusChangeListener { _, hasFocus ->
            if (hasFocus)
                onCLickYears()
        }
        et_year_of_birth.setOnClickListener { onCLickYears() }
        et_gender.editText.onFocusChangeListener = View.OnFocusChangeListener { _, hasFocus ->
            if (hasFocus)
                onGender()
        }
        et_gender.setOnClickListener { onGender() }
        rlAdminAvatar.setOnClickListener { choseGallery() }
        tv_terms_of_service.setOnClickListener {
            showTermOfService(activity).show()
        }
    }

    private fun onCLickYears() {
        val dialog = MonthYearPickerDialog()
        dialog.show(fragmentManager!!, "dialog")
        dialog.setListener { _, year, _, _ ->
            val yearStr = year.toString()
            et_year_of_birth!!.text = yearStr
            val position = yearStr.length
            val text = et_year_of_birth!!.editableText
            Selection.setSelection(text, position)
        }
    }

    private fun onCLickNationality() {
        val picker = CountryPicker.newInstance("Select Country")
        picker.show(fragmentManager!!, "COUNTRY_PICKER")
        picker.setListener { _, _, nationality ->
            // Invoke your function here
            et_nationality!!.text = nationality
            val position = nationality.length
            val text = et_nationality!!.editableText
            Selection.setSelection(text, position)
        }
    }

    private fun onGender() {
        if (activity == null) {
            return
        }
        val options = activity!!.resources.getTextArray(R.array.list_gender)
        onCreateSingleChoiceDialog(activity, getString(R.string.label_gender_input_title), options) { dialog, which ->
            dialog.dismiss()
            et_gender!!.text = options[which].toString()
            val position = options[which].toString().length
            val text = et_gender!!.editableText
            Selection.setSelection(text, position)
        }
    }


    private fun choseGallery() {
        val configuration = DialogConfiguration()
                .setTitle("Choose Options")
                .setOptionOrientation(LinearLayoutCompat.VERTICAL)
                .setBackgroundColor(Color.WHITE)
                .setNegativeText(getString(android.R.string.cancel))
                .setNegativeTextColor(Color.RED)
                .setTitleTextColor(Color.BLUE)

        ImagePicker.build(configuration, ImageResultListener { imageResult ->
            cameraFilePath = imageResult.path
            if (TextUtils.isEmpty(ApplicationSingleton.getInstance().token)) {
                rlAdminAvatar!!.setImageBitmap(imageResult.bitmap)
            } else {
                upload()
            }
        }
        ).show(fragmentManager!!)

    }

    private fun updateData() {
        val bundle = arguments
        if (bundle != null) {
            val update = bundle.getBoolean(UPDATE_INFO)
            if (update) {
                val userInfo = ApplicationSingleton.getInstance().userInfo
                if (userInfo != null) {
                    et_full_name!!.text = userInfo.first_Name
                    et_year_of_birth!!.text = userInfo.identify_Date
                    et_gender!!.text = userInfo.gender
                    et_phone!!.text = userInfo.phone
                    et_email!!.text = userInfo.email
                    et_address!!.text = userInfo.address
                    et_nationality!!.text = userInfo.nationality
                    Glide.with(activity!!).load(userInfo.image).into(rlAdminAvatar)
                }
            } else {
                ApplicationSingleton.getInstance().reset()
            }
        } else {
            ApplicationSingleton.getInstance().reset()
        }
    }


    private fun executeRegister() {
        if (ll_parent == null) {
            return
        }
        val count = ll_parent!!.childCount
        for (i in 0 until count) {
            val v = ll_parent!!.getChildAt(i)
            if (v is EditTextViewLayout) {
                if (TextUtils.isEmpty(v.text)) {
                    showWarningDialog(R.string.label_input_info)
                    return
                }
            }
        }
        if (!isEmailValid(et_email!!.text)) {
            showWarningDialog(R.string.label_email_invalid)
            return
        }
        if (!isPassValid(et_pass!!.text)) {
            showWarningDialog(R.string.error_invalid_password)
            return
        }
        if (!isPhoneValid(et_phone!!.text)) {
            showWarningDialog(R.string.label_phone_invalid)
            return
        }
        if (!cb_term.isChecked) {
            showWarningDialog(R.string.label_accept_terms)
            return
        }
        ApplicationSingleton.getInstance().userInfo = createAccount()
        if (!TextUtils.isEmpty(cameraFilePath) && TextUtils.isEmpty(ApplicationSingleton.getInstance().token)) {
            execute()
            return
        }
        val isUpdate = arguments != null && arguments!!.getBoolean(UPDATE_INFO)
        (activity as LoginActivity).replaceFragment(R.id.fl_content, ButtonRegisterFragment.newInstance(isUpdate, null), false)
    }

    private fun upload() {
        Log.d("Sang", " upload $cameraFilePath")
        showProgressDialog()
        if (!TextUtils.isEmpty(cameraFilePath)) {
            HttpRetrofitClientBase.getInstance().executeUpload(AppConstant.API_UPLOAD, cameraFilePath!!, object : BaseObserver<Response>(true) {
                override fun onSuccess(result: Response, response: String) {

                    hideProgressDialog()
                    if (activity == null) {
                        return
                    }
                    if (result.statusCode == AppConstant.SUCCESS_CODE) {
                        if (result.result?.data != null) {
                            serverPath = result.result?.data?.Image_Name
                            Log.d("Sang", " upload serverPath $serverPath")
                            ApplicationSingleton.getInstance().userInfo.image = result.result?.data?.Image_Name
                            activity!!.runOnUiThread {
                                Glide.with(activity!!).load(serverPath).into(rlAdminAvatar)
                            }
                        }
                    }
                }

                override fun onFailure(e: Throwable, errorMsg: String) {
                    hideProgressDialog()
                    if (!TextUtils.isEmpty(errorMsg)) {
                        activity!!.runOnUiThread { DialogUtils.showAlertDialog(activity, errorMsg) { dialog, _ -> dialog.dismiss() } }
                    }
                }
            })
        }
    }

    private fun execute() {
        showProgressDialog()
        var url = AppConstant.API_REGISTER
        val isUpdate = arguments != null && arguments!!.getBoolean(UPDATE_INFO)
        var token = ""
        if (isUpdate) {
            url = AppConstant.API_UPDATE
            token = ApplicationSingleton.getInstance().token
        }
        val userInfo = ApplicationSingleton.getInstance().userInfo
        Log.d("Sang", "execute: " + Gson().toJson(userInfo))
        HttpRetrofitClientBase.getInstance().executePost(
                url,
                token,
                ApplicationSingleton.getInstance().userInfo,
                object : BaseObserver<Response>(true) {
                    override fun onSuccess(result: Response, response: String) {
                        hideProgressDialog()
                        if (activity == null) {
                            return
                        }
                        if (result.statusCode == AppConstant.SUCCESS_CODE) {
                            if (result.result?.data != null)
                                ApplicationSingleton.getInstance().token = result.result?.data?.token
                            (activity as LoginActivity).replaceFragment(R.id.fl_content, ButtonRegisterFragment.newInstance(isUpdate, cameraFilePath), false)
                        } else {
                            activity!!.runOnUiThread { DialogUtils.showAlertDialog(activity, result.message) { dialog, _ -> dialog.dismiss() } }
                        }
                    }

                    override fun onFailure(e: Throwable, errorMsg: String) {
                        hideProgressDialog()
                        if (!TextUtils.isEmpty(errorMsg)) {
                            activity!!.runOnUiThread { DialogUtils.showAlertDialog(activity, errorMsg) { dialog, _ -> dialog.dismiss() } }
                        }
                    }
                })
    }

    private fun createAccount(): UserInfo {
        val userInfo = UserInfo()
        userInfo.address = et_address!!.text
        userInfo.first_Name = et_full_name!!.text
        userInfo.nationality = et_nationality!!.text
        userInfo.phone = et_phone!!.text
        userInfo.identify_Date = et_year_of_birth!!.text
        userInfo.email = et_email!!.text
        userInfo.gender = et_gender!!.text
        userInfo.password = et_pass!!.text
        userInfo.image = serverPath
        Log.d("Sang", "serverPath: " + serverPath)
        return userInfo
    }

    private fun showWarningDialog(string: Int) {
        DialogUtils.showAlertDialog(activity, getString(string)) { dialog, which -> dialog.dismiss() }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        // Result code is RESULT_OK only if the user selects an Image
        if (resultCode == Activity.RESULT_OK)
            when (requestCode) {
                2 -> {
                    //data.getData returns the content URI for the selected Image
                    val selectedImage = data!!.data
//                    rlAdminAvatar!!.setImageURI(selectedImage)
                    Log.d("Sang", "selectedImage: " + selectedImage)
                    cameraFilePath = selectedImage?.toString();
                    upload()
                }
                1 -> {
//                    rlAdminAvatar!!.setImageURI(Uri.parse(cameraFilePath))
                    upload()
                }
            }

    }

    companion object {
        fun newInstance(update: Boolean): RegisterUserInfoFragment {
            val infoRegisterFragment = RegisterUserInfoFragment()
            val bundle = Bundle()
            bundle.putBoolean(UPDATE_INFO, update)
            infoRegisterFragment.arguments = bundle
            return infoRegisterFragment
        }
    }

    override fun layoutId(): Int {
        return R.layout.fragment_register_user_info
    }
}
