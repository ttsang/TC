package sang.thai.tran.travelcompanion.model

import com.google.gson.annotations.SerializedName

open class Assistance : BaseModel() {

    @SerializedName("StatusCode")
    var statusCode: Int = 0

    @SerializedName("Text_VN")
    var text_VN: String? = null
        internal set

    @SerializedName("Text_EN")
    var text_EN: String? = null
        internal set

    var selected: Boolean? = false
        internal set

}
