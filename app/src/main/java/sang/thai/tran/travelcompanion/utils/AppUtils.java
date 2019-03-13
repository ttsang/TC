package sang.thai.tran.travelcompanion.utils;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.v4.content.ContextCompat;

public class AppUtils {

    public static Drawable getDrawable(Context context, int id) {
        if (context == null) return null;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            return context.getDrawable(id);
        } else {
            if (context.getResources() != null) {
                return ContextCompat.getDrawable(context, id);
            }
        }
        return null;
    }

    public static int getPixelValue(Context context, int dp) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dp * scale + 0.5f);
    }
}
