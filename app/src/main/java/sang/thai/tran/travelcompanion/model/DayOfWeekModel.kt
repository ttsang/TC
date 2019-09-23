package sang.thai.tran.travelcompanion.model

import com.google.gson.annotations.SerializedName

open class DayOfWeekModel : BaseModel() {

    @SerializedName("Selected")
    var selected: Boolean? = false
        internal set
}
