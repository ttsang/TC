package sang.thai.tran.travelcompanion.view;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.AttributeSet;
import android.view.Gravity;
import android.widget.CheckedTextView;

import java.lang.reflect.Field;
import java.util.logging.Logger;

@SuppressLint("AppCompatCustomView")
public class LeftSideCheckedTextView extends CheckedTextView {

    public LeftSideCheckedTextView(Context context) {
        this(context, null, 0);
    }

    public LeftSideCheckedTextView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public LeftSideCheckedTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        Field mCheckMarkGravity = null;
        try {
            mCheckMarkGravity = this.getClass().getSuperclass().getDeclaredField("mCheckMarkGravity");
            mCheckMarkGravity.setAccessible(true);
            mCheckMarkGravity.set(this, Gravity.START);
        } catch (Exception e) {
        }
    }
}
