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
import sang.thai.tran.travelcompanion.fragment.RegisterGuideNeedFragment
import sang.thai.tran.travelcompanion.utils.ApplicationSingleton


//@ReportsCrashes(mailTo = "lukatrolai@gmail.com", customReportContent = [ReportField.APP_VERSION_NAME, ReportField.APP_VERSION_CODE, ReportField.ANDROID_VERSION, ReportField.PHONE_MODEL, ReportField.STACK_TRACE], mode = ReportingInteractionMode.TOAST, resToastText = R.string.crash_toast_text)
class TravelCompanionApplication : Application() {
    var co : Application? = null
    override fun onCreate() {
        super.onCreate()
        co = this
        RxJavaPlugins.setErrorHandler { }
        Fabric.with(this, Crashlytics())
    }

    override fun attachBaseContext(base: Context) {
        super.attachBaseContext(LocaleHelper.onAttach(base, "en"))
        MultiDex.install(this);
    }


    fun getContext() : Application? {
        return co
    }

    companion object {
        private var mInstance: TravelCompanionApplication? = null

        fun getInstance(): TravelCompanionApplication {
            if (mInstance == null) {
                mInstance = TravelCompanionApplication()
            }
            return mInstance as TravelCompanionApplication
        }
    }
}
