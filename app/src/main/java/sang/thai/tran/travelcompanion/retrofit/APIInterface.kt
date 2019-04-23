package sang.thai.tran.travelcompanion.retrofit

import io.reactivex.Observable
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.http.Body
import retrofit2.http.FieldMap
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part
import retrofit2.http.QueryMap
import retrofit2.http.Url
import sang.thai.tran.travelcompanion.model.Response
import sang.thai.tran.travelcompanion.model.UserInfo

/**
 * Created by Sang on 16/10/2018.
 */
interface APIInterface {

    @FormUrlEncoded
    @POST
    fun post(@Url url: String, @FieldMap data: Map<String, String>): Observable<String>

    @GET
    operator fun get(@Url url: String, @QueryMap(encoded = true) data: Map<String, String>): Observable<String>

    @POST
    fun uploadAudio(@Url url: String, @Body requestBodyTmp: RequestBody): Observable<String>

    @POST
    fun uploadImage(@Url url: String, @Body requestBodyTmp: RequestBody): Observable<String>

    @Multipart
    @POST("upload")
    fun uploadMultiImages(
            @Url url: String,
            @Part("description") description: RequestBody,
            @Part files: List<MultipartBody.Part>): Observable<String>

    @POST
    fun postLogin(@Url url: String, @QueryMap(encoded = true) data: Map<String, String>): Observable<Response>

    @POST
    fun postRegister(@Url url: String, @Body data: UserInfo): Observable<Response>
}
