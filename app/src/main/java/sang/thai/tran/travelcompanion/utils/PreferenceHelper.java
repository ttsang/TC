package sang.thai.tran.travelcompanion.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import static sang.thai.tran.travelcompanion.utils.AppConstant.CURRENT_LANGUAGE;


/**
 * This is manager class that support manage set and get preference data of app.
 * @author Sang
 *
 */
public class PreferenceHelper {
	private SharedPreferences mSharedPrefer;
	private static PreferenceHelper mInstance;
	private static final String IS_CHECK_IN_ME = "IS_CHECK_IN_ME";
	private static final String IS_LOG_IN = "IS_LOG_IN";
	private static final String IS_GO_TO_OTHER_APP = "IS_GO_TO_OTHER_APP";
	private static final String IS_CHAT_SCREEN = "IS_CHAT_SCREEN";
	private static final String IS_BACK_GROUND = "IS_BACK_GROUND";

	private PreferenceHelper(Context context) {
		mSharedPrefer = PreferenceManager.getDefaultSharedPreferences(context);
	}

	/**
	 * get current doing preference
	 * @return
	 */
	public SharedPreferences getPreference() {
		return mSharedPrefer;
	}
	
	/**
	 * get settings instance
	 * @param context
	 * @return
	 */
	public static PreferenceHelper getInstance(Context context) {
		if (mInstance == null) {
			mInstance = new PreferenceHelper(context);
		}
		return mInstance;
	}




	/**
	 * Support track custom data
	 * @param value
	 * @return
	 */
	public void setIsLogIn(boolean value) {
		mSharedPrefer.edit().putBoolean(IS_LOG_IN, value).apply();
	}

	/**
	 * Support get value from key
	 * @return
	 */
	public boolean getIsLogin() {
		return mSharedPrefer.getBoolean(IS_LOG_IN,false);
	}

	/**
	 * Support track custom data
	 * @param value
	 * @return
	 */
	public void setGotoOtherApp(boolean value) {
		mSharedPrefer.edit().putBoolean(IS_GO_TO_OTHER_APP, value).apply();
	}

	/**
	 * Support get value from key
	 * @return
	 */
	public boolean getIsGotoOtherApp() {
		return mSharedPrefer.getBoolean(IS_GO_TO_OTHER_APP,false);
	}

	/**
	 * Support track custom data
	 * @param value
	 * @return
	 */
	public void setChatScreen(boolean value) {
		mSharedPrefer.edit().putBoolean(IS_CHAT_SCREEN, value).apply();
	}

	/**
	 * Support get value from key
	 * @return
	 */
	public boolean getIsChatScreen() {
		return mSharedPrefer.getBoolean(IS_CHAT_SCREEN,false);
	}


	/**
	 * Support track custom data
	 * @param value
	 * @return
	 */
	public void setLanguage(String value) {
		mSharedPrefer.edit().putString(CURRENT_LANGUAGE, value).apply();
	}

	/**
	 * Support get value from key
	 * @return
	 */
	public String getLanguage(String defaultLang) {
		return mSharedPrefer.getString(CURRENT_LANGUAGE,defaultLang);
	}

	/**
	 * Support track custom data
	 * @param value
	 * @return
	 */
	public void setString(String param, String value) {
		mSharedPrefer.edit().putString(param, value).apply();
	}

	/**
	 * Support get value from key
	 * @return
	 */
	public String getString(String param) {
		return mSharedPrefer.getString(param,"");
	}
}
