package sang.thai.tran.travelcompanion;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;

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

            tv_update_info.setText(getString(R.string.label_register_flight));
            tv_work_title.setText(text);
        }
    }
}
