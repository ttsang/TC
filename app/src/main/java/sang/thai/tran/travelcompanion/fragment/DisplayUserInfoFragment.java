package sang.thai.tran.travelcompanion.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import sang.thai.tran.travelcompanion.R;
import sang.thai.tran.travelcompanion.activity.LoginActivity;
import sang.thai.tran.travelcompanion.activity.MainActivity;
import sang.thai.tran.travelcompanion.model.UserInfo;
import sang.thai.tran.travelcompanion.utils.ApplicationSingleton;
import sang.thai.tran.travelcompanion.view.EditTextViewLayout;

import static sang.thai.tran.travelcompanion.activity.MainActivity.UPDATE_INFO;
import static sang.thai.tran.travelcompanion.activity.MainActivity.USER_TYPE_EXTRA;
import static sang.thai.tran.travelcompanion.activity.MainActivity.WORK_TITLE_EXTRA;
import static sang.thai.tran.travelcompanion.utils.AppConstant.NEED_SUPPORT_COMPANION;
import static sang.thai.tran.travelcompanion.utils.AppConstant.NEED_SUPPORT_COMPANION_GUIDE;
import static sang.thai.tran.travelcompanion.utils.AppConstant.NEED_SUPPORT_COMPANION_WELL;
import static sang.thai.tran.travelcompanion.utils.AppConstant.SUPPORT_COMPANION;
import static sang.thai.tran.travelcompanion.utils.AppConstant.SUPPORT_COMPANION_GUIDE;
import static sang.thai.tran.travelcompanion.utils.AppConstant.SUPPORT_COMPANION_WELL;

public class DisplayUserInfoFragment extends BaseFragment {


    @BindView(R.id.tv_work_title)
    TextView tv_work_title;

    @BindView(R.id.ll_final_button)
    LinearLayout ll_final_button;

    @BindView(R.id.tv_update_info)
    TextView tv_update_info;

    @BindView(R.id.tv_register_flight_or_guide)
    TextView tv_register_flight_or_guide;

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

    private String type;

    public static DisplayUserInfoFragment newInstance(Bundle bundle) {
        DisplayUserInfoFragment infoRegisterFragment = new DisplayUserInfoFragment();
        infoRegisterFragment.setArguments(bundle);
        return infoRegisterFragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_display_info, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        initView();
    }

    private void initView() {
        Bundle intent = getArguments();
        if (intent != null) {
            String title = intent.getString(WORK_TITLE_EXTRA);
            type = intent.getString(USER_TYPE_EXTRA);
            if (!TextUtils.isEmpty(type)) {
                switch (type) {
                    case SUPPORT_COMPANION:
                        tv_register_well.setVisibility(View.GONE);
                        break;
                    case SUPPORT_COMPANION_GUIDE:
                        tv_register_well.setVisibility(View.GONE);
                        tv_register_flight_or_guide.setText(getString(R.string.label_register_guide));
                        break;
                    case SUPPORT_COMPANION_WELL:
                        tv_register_flight_or_guide.setText(getString(R.string.label_register_flight_companion_domestic));
                        tv_register_well.setText(getString(R.string.label_register_well_trained_companion));
                        break;
                    case NEED_SUPPORT_COMPANION:
                        title = getString(R.string.label_need_support);
                        tv_register_well.setVisibility(View.GONE);
                        ll_final_button.setVisibility(View.GONE);
                        break;
                    case NEED_SUPPORT_COMPANION_GUIDE:
                        title = getString(R.string.label_need_support_guide);
                        tv_register_well.setVisibility(View.GONE);
                        ll_final_button.setVisibility(View.GONE);
                        tv_register_flight_or_guide.setText(getString(R.string.label_register_guide));
                        break;
                    case NEED_SUPPORT_COMPANION_WELL:
                        title = getString(R.string.label_need_support_well);
                        ll_final_button.setVisibility(View.GONE);
                        tv_register_flight_or_guide.setText(getString(R.string.label_register_flight_companion));
                        tv_register_well.setText(getString(R.string.label_register_for_hour));
                        break;
                }
            }

            tv_update_info.setText(getString(R.string.label_update_info));
            tv_work_title.setText(title);
        }
        updateUserInfo();
    }

    private void updateUserInfo() {
        UserInfo userInfo = ApplicationSingleton.getInstance().getUserInfo();
        if (userInfo != null) {
            et_full_name.setText(userInfo.getName());
            et_year_of_birth.setText(userInfo.getYear_of_birth());
            et_gender.setText(userInfo.getGender());
            et_phone.setText(userInfo.getPhone());
            et_email.setText(userInfo.getEmail());
        }
    }

    @OnClick(R.id.tv_update_info)
    protected void onClickUpdateInfo() {
        Intent intent = new Intent(getActivity(), LoginActivity.class);
        intent.putExtra(UPDATE_INFO, true);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }

    @OnClick(R.id.tv_register_flight_or_guide)
    protected void onClickRegisterFlight() {
        switch (type) {
            case SUPPORT_COMPANION:
                ((MainActivity) getActivity()).registerFlight(false);
                break;
            case SUPPORT_COMPANION_GUIDE:
                ((MainActivity) getActivity()).registerGuide(false);
                break;
            case SUPPORT_COMPANION_WELL:
                ((MainActivity) getActivity()).registerFlight(false);
                break;
            case NEED_SUPPORT_COMPANION:
                ((MainActivity) getActivity()).registerFlight(true);
                break;
            case NEED_SUPPORT_COMPANION_GUIDE:
                ((MainActivity) getActivity()).registerGuide(true);
                break;
            case NEED_SUPPORT_COMPANION_WELL:
                ((MainActivity) getActivity()).registerFlight(true);
                break;
        }
    }

    @OnClick(R.id.tv_register_well)
    protected void onClickRegisterGuide() {
        ((MainActivity) getActivity()).registerWell(type.equals(NEED_SUPPORT_COMPANION_WELL));
    }
}
