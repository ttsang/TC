package sang.thai.tran.travelcompanion.model;

import com.google.gson.annotations.SerializedName;

public class Response {
    String Version;

    public int getStatusCode() {
        return StatusCode;
    }

    public void setStatusCode(int statusCode) {
        StatusCode = statusCode;
    }

    int StatusCode;

    public String getMessage() {
        return Message;
    }

    String Message;

    public Result getResult() {
        return result;
    }

    @SerializedName("Result")
    Result result;
}
