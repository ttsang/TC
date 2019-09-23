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


    @SerializedName("List")
    internal var list: List<RegisterModel>? = null

    @SerializedName("DegreesList")
    internal var degreesList: List<DegreeModel>? = null

    @SerializedName("QualificationList")
    internal var qualificationList: List<QualificationModel>? = null

    @SerializedName("CommunicationSkillsList")
    internal var communicationSkillsList: List<CommunicationSkill>? = null

    @SerializedName("HelperSubjectQualificationList")
    internal var helperSubjectQualificationList: List<HelperSubjectQualificationModel>? = null

    @SerializedName("TimesTypeQualificationList")
    internal var timesTypeQualificationList: List<TimesTypeQualificationModel>? = null

    @SerializedName("DayOfWeekList")
    internal var dayOfWeekList: List<DayOfWeekModel>? = null

    @SerializedName("Districts")
    internal var districts: List<DistrictModel>? = null
}
