package sang.thai.tran.travelcompanion.fragment

import android.app.Activity
import android.content.Intent
import android.graphics.Color
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.support.v7.widget.LinearLayoutCompat
import android.text.Selection
import android.text.TextUtils
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.LinearLayout
import butterknife.BindView
import butterknife.ButterKnife
import butterknife.OnClick
import com.countrypicker.CountryPicker
import com.google.gson.Gson
import com.nj.imagepicker.ImagePicker
import com.nj.imagepicker.listener.ImageResultListener
import com.nj.imagepicker.utils.DialogConfiguration
import de.hdodenhof.circleimageview.CircleImageView
import sang.thai.tran.travelcompanion.R
import sang.thai.tran.travelcompanion.activity.LoginActivity
import sang.thai.tran.travelcompanion.activity.MainActivity
import sang.thai.tran.travelcompanion.model.UserInfo
import sang.thai.tran.travelcompanion.utils.AppUtils.*
import sang.thai.tran.travelcompanion.utils.ApplicationSingleton
import sang.thai.tran.travelcompanion.utils.DialogUtils
import sang.thai.tran.travelcompanion.utils.DialogUtils.onCreateSingleChoiceDialog
import sang.thai.tran.travelcompanion.view.EditTextViewLayout
import java.io.File
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.*

class RegisterUserInfoFragment : BaseFragment() {

    @BindView(R.id.email_sign_in_button)
    internal var email_sign_in_button: Button? = null

    @BindView(R.id.et_full_name)
    internal var et_full_name: EditTextViewLayout? = null

    @BindView(R.id.et_year_of_birth)
    internal var et_year_of_birth: EditTextViewLayout? = null

    @BindView(R.id.et_gender)
    internal var et_gender: EditTextViewLayout? = null

    @BindView(R.id.et_nationality)
    internal var et_nationality: EditTextViewLayout? = null

    @BindView(R.id.et_phone)
    internal var et_phone: EditTextViewLayout? = null

    @BindView(R.id.et_email)
    internal var et_email: EditTextViewLayout? = null

    @BindView(R.id.et_address)
    internal var et_address: EditTextViewLayout? = null

    @BindView(R.id.et_pass)
    internal var et_pass: EditTextViewLayout? = null

    @BindView(R.id.ll_parent)
    internal var ll_parent: LinearLayout? = null

    @BindView(R.id.rlAdminAvatar)
    internal var rlAdminAvatar: CircleImageView? = null

    private var cameraFilePath: String? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_register_user_info, container, false)
        ButterKnife.bind(this, view)
        email_sign_in_button!!.setOnClickListener { executeRegister() }
        updateData()
        et_nationality!!.editText.onFocusChangeListener = View.OnFocusChangeListener { v, hasFocus ->
            if (hasFocus)
                onCLickNationality()
        }
        et_year_of_birth!!.editText.onFocusChangeListener = View.OnFocusChangeListener { v, hasFocus ->
            if (hasFocus)
                onCLickYears()
        }
        et_gender!!.editText.onFocusChangeListener = View.OnFocusChangeListener { v, hasFocus ->
            if (hasFocus)
                onGender()
        }
        return view
    }

    @OnClick(R.id.et_year_of_birth)
    fun onCLickYears() {
        val dialog = MonthYearPickerDialog()
        dialog.show(fragmentManager!!, "dialog")
        dialog.setListener { view, year, month, dayOfMonth ->
            val yearStr = year.toString()
            et_year_of_birth!!.text = yearStr
            val position = yearStr.length
            val text = et_year_of_birth!!.editableText
            Selection.setSelection(text, position)
        }
    }

    @OnClick(R.id.et_nationality)
    fun onCLickNationality() {
        val picker = CountryPicker.newInstance("Select Country")
        picker.show(fragmentManager!!, "COUNTRY_PICKER")
        picker.setListener { name, code, nationality ->
            // Invoke your function here
            et_nationality!!.text = nationality
            val position = nationality.length
            val text = et_nationality!!.editableText
            Selection.setSelection(text, position)
        }
    }

    @OnClick(R.id.et_gender)
    fun onGender() {
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


    @OnClick(R.id.rlAdminAvatar)
    fun choseGallery() {
        val configuration = DialogConfiguration()
                .setTitle("Choose Options")
                .setOptionOrientation(LinearLayoutCompat.VERTICAL)
                .setBackgroundColor(Color.WHITE)
                .setNegativeText("No")
                .setNegativeTextColor(Color.RED)
                .setTitleTextColor(Color.BLUE)

        ImagePicker.build(configuration, ImageResultListener { imageResult -> rlAdminAvatar!!.setImageBitmap(imageResult.bitmap) }).show(fragmentManager!!)
    }

    private fun updateData() {
        val bundle = arguments
        if (bundle != null) {
            val update = bundle.getBoolean(MainActivity.UPDATE_INFO)
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
                }
            }
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
        ApplicationSingleton.getInstance().userInfo = createAccount()
        val isUpdate = arguments != null && arguments!!.getBoolean(MainActivity.UPDATE_INFO)
        (activity as LoginActivity).replaceFragment(R.id.fl_content, ButtonRegisterFragment.newInstance(isUpdate), false)
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
        val gson = Gson()

        Log.d("Sang", "createAccount: " + gson.toJson(userInfo))
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
                    rlAdminAvatar!!.setImageURI(selectedImage)
                }
                1 -> rlAdminAvatar!!.setImageURI(Uri.parse(cameraFilePath))
            }

    }

    @Throws(IOException::class)
    private fun createImageFile(): File {
        // Create an image file name
        val timeStamp = SimpleDateFormat("yyyyMMdd_HHmmss", Locale.ENGLISH).format(Date())
        val imageFileName = "JPEG_" + timeStamp + "_"
        //This is the directory in which the file will be created. This is the default location of Camera photos
        val storageDir = File(Environment.getExternalStoragePublicDirectory(
                Environment.DIRECTORY_DCIM), "Camera")
        val image = File.createTempFile(
                imageFileName, /* prefix */
                ".jpg", /* suffix */
                storageDir      /* directory */
        )
        // Save a file: path for using again
        cameraFilePath = "file://" + image.absolutePath
        return image
    }

    companion object {


        fun newInstance(update: Boolean): RegisterUserInfoFragment {
            val infoRegisterFragment = RegisterUserInfoFragment()
            val bundle = Bundle()
            bundle.putBoolean(MainActivity.UPDATE_INFO, update)
            infoRegisterFragment.arguments = bundle
            return infoRegisterFragment
        }
    }
}
