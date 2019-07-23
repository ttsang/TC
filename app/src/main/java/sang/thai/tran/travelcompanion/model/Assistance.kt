package sang.thai.tran.travelcompanion.model

import com.google.gson.annotations.SerializedName

class Assistance {

    @SerializedName("StatusCode")
    var statusCode: Int = 0

    @SerializedName("Text_VN")
    var text_VN: String? = null
        internal set

    @SerializedName("Text_EN")
    var text_EN: String? = null
        internal set

    @SerializedName("Value")
    var value: String? = null
        internal set

    @SerializedName("Group")
    var group: String? = null
        internal set
}