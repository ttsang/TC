package sang.thai.tran.travelcompanion.view;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.TypedArray;
import android.support.annotation.Nullable;
import android.text.InputType;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import sang.thai.tran.travelcompanion.R;
import sang.thai.tran.travelcompanion.utils.AppUtils;

public class EditTextViewLayout extends LinearLayout {
    @BindView(R.id.et_input)
    EditText et_input;

    @BindView(R.id.tv_input_label)
    TextView tv_input_label;

    @BindView(R.id.ll_input)
    LinearLayout ll_input;

    public EditTextViewLayout(Context context) {
        super(context);
        initView(context, null);
    }

    public EditTextViewLayout(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initView(context, attrs);
    }

    public EditTextViewLayout(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(context, attrs);
    }

    void initView(final Context context, AttributeSet attrs) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.layout_input, this, true);
        ButterKnife.bind(this, view);
        if (attrs != null) {
            @SuppressLint("CustomViewStyleable") TypedArray a = context.obtainStyledAttributes(attrs,
                    R.styleable.InputView, 0, 0);

            String label = a.getString(R.styleable.InputView_text);
            int textColor = a.getColor(R.styleable.InputView_textColor, 10);
            int imeOptions = a.getInteger(R.styleable.InputView_imeOptions, 0);
            int inputType = a.getInteger(R.styleable.InputView_inputType, -1);
            boolean disable = a.getBoolean(R.styleable.InputView_disable, true);
            final boolean isGreen = a.getBoolean(R.styleable.InputView_isGreen, false);
            tv_input_label.setText(label);
            tv_input_label.setTextColor(textColor);
            et_input.setTextColor(textColor);
            if (imeOptions != -1) {
                et_input.setImeOptions(imeOptions);
            }
            if (inputType != -1) {
                if (inputType == 21) {
                    et_input.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                } else {
                    et_input.setInputType(inputType);
                }
            }
            et_input.setEnabled(disable);
            et_input.setClickable(disable);
            et_input.setFocusable(disable);
            a.recycle();

            et_input.setOnFocusChangeListener(new OnFocusChangeListener() {
                @Override
                public void onFocusChange(View v, boolean hasFocus) {
                    if (ll_input != null) {
                        if (isGreen) {
                            ll_input.setBackground(AppUtils.getDrawable(context, hasFocus ? R.drawable.bg_blue_border_white_filled_rounded_corner
                                    : R.drawable.bg_green_border_white_filled_rounded_corner));
                        } else {
                            ll_input.setBackground(AppUtils.getDrawable(context, hasFocus ? R.drawable.bg_blue_border_white_filled_rounded_corner
                                    : R.drawable.bg_orange_border_white_filled_rounded_corner));
                        }
                    }
                }
            });

            if (isGreen) {
                ll_input.setBackground(AppUtils.getDrawable(context, R.drawable.bg_green_border_white_filled_rounded_corner));
            } else {
                ll_input.setBackground(AppUtils.getDrawable(context, R.drawable.bg_orange_border_white_filled_rounded_corner));
            }
        }


    }

    public String getText() {
        if (et_input != null) {
            return et_input.getText().toString();
        }
        return "";
    }

    public void setText(String text) {
        if (et_input != null) {
            et_input.setText(text);
        }
    }
}
