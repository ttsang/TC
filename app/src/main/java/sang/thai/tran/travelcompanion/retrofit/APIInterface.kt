package sang.thai.tran.travelcompanion.retrofit

import io.reactivex.Observable
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.http.*
import sang.thai.tran.travelcompanion.model.FlightJobModel
import sang.thai.tran.travelcompanion.model.RegisterModel
import sang.thai.tran.travelcompanion.model.Response
import sang.thai.tran.travelcompanion.model.UserInfo
import sang.thai.tran.travelcompanion.utils.AppConstant.API_PARAM_ACCESS_TOKEN

/**
 * Created by Sang on 16/10/2018.
 */
interface APIInterface {

    @POST
    fun post(@Url url: String, @QueryMap(encoded = true) data: Map<String, String>): Observable<Response>

    @GET
    operator fun get(@Url url: String, @Query(value = API_PARAM_ACCESS_TOKEN) token: String): Observable<Response>

    @POST
    fun uploadAudio(@Url url: String, @Body requestBodyTmp: RequestBody): Observable<String>

    @POST
    fun uploadImage(@Url url: String, @Query(value = API_PARAM_ACCESS_TOKEN) token: String, @Body data: RequestBody): Observable<Response>

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

    @POST
    fun postOnFlightJob(@Url url: String, @Query(value = API_PARAM_ACCESS_TOKEN) token: String, @Body data: FlightJobModel): Observable<Response>

    @POST
    fun postUpdate(@Url url: String, @Query(value = API_PARAM_ACCESS_TOKEN) token: String, @Body data: UserInfo): Observable<Response>

    @POST
    fun postRegisterFeature(@Url url: String, @Query(value = API_PARAM_ACCESS_TOKEN) token: String, @Body data: RegisterModel): Observable<Response>
}
