package sang.thai.tran.travelcompanion.model

import com.google.gson.annotations.SerializedName

class Response {
    internal var Version: String? = null

    @SerializedName("StatusCode")
    var statusCode: Int = 0

    @SerializedName("Message")
    var message: String? = null
        internal set

    @SerializedName("Result")
    var result: Result? = null
        internal set
}
