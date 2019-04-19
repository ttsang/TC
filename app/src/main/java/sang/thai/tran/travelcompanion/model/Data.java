package sang.thai.tran.travelcompanion.model;

import com.google.gson.annotations.SerializedName;

public class Data {
    public UserInfo getUserInfo() {
        return userInfo;
    }

    @SerializedName("User")
    UserInfo userInfo;

    @SerializedName("Token")
    String Token;
}
