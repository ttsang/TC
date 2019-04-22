package sang.thai.tran.travelcompanion.retrofit;

import java.util.List;
import java.util.Map;

import io.reactivex.Observable;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.http.Body;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.QueryMap;
import retrofit2.http.Url;
import sang.thai.tran.travelcompanion.model.Response;
import sang.thai.tran.travelcompanion.model.UserInfo;

/**
 * Created by Sang on 16/10/2018.
 */
public interface APIInterface {

    @FormUrlEncoded
    @POST()
    Observable<String> post(@Url String url, @FieldMap Map<String, String> data);

    @GET()
    Observable<String> get(@Url String url, @QueryMap(encoded = true) Map<String, String> data);

    @POST()
    Observable<String> uploadAudio(@Url String url, @Body RequestBody requestBodyTmp);

    @POST()
    Observable<String> uploadImage(@Url String url, @Body RequestBody requestBodyTmp);

    @Multipart
    @POST("upload")
    Observable<String> uploadMultiImages(
            @Url String url,
            @Part("description") RequestBody description,
            @Part List<MultipartBody.Part> files);

    @POST()
    Observable<Response> postLogin(@Url String url, @QueryMap(encoded=true) Map<String, String> data);

    @POST()
    Observable<Response> postRegister(@Url String url, @Body UserInfo data);
}
