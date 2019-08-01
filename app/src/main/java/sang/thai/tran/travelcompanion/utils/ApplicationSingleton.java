package sang.thai.tran.travelcompanion.utils;

import sang.thai.tran.travelcompanion.model.RegisterModel;
import sang.thai.tran.travelcompanion.model.UserInfo;

import static sang.thai.tran.travelcompanion.utils.AppUtils.calledFrom;

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

    public String getToken() {
        return Token;
    }

    public void setToken(String token) {
        Log.d("Sang", "setToken : " + token + " calledFrom: " + calledFrom());
        Token = token;
    }

    private String Token;

    public String getUserType() {
        return userType;
    }

    public void setUserType(String userType) {
        this.userType = userType;
    }

    private String userType;

    public void reset() {
        setToken(null);
        setUserInfo(null);
    }

    public RegisterModel getRegisterModel() {
        return registerModel;
    }

    public void setRegisterModel(RegisterModel registerModel) {
        this.registerModel = registerModel;
    }

    private RegisterModel registerModel;
}
