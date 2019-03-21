package com.countrypicker;

/**
 * POJO
 */
public class Country {
    private String num_code;
    private String en_short_name;

    public String getAlpha_2_code() {
        return alpha_2_code;
    }

    public void setAlpha_2_code(String alpha_2_code) {
        this.alpha_2_code = alpha_2_code;
    }

    private String alpha_2_code;

    public String getNationality() {
        return nationality;
    }

    public void setNationality(String nationality) {
        this.nationality = nationality;
    }

    private String nationality;

    public String getNum_code() {
        return num_code;
    }

    public void setNum_code(String num_code) {
        this.num_code = num_code;
    }

    public String getEn_short_name() {
        return en_short_name;
    }

    public void setEn_short_name(String en_short_name) {
        this.en_short_name = en_short_name;
    }

}