package sang.thai.tran.travelcompanion.retrofit


import android.annotation.SuppressLint
import android.text.TextUtils
import android.util.Log
import com.google.gson.GsonBuilder
import io.reactivex.schedulers.Schedulers
import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.OkHttpClient
import okhttp3.RequestBody
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory
import sang.thai.tran.travelcompanion.BuildConfig
import sang.thai.tran.travelcompanion.model.Response
import sang.thai.tran.travelcompanion.model.UserInfo
import sang.thai.tran.travelcompanion.utils.AppConstant
import sang.thai.tran.travelcompanion.utils.AppConstant.API_UPDATE
import sang.thai.tran.travelcompanion.utils.AppUtils.getBase64
import sang.thai.tran.travelcompanion.utils.ApplicationSingleton
import java.io.File
import java.io.FileInputStream
import java.security.cert.CertificateException
import java.util.*
import java.util.concurrent.TimeUnit
import java.util.concurrent.TimeUnit.MILLISECONDS
import javax.net.ssl.SSLContext
import javax.net.ssl.X509TrustManager

/*
 * Created by Sang Heo Map
 */
class HttpRetrofitClientBase {
    private val REQUEST_TIMEOUT_FOR_UPLOADING: Long = 60000
    private var retrofit: Retrofit? = null
    private val retrofitFile: Retrofit? = null
    private var retrofitGoogle: Retrofit? = null
    private val isLocalUrl: Boolean = false

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
        okHttpClientBuilder.readTimeout(CONNECT_TIMEOUT, TimeUnit.MILLISECONDS)
        okHttpClientBuilder.writeTimeout(CONNECT_TIMEOUT, TimeUnit.MILLISECONDS)
        if (BuildConfig.DEBUG) {
            okHttpClientBuilder.addInterceptor(interceptor)
            //            okHttpClientBuilder.addNetworkInterceptor(new StethoInterceptor());
        }
        try {
            // Create a trust manager that does not validate certificate chains
            val trustAllCerts = arrayOf<X509TrustManager>(object : X509TrustManager {
                @SuppressLint("TrustAllX509TrustManager")
                override fun checkClientTrusted(chain: Array<java.security.cert.X509Certificate>, authType: String) {
                }

                @Throws(CertificateException::class)
                override fun checkServerTrusted(chain: Array<java.security.cert.X509Certificate>?, authType: String) {
                    if (chain == null) {
                        throw IllegalArgumentException("checkServerTrusted: X509Certificate array is null")
                    }

                    if (chain.size <= 0) {
                        throw IllegalArgumentException("checkServerTrusted: X509Certificate is empty")
                    }
                    try {
                        chain[0].checkValidity()
                    } catch (e: Exception) {
                        throw CertificateException("Certificate not valid or trusted.")
                    }

                }

                override fun getAcceptedIssuers(): Array<java.security.cert.X509Certificate>? {
                    return null
                }
            })

            // Install the all-trusting trust manager
            val sslContext = SSLContext.getInstance("SSL")
            sslContext.init(null, trustAllCerts, java.security.SecureRandom())

        } catch (e: Exception) {
            throw RuntimeException(e)
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

    private fun getRetrofitGoogle(): Retrofit? {
        if (retrofitGoogle == null) {
            retrofitGoogle = initialize(GOOGLE_API_URL)
        }
        return retrofitGoogle
    }


    fun loginFunction(url: String, params: Map<String, String>?, listener: BaseObserver<sang.thai.tran.travelcompanion.model.Response>) {
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

    fun executePost(url: String, token : String, userInfo: UserInfo?, listener: BaseObserver<sang.thai.tran.travelcompanion.model.Response>) {
        if (userInfo == null) {
            return
        }
        val service = getRetrofit()!!.create(APIInterface::class.java)
        var serviceObservable = service.postRegister(url, userInfo)
                .subscribeOn(Schedulers.newThread())
                .observeOn(Schedulers.computation())
                .timeout(CONNECT_TIMEOUT, MILLISECONDS)
        if (!TextUtils.isEmpty(token) && url == API_UPDATE) {
            serviceObservable = service.postUpdate(url, token, userInfo)
                    .subscribeOn(Schedulers.newThread())
                    .observeOn(Schedulers.computation())
                    .timeout(CONNECT_TIMEOUT, MILLISECONDS)
        }
        serviceObservable.subscribe(listener)
    }

    fun executeUpload(urlParam: String, imageFile: String, listener: BaseObserver<Response>) {
        val file = File(imageFile)
        if (!file.exists() || ApplicationSingleton.getInstance().token == null) {
            listener.onFailure(Exception("File is not exist"), "File is not exist")
            return
        }
        val params = HashMap<String, String>()
        params[AppConstant.API_PARAM_ACCESS_TOKEN] = ApplicationSingleton.getInstance().token
        val requestBody = RequestBody.create(MediaType.parse("image/*"), file)
        val input =  FileInputStream(File(imageFile))
        val buf =  ByteArray(input.available())
        while (input.read(buf) != -1) {

        }
//        val base64 = Base64.encodeToString(buf, Base64.DEFAULT)
//        val base64 = Base64.getEncoder().encodeToString(buf)
        Log.d("Sang","base64: " + getBase64(buf))
        val requestBodySang: RequestBody = RequestBody.create(MediaType.parse("multipart/mixed"), getBase64(buf))
        val requestBodyTmp = MultipartBody.Builder()
                .setType(MultipartBody.FORM)
//                .addFormDataPart(API_PARAM_ACCESS_TOKEN, ApplicationSingleton.getInstance().token)
                .addFormDataPart("File", file.name, requestBody)
                .build()
//application/octet-stream
//        application/java-vm
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
        private val CONNECT_TIMEOUT: Long = 15000   // 30 seconds
        private val GOOGLE_API_URL = "https://maps.googleapis.com/maps/api/geocode/"
        private val GOOGLE_API_URL_OUTPUT = "json"
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
