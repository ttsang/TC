package sang.thai.tran.travelcompanion

import android.app.Application
import android.content.Context
import androidx.multidex.MultiDex
//
//import org.acra.ReportField
//import org.acra.ReportingInteractionMode
//import org.acra.annotation.ReportsCrashes

import sang.thai.tran.travelcompanion.utils.LocaleHelper
import io.reactivex.plugins.RxJavaPlugins
import com.crashlytics.android.Crashlytics
import io.fabric.sdk.android.Fabric





//@ReportsCrashes(mailTo = "lukatrolai@gmail.com", customReportContent = [ReportField.APP_VERSION_NAME, ReportField.APP_VERSION_CODE, ReportField.ANDROID_VERSION, ReportField.PHONE_MODEL, ReportField.STACK_TRACE], mode = ReportingInteractionMode.TOAST, resToastText = R.string.crash_toast_text)
class TravelCompanionApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        RxJavaPlugins.setErrorHandler { }
        Fabric.with(this, Crashlytics())
    }

    override fun attachBaseContext(base: Context) {
        super.attachBaseContext(LocaleHelper.onAttach(base, "en"))
        MultiDex.install(this);
    }
}
