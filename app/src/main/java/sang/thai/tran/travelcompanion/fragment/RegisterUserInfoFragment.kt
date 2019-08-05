package sang.thai.tran.travelcompanion.fragment

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.telephony.TelephonyManager
import android.text.*
import android.text.method.LinkMovementMethod
import android.text.style.ClickableSpan
import android.util.Log
import android.view.View
import android.widget.TextView
import androidx.annotation.NonNull
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
import sang.thai.tran.travelcompanion.utils.AppUtils
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
        updateData()
        if (update) {
            email_sign_in_button.setText(getString(R.string.label_update))
            tv_terms_of_service.visibility = View.GONE
            ll_check_box.visibility = View.GONE
        } else {
            email_sign_in_button.setText(getString(R.string.label_register))
        }
        email_sign_in_button.setOnClickListener { executeRegister() }
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
            activity?.let { it1 -> showTermOfService(it1).show() }
        }
        setSpannable()
    }

    private fun setSpannable() {
        val ss = SpannableString(Html.fromHtml(
                activity?.getString(R.string.terms_of_service)
        ))
//                            + RootSettingModel.address));
        val storeNameClickableSpan = object : ClickableSpan() {
            override fun onClick(@NonNull textView: View) {
                //open to checkin from route setting
                //                    activity.createStoreFragment(rootSettingModel, RecordFragment.dateIndicator);
            }

            override fun updateDrawState(@NonNull ds: TextPaint) {
                super.updateDrawState(ds)
                ds.color = getColor(activity, R.color.color_blue_logo)
                ds.isUnderlineText = true
            }
        }
        try {
            ss.setSpan(storeNameClickableSpan, 0,
                    activity?.getString(R.string.terms_of_service)?.length!!, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
        } catch (e: Exception) {
        }

        tv_terms_of_service.movementMethod = LinkMovementMethod.getInstance()
        tv_terms_of_service.setText(ss, TextView.BufferType.SPANNABLE)
    }

    private fun onCLickYears() {
        val dialog = MonthYearPickerDialog()
        dialog.show(fragmentManager!!, "dialog")
        dialog.setListener { _, year, _, _ ->
            val yearStr = year.toString()
            et_year_of_birth?.text = yearStr
            val position = yearStr.length
            val text = et_year_of_birth?.editableText
            Selection.setSelection(text, position)
        }
    }

    private fun onCLickNationality() {
        val picker = CountryPicker.newInstance("Select Country")
        picker.show(fragmentManager!!, "COUNTRY_PICKER")
        picker.setListener { num_code, _, nationality ->
            // Invoke your function here
            et_nationality?.text = nationality
            val position = nationality.length
            val text = et_nationality?.editableText
            Selection.setSelection(text, position)
            setCountryCode(num_code)
        }
    }

    private fun onGender() {
        if (activity == null) {
            return
        }
        val options = activity?.resources?.getTextArray(R.array.list_gender)
        onCreateSingleChoiceDialog(activity, getString(R.string.label_gender_input_title), options) { dialog, which ->
            dialog.dismiss()
            et_gender?.text = options?.get(which).toString()
            val position = options?.get(which).toString().length
            val text = et_gender?.editableText
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
                rlAdminAvatar?.setImageBitmap(imageResult.bitmap)
            } else {
                upload()
            }
        }
        ).show(fragmentManager)

    }

    var update: Boolean = false
    private fun updateData() {
        val bundle = arguments
        if (bundle != null) {
            update = bundle.getBoolean(UPDATE_INFO)
            if (update) {
                val userInfo = ApplicationSingleton.getInstance().userInfo
                if (userInfo != null) {
                    et_full_name?.text = userInfo.first_Name
                    et_year_of_birth?.text = userInfo.birthday
                    et_gender?.text = userInfo.gender
                    et_phone?.text = userInfo.phone
                    et_email?.text = userInfo.email
                    et_address?.text = userInfo.address
                    et_nationality?.text = userInfo.nationality
                    Glide.with(activity!!).load(userInfo.image).into(rlAdminAvatar)
                }
            } else {
                setCountryCode("")
                ApplicationSingleton.getInstance().reset()
            }
        } else {
            setCountryCode("")
            ApplicationSingleton.getInstance().reset()
        }
    }

    @Suppress("DEPRECATION")
    private fun setCountryCode(countryCode: String) {
        val locale = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            context?.resources?.configuration?.locales?.get(0).toString()
        } else {
            context?.resources?.configuration?.locale?.country
        }
        Log.d("Sang", "locale: $locale")
        val tm = activity?.getSystemService(Context.TELEPHONY_SERVICE) as TelephonyManager
        Log.d("Sang", "countryCode: $countryCode")


        var countryZipCode = ""

        //getNetworkCountryIso
        var countryID = tm.simCountryIso.toUpperCase()
        if (TextUtils.isEmpty(countryID)) {
            countryID = countryCode.toUpperCase()
        }
        val rl = activity?.resources?.getStringArray(R.array.CountryCodes)
        for (i in 0 until rl?.size!!) {
            val g = rl[i]?.split(",")
            if (g?.get(1)?.trim().equals(countryID.trim())) {
                countryZipCode = g?.get(0) ?: ""
                break
            }
        }
        Log.d("Sang", "CountryZipCode: $countryZipCode")
        et_phone.text = "+$countryZipCode"
    }

    private fun executeRegister() {
        if (ll_parent == null) {
            return
        }
        val count = ll_parent?.childCount
        for (i in 0 until count!!) {
            val v = ll_parent?.getChildAt(i)
            if (v is EditTextViewLayout) {
                if (TextUtils.isEmpty(v.text)) {
                    showWarningDialog(R.string.label_input_info)
                    return
                }
            }
        }
        if (!isEmailValid(et_email?.text)) {
            showWarningDialog(R.string.label_email_invalid)
            return
        }
//        val errorList = isPassValid(passwordhere, confirmhere)
//        while (!errorList.isEmpty()) {
//            println("The password entered here  is invalid")
//            for (error in errorList) {
//                println(error)
//            }
//            val Passwordhere = `in`.nextLine()
//            print("Please re-enter the password to confirm : ")
//        }
        if (!isPassValid(et_pass?.text)) {
            showWarningDialog(R.string.error_invalid_password)
            return
        }
        if (!isPhoneValid(et_phone?.text)) {
            showWarningDialog(R.string.label_phone_invalid)
            return
        }
        if (!cb_term.isChecked && !update) {
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
                            activity?.runOnUiThread {
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
                            if (result.result?.data != null && !isUpdate)
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
        var userInfo = ApplicationSingleton.getInstance().userInfo
        if (userInfo == null) {
            userInfo = UserInfo()
        }

        userInfo.address = et_address!!.text
        userInfo.first_Name = et_full_name!!.text
        userInfo.nationality = et_nationality!!.text
        userInfo.phone = et_phone!!.text
        userInfo.birthday = et_year_of_birth!!.text + if (update) "" else "-01-01"
        userInfo.email = et_email!!.text
        userInfo.gender = et_gender!!.text
        userInfo.password = et_pass!!.text
        if (!TextUtils.isEmpty(serverPath)) {
            userInfo.image = serverPath
        }
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
                    val selectedImage = data!!.data
                    Log.d("Sang", "selectedImage: $selectedImage")
                    cameraFilePath = selectedImage?.toString()
                    upload()
                }
                1 -> {
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
