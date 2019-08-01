package sang.thai.tran.travelcompanion.model

import com.google.gson.annotations.SerializedName

open class FlightJobModel : BaseModel() {

    @SerializedName("JobCode")
    var jobCode: String = ""

    @SerializedName("TicketImage")
    var ticketImage: String? = null
        internal set

    @SerializedName("TicketDeprtDate")
    var ticketDeprtDate: String? = null
        internal set

    @SerializedName("Airline")
    var airline: String? = null
        internal set

    @SerializedName("FlightNumber")
    var flightNumber: String? = null
        internal set

    @SerializedName("TicketDeprtAirport")
    var ticketDeprtAirport: String? = null
        internal set

    @SerializedName("TicketArrAirport")
    var ticketArrAirport: String? = null
        internal set
}
