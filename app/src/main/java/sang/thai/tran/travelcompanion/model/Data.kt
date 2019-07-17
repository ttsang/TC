package sang.thai.tran.travelcompanion.model

import com.google.gson.annotations.SerializedName

class Data {

    @SerializedName("User")
    var userInfo: UserInfo? = null
        internal set

    @SerializedName("Token")
    var token: String? = null
        internal set

    @SerializedName("ImageName")
    internal var Image_Name: String? = null

    @SerializedName("Message")
    internal var message: String? = null

}
