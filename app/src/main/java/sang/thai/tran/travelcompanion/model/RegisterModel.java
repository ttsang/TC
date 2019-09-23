package sang.thai.tran.travelcompanion.model;

import com.google.gson.annotations.SerializedName;

public class RegisterModel extends Assistance {

    //API_UPDATE_ON_FLIGHT
    @SerializedName("id")
    private String id;

    @SerializedName("DepartureDateFrom")
    private String departureDateFrom;

    @SerializedName("AirlineOption")
    private String airlineOption;

    @SerializedName("FlightNumber")
    private String flightNumber;

    @SerializedName("DepartureAirport")
    private String departureAirport;

    @SerializedName("ArrivalAirport")
    private String arrivalAirport;

    @SerializedName("ExpectAssistance")
    private String expectAssistance;

    @SerializedName("Note")
    private String note;

    @SerializedName("AdditionalServices")
    private String additionalServices;

    @SerializedName("ElderlyNumber")
    private int elderlyNumber;

    @SerializedName("MiddleAgeNumber")
    private int middleAgeNumber;

    @SerializedName("ChildrenNumber")
    private int childrenNumber;

    @SerializedName("PregnantNumber")
    private int pregnantNumber;

    @SerializedName("DisabilityNumber")
    private int disabilityNumber;

    //POST /api/poster/updateGuidesPlace
    @SerializedName("PickupPoint")
    private String pickupPoint;

    @SerializedName("VisitPlaces")
    private String visitPlaces;

    //POST /api/poster/updateWellTrained
    @SerializedName("ContactName")
    private String contactName;

    @SerializedName("WellTrainedObject")
    private String wellTrainedObject;

    @SerializedName("Type")
    private String type;

    @SerializedName("Status")
    private String status;

    @SerializedName("Code")
    private String code;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDepartureDateFrom() {
        return departureDateFrom;
    }

    public void setDepartureDateFrom(String departureDateFrom) {
        this.departureDateFrom = departureDateFrom;
    }

    public String getAirlineOption() {
        return airlineOption;
    }

    public void setAirlineOption(String airlineOption) {
        this.airlineOption = airlineOption;
    }

    public String getFlightNumber() {
        return flightNumber;
    }

    public void setFlightNumber(String flightNumber) {
        this.flightNumber = flightNumber;
    }

    public String getDepartureAirport() {
        return departureAirport;
    }

    public void setDepartureAirport(String departureAirport) {
        this.departureAirport = departureAirport;
    }

    public String getArrivalAirport() {
        return arrivalAirport;
    }

    public void setArrivalAirport(String arrivalAirport) {
        this.arrivalAirport = arrivalAirport;
    }

    public String getExpectAssistance() {
        return expectAssistance;
    }

    public void setExpectAssistance(String expectAssistance) {
        this.expectAssistance = expectAssistance;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public String getAdditionalServices() {
        return additionalServices;
    }

    public void setAdditionalServices(String additionalServices) {
        this.additionalServices = additionalServices;
    }

    public int getElderlyNumber() {
        return elderlyNumber;
    }

    public void setElderlyNumber(int elderlyNumber) {
        this.elderlyNumber = elderlyNumber;
    }

    public int getMiddleAgeNumber() {
        return middleAgeNumber;
    }

    public void setMiddleAgeNumber(int middleAgeNumber) {
        this.middleAgeNumber = middleAgeNumber;
    }

    public int getChildrenNumber() {
        return childrenNumber;
    }

    public void setChildrenNumber(int childrenNumber) {
        this.childrenNumber = childrenNumber;
    }

    public int getPregnantNumber() {
        return pregnantNumber;
    }

    public void setPregnantNumber(int pregnantNumber) {
        this.pregnantNumber = pregnantNumber;
    }

    public int getDisabilityNumber() {
        return disabilityNumber;
    }

    public void setDisabilityNumber(int disabilityNumber) {
        this.disabilityNumber = disabilityNumber;
    }

    public String getPickupPoint() {
        return pickupPoint;
    }

    public void setPickupPoint(String pickupPoint) {
        this.pickupPoint = pickupPoint;
    }

    public String getVisitPlaces() {
        return visitPlaces;
    }

    public void setVisitPlaces(String visitPlaces) {
        this.visitPlaces = visitPlaces;
    }

    public String getContactName() {
        return contactName;
    }

    public void setContactName(String contactName) {
        this.contactName = contactName;
    }

    public String getWellTrainedObject() {
        return wellTrainedObject;
    }

    public void setWellTrainedObject(String wellTrainedObject) {
        this.wellTrainedObject = wellTrainedObject;
    }
}
