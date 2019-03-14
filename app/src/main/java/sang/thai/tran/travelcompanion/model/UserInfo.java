package sang.thai.tran.travelcompanion.model;

import com.google.gson.annotations.SerializedName;

public class UserInfo {

    @SerializedName("id")
    private Integer id;

    @SerializedName("name")
    private String name;

    @SerializedName("year_of_birth")
    private String year_of_birth;

    @SerializedName("nationality")
    private String nationality;

    @SerializedName("phone")
    private String phone;

    @SerializedName("email")
    private String email;

    @SerializedName("address")
    private String address;

    @SerializedName("pass")
    private String pass;

    @SerializedName("thumbnail_url")
    private String thumbnail_url;

    @SerializedName("gender")
    private String gender;

    @SerializedName("user_type")
    private String user_type;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getYear_of_birth() {
        return year_of_birth;
    }

    public void setYear_of_birth(String year_of_birth) {
        this.year_of_birth = year_of_birth;
    }

    public String getNationality() {
        return nationality;
    }

    public void setNationality(String nationality) {
        this.nationality = nationality;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPass() {
        return pass;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }

    public String getThumbnail_url() {
        return thumbnail_url;
    }

    public void setThumbnail_url(String thumbnail_url) {
        this.thumbnail_url = thumbnail_url;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String et_gender) {
        this.gender = et_gender;
    }

    public String getUser_type() {
        return user_type;
    }

    public void setUser_type(String user_type) {
        this.user_type = user_type;
    }

    @Override
    public String toString() {
        return "UserInfo [id= " + id + ", name= " + name + ", year_of_birth= " + year_of_birth +
                ", nationality= " + nationality + ", phone= " + phone +
                ", email= " + email + ", address= " + address +
                ", thumbnail_url= " + thumbnail_url +
                ", gender= " + gender + ", user_type = " + user_type +
                "]";
    }
}
