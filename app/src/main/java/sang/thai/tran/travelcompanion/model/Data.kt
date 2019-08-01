package sang.thai.tran.travelcompanion.model

import com.google.gson.Gson
import com.google.gson.JsonArray
import com.google.gson.JsonElement
import com.google.gson.JsonObject
import com.google.gson.annotations.SerializedName
import com.google.gson.reflect.TypeToken


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


    @SerializedName("List")
    internal var list: List<RegisterModel>? = null

//    @SerializedName("List")
//    internal var listNeedSupport: List<RegisterModel>? = null

    private val results: JsonElement? = null // This has been Changed.

//    private val list: List<Assistance>? = null // This has been added newly and cannot be initialized by gson.

    fun getResults(): JsonElement? {
        return results
    }

    fun getResultsList(): List<Assistance> {
        var resultList: MutableList<Assistance> = ArrayList() // Initializing here just to cover the null pointer exception
        val gson = Gson()
        if (getResults() is JsonObject) {
            resultList.add(gson.fromJson(getResults(), Assistance::class.java))
        } else if (getResults() is JsonArray) {
            val founderListType = object : TypeToken<ArrayList<Assistance>>() {

            }.type
            resultList = gson.fromJson<List<Assistance>>(getResults(), founderListType) as MutableList<Assistance>
        }
        return resultList // This is the actual list which i need and will work well with my code.
    }
}
