package sang.thai.tran.travelcompanion.model;

import com.google.gson.annotations.SerializedName;

public class Response {
    String Version;
    int StatusCode;
    String Message;

    public Result getResult() {
        return result;
    }

    @SerializedName("Result")
    Result result;
}
