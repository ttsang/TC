package sang.thai.tran.travelcompanion;

import android.app.Application;
import android.content.Context;
import org.acra.ACRA;
import org.acra.ReportField;
import org.acra.ReportingInteractionMode;
import org.acra.annotation.ReportsCrashes;
import sang.thai.tran.travelcompanion.utils.LocaleHelper;

@ReportsCrashes(mailTo = "lukatrolai@gmail.com",
        customReportContent = {
                ReportField.APP_VERSION_NAME,
                ReportField.APP_VERSION_CODE,
                ReportField.ANDROID_VERSION,
                ReportField.PHONE_MODEL,
                ReportField.STACK_TRACE,
        },
        mode = ReportingInteractionMode.TOAST, resToastText = R.string.crash_toast_text)
public class TravelCompanionApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(LocaleHelper.onAttach(base, "en"));
    }
}
