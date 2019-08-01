package sang.thai.tran.travelcompanion.retrofit


import android.text.TextUtils
import android.util.Log
import com.google.gson.GsonBuilder
import io.reactivex.schedulers.Schedulers
import okhttp3.*
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory
import sang.thai.tran.travelcompanion.BuildConfig
import sang.thai.tran.travelcompanion.model.FlightJobModel
import sang.thai.tran.travelcompanion.model.RegisterModel
import sang.thai.tran.travelcompanion.model.Response
import sang.thai.tran.travelcompanion.model.UserInfo
import sang.thai.tran.travelcompanion.utils.AppConstant
import sang.thai.tran.travelcompanion.utils.AppConstant.API_UPDATE
import sang.thai.tran.travelcompanion.utils.ApplicationSingleton
import java.io.File
import java.io.FileInputStream
import java.util.*
import java.util.concurrent.TimeUnit
import java.util.concurrent.TimeUnit.MILLISECONDS

/*
 * Created by Sang Heo Map
 */
class HttpRetrofitClientBase {
    private val REQUEST_TIMEOUT_FOR_UPLOADING: Long = 60000
    private var retrofit: Retrofit? = null

    val baseUrl: String
        get() = "http://assistant.uniquetour.biz/"

    private fun initialize(url: String): Retrofit {
        val interceptor = HttpLoggingInterceptor()
        interceptor.level = HttpLoggingInterceptor.Level.BODY

        val gson = GsonBuilder()
                .setDateFormat("yyyy-MM-dd'T'HH:mm:ssZ")
                .setLenient()
                .create()
        val client = buildClient(interceptor)

        // Retrofit
        return Retrofit.Builder()
                .baseUrl(url)
                .client(client)
                .addConverterFactory(ScalarsConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build()
    }


    private fun buildClient(interceptor: HttpLoggingInterceptor): OkHttpClient {
        val okHttpClientBuilder = OkHttpClient.Builder()
        okHttpClientBuilder.connectTimeout(30, TimeUnit.SECONDS)
        okHttpClientBuilder.readTimeout(CONNECT_TIMEOUT, MILLISECONDS)
        okHttpClientBuilder.writeTimeout(CONNECT_TIMEOUT, MILLISECONDS)
        okHttpClientBuilder.connectionSpecs(listOf(ConnectionSpec.COMPATIBLE_TLS, ConnectionSpec.CLEARTEXT, ConnectionSpec.MODERN_TLS))
        if (BuildConfig.DEBUG) {
            okHttpClientBuilder.addInterceptor(interceptor)
            //            okHttpClientBuilder.addNetworkInterceptor(new StethoInterceptor());
        }

        //        okHttpClientBuilder.connectionSpecs(Collections.singletonList(spec));
        okHttpClientBuilder.addNetworkInterceptor { chain ->
            val original = chain.request()
            // Request customization: add request headers
            val requestBuilder = original.newBuilder()
//                    .addHeader("Content-Type", "application/json")
//                    .addHeader("Content-Type", "application/octet-stream")
                    .addHeader("Accept", "*/*")

            //                String authorization = AppHelper.sp.getString(API_AUTHORIZATION_CODE,"");
            //                if (!TextUtils.isEmpty(authorization)) {
            //                    requestBuilder.addHeader(AUTHORIZATION, authorization);
            //                }
            val request = requestBuilder.build()
            val response = chain.proceed(request)
            Log.d(TAG, "response Code : $response")
            response
        }

        return okHttpClientBuilder.build()
    }

    private fun getRetrofit(): Retrofit? {
        if (retrofit == null) {
            retrofit = initialize(baseUrl)
        }
        return retrofit
    }


    fun loginFunction(url: String, params: Map<String, String>?, listener: BaseObserver<Response>) {
        if (params == null) {
            return
        }
        val service = getRetrofit()!!.create(APIInterface::class.java)
        val serviceObservable = service.postLogin(url, params)
                .subscribeOn(Schedulers.newThread())
                .observeOn(Schedulers.computation())
                .timeout(CONNECT_TIMEOUT, MILLISECONDS)
        serviceObservable.subscribe(listener)
    }

    fun executePost(url: String, token: String?, userInfo: UserInfo?, listener: BaseObserver<Response>) {
        if (userInfo == null) {
            return
        }
        val service = getRetrofit()!!.create(APIInterface::class.java)
        if (!TextUtils.isEmpty(token) && url == API_UPDATE) {
            val serviceObservable = token?.let {
                service.postUpdate(url, it, userInfo)
                        .subscribeOn(Schedulers.newThread())
                        .observeOn(Schedulers.computation())
                        .timeout(CONNECT_TIMEOUT, MILLISECONDS)
            }
            serviceObservable?.subscribe(listener)
        } else {
            val serviceObservable = service.postRegister(url, userInfo)
                    .subscribeOn(Schedulers.newThread())
                    .observeOn(Schedulers.computation())
                    .timeout(CONNECT_TIMEOUT, MILLISECONDS)
            serviceObservable.subscribe(listener)
        }
    }

    fun postRegisterFeature(url: String, token: String?, userInfo: RegisterModel?, listener: BaseObserver<Response>) {
        if (userInfo == null) {
            return
        }
        val service = getRetrofit()!!.create(APIInterface::class.java)
        val serviceObservable = token?.let {
            service.postRegisterFeature(url, it, userInfo)
                    .subscribeOn(Schedulers.newThread())
                    .observeOn(Schedulers.computation())
                    .timeout(CONNECT_TIMEOUT, MILLISECONDS)
        }
        serviceObservable?.subscribe(listener)
    }

    fun executePost(url: String, data: Map<String, String>, listener: BaseObserver<Response>) {
        val service = getRetrofit()!!.create(APIInterface::class.java)
        val serviceObservable = service.post(url, data)
                .subscribeOn(Schedulers.newThread())
                .observeOn(Schedulers.computation())
                .timeout(CONNECT_TIMEOUT, MILLISECONDS)
        serviceObservable.subscribe(listener)
    }

    fun executeTakeJobOnFlightPost(url: String, token: String, data: FlightJobModel, listener: BaseObserver<Response>) {
        val service = getRetrofit()!!.create(APIInterface::class.java)
        val serviceObservable = service.postOnFlightJob(url, token, data)
                .subscribeOn(Schedulers.newThread())
                .observeOn(Schedulers.computation())
                .timeout(CONNECT_TIMEOUT, MILLISECONDS)
        serviceObservable.subscribe(listener)
    }

    fun executeGet(url: String, token: String, listener: BaseObserver<Response>) {
        val service = getRetrofit()!!.create(APIInterface::class.java)
        val serviceObservable = service.get(url, token)
                .subscribeOn(Schedulers.newThread())
                .observeOn(Schedulers.computation())
                .timeout(CONNECT_TIMEOUT, MILLISECONDS)
        serviceObservable.subscribe(listener)
    }

    fun executeUpload(urlParam: String, imageFile: String, listener: BaseObserver<Response>) {
        val file = File(imageFile)
        if (!file.exists() || TextUtils.isEmpty(ApplicationSingleton.getInstance().token)) {
            listener.onFailure(Exception("File is not exist"), "File is not exist")
            return
        }
        val params = HashMap<String, String>()
        params[AppConstant.API_PARAM_ACCESS_TOKEN] = ApplicationSingleton.getInstance().token
        val requestBody = RequestBody.create(MediaType.parse("image/*"), file)
        val input = FileInputStream(File(imageFile))
        val buf = ByteArray(input.available())
        while (input.read(buf) != -1) {

        }
        val requestBodyTmp = MultipartBody.Builder()
                .setType(MultipartBody.FORM)
                .addFormDataPart("File", file.name, requestBody)
                .build()
        val service = getRetrofit()!!.create(APIInterface::class.java)


        service.uploadImage(urlParam, ApplicationSingleton.getInstance().token, requestBodyTmp)
                .subscribeOn(Schedulers.newThread())
                .observeOn(Schedulers.computation())
                .timeout(REQUEST_TIMEOUT_FOR_UPLOADING, MILLISECONDS)
                .subscribe(listener)
        listener.setUrl(urlParam)
        listener.setParams(params)

    }


    companion object {
        private const val CONNECT_TIMEOUT: Long = 25000   // 30 seconds
        //        private const val GOOGLE_API_URL = "https://maps.googleapis.com/maps/api/geocode/"
        private val TAG = HttpRetrofitClientBase::class.java.simpleName

        private var instance: HttpRetrofitClientBase? = null

        fun getInstance(): HttpRetrofitClientBase {
            if (instance == null) {
                instance = HttpRetrofitClientBase()
            }
            return instance as HttpRetrofitClientBase
        }
    }


}
