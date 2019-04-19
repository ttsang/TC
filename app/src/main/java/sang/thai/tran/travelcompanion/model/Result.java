package sang.thai.tran.travelcompanion.model;

import com.google.gson.annotations.SerializedName;

public class Result {
    public Data getData() {
        return data;
    }

    @SerializedName("Data")
    Data data;
}
