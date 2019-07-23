package sang.thai.tran.travelcompanion.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class MyData {

    var data: Data? = null
        internal set

    var list: List<Assistance>? = null
        internal set
}
