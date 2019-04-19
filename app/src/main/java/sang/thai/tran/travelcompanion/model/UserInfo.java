package sang.thai.tran.travelcompanion.model;

import com.google.gson.annotations.SerializedName;

public class UserInfo extends BaseModel{

    @SerializedName("Code")
    private String Code;

    @SerializedName("Postal")
    private Integer Postal;

    @SerializedName("First_Name")
    private String First_Name;

    @SerializedName("Last_Name")
    private String Last_Name;

    @SerializedName("nationality")
    private String nationality;

    @SerializedName("Phone")
    private String Phone;

    @SerializedName("Email")
    private String Email;

    @SerializedName("Address")
    private String Address;

    @SerializedName("Address_2")
    private String Address_2;

    @SerializedName("pass")
    private String pass;

    @SerializedName("Image")
    private String Image;

    @SerializedName("Title")
    private String gender;

    @SerializedName("Type")
    private String Type;

    @SerializedName("City")
    private String City;

    @SerializedName("Identify_No")
    private String Identify_No;

    public String getIdentify_Date() {
        return Identify_Date;
    }

    public void setIdentify_Date(String identify_Date) {
        Identify_Date = identify_Date;
    }

    @SerializedName("Identify_Date")
    private String Identify_Date;

    @SerializedName("Identify_Place")
    private String Identify_Place;

    @SerializedName("Passport")
    private String Passport;

    @SerializedName("Country")
    private String Country;

    @SerializedName("Job_Type")
    private String Job_Type;

    public String getCode() {
        return Code;
    }

    public void setCode(String Code) {
        this.Code = Code;
    }

    public String getFirst_Name() {
        return First_Name;
    }

    public void setFirst_Name(String name) {
        this.First_Name = name;
    }

    public String getNationality() {
        return Country;
    }

    public void setNationality(String nationality) {
        this.Country = nationality;
    }

    public String getPhone() {
        return Phone;
    }

    public void setPhone(String phone) {
        this.Phone = phone;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        this.Email = email;
    }

    public String getAddress() {
        return Address;
    }

    public void setAddress(String address) {
        this.Address = address;
    }

    public String getPass() {
        return pass;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }

    public String getImage() {
        return Image;
    }

    public void setImage(String image) {
        this.Image = image;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String et_gender) {
        this.gender = et_gender;
    }

    public String getType() {
        return Type;
    }

    public void setType(String type) {
        this.Type = type;
    }

    public String getLast_Name() {
        return Last_Name;
    }

    public void setLast_Name(String last_Name) {
        Last_Name = last_Name;
    }
    @Override
    public String toString() {
        return "UserInfo [Code= " + Code + ", First_Name= " + First_Name +
                ", nationality= " + nationality + ", Phone= " + Phone +
                ", Email= " + Email + ", Address= " + Address +
                ", Image= " + Image +
                ", gender= " + gender + ", Type = " + Type +
                "]";
    }
}
