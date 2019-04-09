package sang.thai.tran.travelcompanion;

import android.app.Application;
import android.content.Context;

import sang.thai.tran.travelcompanion.utils.LocaleHelper;

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
