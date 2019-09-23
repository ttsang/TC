package sang.thai.tran.travelcompanion.model

import com.google.gson.annotations.SerializedName
import java.util.ArrayList

class ProfessionalRecordsInfoModel : BaseModel() {

    @SerializedName("Professional_Degree_List")
    var professional_Degree_List: List<String>? = null

    @SerializedName("Qualification_List")
    var qualification_List: List<String>? = null

    @SerializedName("Places_Qualification_List")
     var places_Qualification_List: List<String>? = null

    @SerializedName("Subject_Qualification_List")
     var subject_Qualification_List: List<String>? = null

    @SerializedName("WorkingTimes")
     var workingTimes: MutableList<WorkingTime>? = mutableListOf()

    @SerializedName("Qualification_Business")
     var qualification_Business : String? = null

    @SerializedName("Other_Qualification")
     var other_Qualification : String? = null

    @SerializedName("Medical_Certificate")
     var medical_Certificate : String? = null

    @SerializedName("Medical_Certificate_Place")
     var medical_Certificate_Place : String? = null

    @SerializedName("Foreign_Language")
     var foreign_Language : String? = null

    @SerializedName("Communication_Skills")
     var communication_Skills : String? = null

    @SerializedName("Career")
     var career : String? = null

    @SerializedName("Workplace")
     var workplace : String? = null

    @SerializedName("Working_Position")
     var working_Position : String? = null

    @SerializedName("Times_Type_Qualification")
     var times_Type_Qualification : String? = null

}
