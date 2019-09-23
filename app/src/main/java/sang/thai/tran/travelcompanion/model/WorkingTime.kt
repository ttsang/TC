package sang.thai.tran.travelcompanion.model

import com.google.gson.annotations.SerializedName

class WorkingTime() : BaseModel() {

    @SerializedName("Day")
    var day: String? = null

    @SerializedName("FromTime")
    var fromTime: String? = null

    @SerializedName("ToTime")
    var toTime: String? = null

}
