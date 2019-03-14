package sang.thai.tran.travelcompanion.utils;

import sang.thai.tran.travelcompanion.model.UserInfo;

public class ApplicationSingleton {

    private static ApplicationSingleton mInstance;

    public static ApplicationSingleton getInstance() {
        if (mInstance == null) {
            mInstance = new ApplicationSingleton();
        }
        return mInstance;
    }

    private UserInfo mUserInfo;


    public UserInfo getUserInfo() {
        return mUserInfo;
    }

    public void setUserInfo(UserInfo mUserInfo) {
        this.mUserInfo = mUserInfo;
    }
}
