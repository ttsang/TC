package sang.thai.tran.travelcompanion.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import sang.thai.tran.travelcompanion.R;
import sang.thai.tran.travelcompanion.model.UserInfo;
import sang.thai.tran.travelcompanion.utils.ApplicationSingleton;
import sang.thai.tran.travelcompanion.view.EditTextViewLayout;

import static sang.thai.tran.travelcompanion.utils.AppConstant.NEED_SUPPORT_COMPANION;
import static sang.thai.tran.travelcompanion.utils.AppConstant.NEED_SUPPORT_COMPANION_GUIDE;
import static sang.thai.tran.travelcompanion.utils.AppConstant.NEED_SUPPORT_COMPANION_WELL;
import static sang.thai.tran.travelcompanion.utils.AppConstant.SUPPORT_COMPANION;
import static sang.thai.tran.travelcompanion.utils.AppConstant.SUPPORT_COMPANION_GUIDE;
import static sang.thai.tran.travelcompanion.utils.AppConstant.SUPPORT_COMPANION_WELL;

public class MainActivity extends AppCompatActivity {
    public static final String TAG = "MainActivity";

    public static final String WORK_TITLE_EXTRA = "WORK_TITLE_EXTRA";
    public static final String USER_TYPE_EXTRA = "USER_TYPE_EXTRA";
    public static final String UPDATE_INFO = "UPDATE_INFO";


    @BindView(R.id.tv_work_title)
    TextView tv_work_title;

    @BindView(R.id.ll_final_button)
    LinearLayout ll_final_button;

    @BindView(R.id.tv_update_info)
    TextView tv_update_info;

    @BindView(R.id.tv_register_flight)
    TextView tv_register_flight;

    @BindView(R.id.tv_register_well)
    TextView tv_register_well;

    @BindView(R.id.et_full_name)
    EditTextViewLayout et_full_name;

    @BindView(R.id.et_year_of_birth)
    EditTextViewLayout et_year_of_birth;

    @BindView(R.id.et_gender)
    EditTextViewLayout et_gender;

    @BindView(R.id.et_phone)
    EditTextViewLayout et_phone;

    @BindView(R.id.et_email)
    EditTextViewLayout et_email;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_info);
        ButterKnife.bind(this);
        initView();
    }

    private void initView() {
        Intent intent = getIntent();
        if (intent != null) {
            String text = intent.getStringExtra(WORK_TITLE_EXTRA);
            String type = intent.getStringExtra(USER_TYPE_EXTRA);
            switch (type) {
                case SUPPORT_COMPANION:
                    tv_register_well.setVisibility(View.GONE);
                    break;
                case SUPPORT_COMPANION_GUIDE:
                    tv_register_well.setVisibility(View.GONE);
                    tv_register_flight.setText(getString(R.string.label_register_guide));
                    break;
                case SUPPORT_COMPANION_WELL:
                    tv_register_flight.setText(getString(R.string.label_register_flight_companion_domestic));
                    tv_register_well.setText(getString(R.string.label_register_well));
                    break;
                case NEED_SUPPORT_COMPANION:
                    text = getString(R.string.label_need_support);
                    tv_register_well.setVisibility(View.GONE);
                    ll_final_button.setVisibility(View.GONE);
                    break;
                case NEED_SUPPORT_COMPANION_GUIDE:
                    text = getString(R.string.label_need_support_guide);
                    tv_register_well.setVisibility(View.GONE);
                    ll_final_button.setVisibility(View.GONE);
                    tv_register_flight.setText(getString(R.string.label_register_guide));
                    break;
                case NEED_SUPPORT_COMPANION_WELL:
                    text = getString(R.string.label_need_support_well);
                    ll_final_button.setVisibility(View.GONE);
                    tv_register_flight.setText(getString(R.string.label_register_flight_companion));
                    tv_register_well.setText(getString(R.string.label_register_for_hour));
                    break;
            }

            tv_update_info.setText(getString(R.string.label_update_info));
            tv_work_title.setText(text);
        }
        updateUserInfo();
    }

    private void updateUserInfo() {
        UserInfo userInfo = ApplicationSingleton.getInstance().getUserInfo();
        et_full_name.setText(userInfo.getName());
        et_year_of_birth.setText(userInfo.getYear_of_birth());
        et_gender.setText(userInfo.getGender());
        et_phone.setText(userInfo.getPhone());
        et_email.setText(userInfo.getEmail());
    }

//    @OnClick(R.id.tv_update_info)
//    protected void onClickUpdateInfo() {
//        Intent intent = new Intent(this, LoginActivity.class);
//        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
//        startActivity(intent);
//    }
}
