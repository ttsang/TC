package sang.thai.tran.travelcompanion.model

import com.google.gson.annotations.SerializedName
import java.io.Serializable

open class BaseModel : Serializable {
    @SerializedName("Text")
    var text_1: String? = null
        internal set

    @SerializedName("Text_2")
    var text_2: String? = null
        internal set

    @SerializedName("Value")
    var value: String? = null
        internal set

    @SerializedName("Group")
    var group: String? = null
        internal set
}

