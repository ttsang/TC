package sang.thai.tran.travelcompanion.retrofit


import android.annotation.SuppressLint
import android.util.Log

import com.google.gson.Gson
import com.google.gson.GsonBuilder

import java.io.IOException
import java.security.cert.CertificateException
import java.util.concurrent.TimeUnit

import javax.net.ssl.SSLContext
import javax.net.ssl.X509TrustManager

import io.reactivex.Observable
import io.reactivex.schedulers.Schedulers
import okhttp3.*
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory
import sang.thai.tran.travelcompanion.BuildConfig
import sang.thai.tran.travelcompanion.model.UserInfo
import sang.thai.tran.travelcompanion.utils.ApplicationSingleton
import java.io.File
import java.util.HashMap

import java.util.concurrent.TimeUnit.MILLISECONDS

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

    fun initialize(url: String): Retrofit {
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
            // Create an ssl socket factory with our all-trusting manager
            //            final SSLSocketFactory sslSocketFactory = sslContext.getSocketFactory();
            //            okHttpClientBuilder.sslSocketFactory(sslSocketFactory, new X509TrustManager() {
            //                @Override
            //                public void checkClientTrusted(java.security.cert.X509Certificate[] chain, String authType) throws CertificateException {
            //                }
            //
            //                @Override
            //                public void checkServerTrusted(java.security.cert.X509Certificate[] chain, String authType) throws CertificateException {
            //                    if (chain == null) {
            //                        throw new IllegalArgumentException("checkServerTrusted: X509Certificate array is null");
            //                    }
            //
            //                    if (!(chain.length > 0)) {
            //                        throw new IllegalArgumentException("checkServerTrusted: X509Certificate is empty");
            //                    }
            //                    try {
            //                        chain[0].checkValidity();
            //                    } catch (Exception e) {
            //                        throw new CertificateException("Certificate not valid or trusted.");
            //                    }
            //                }
            //
            //                @Override
            //                public java.security.cert.X509Certificate[] getAcceptedIssuers() {
            //                    return new X509Certificate[0];
            //                }
            //            });
            //            okHttpClientBuilder.hostnameVerifier(new HostnameVerifier() {
            //                @Override
            //                public boolean verify(String hostname, SSLSession session) {
            //                    Log.d(TAG, "verify hostname: " + hostname);
            //                    return true;
            //                }
            //            });

        } catch (e: Exception) {
            throw RuntimeException(e)
        }

        //        okHttpClientBuilder.connectionSpecs(Collections.singletonList(spec));
        okHttpClientBuilder.addNetworkInterceptor { chain ->
            val original = chain.request()
            // Request customization: add request headers
            val requestBuilder = original.newBuilder()
            //                        .addHeader("Content-Type", "application/json")
            //                        .addHeader("Accept", "*/*");

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

    //    private ConnectionSpec spec = new ConnectionSpec.Builder(ConnectionSpec.COMPATIBLE_TLS)
    //            .supportsTlsExtensions(true)
    //            .tlsVersions(TlsVersion.TLS_1_2, TlsVersion.TLS_1_1, TlsVersion.TLS_1_0)
    //            .cipherSuites(
    //                    CipherSuite.TLS_ECDHE_ECDSA_WITH_AES_128_GCM_SHA256,
    //                    CipherSuite.TLS_ECDHE_RSA_WITH_AES_128_GCM_SHA256,
    //                    CipherSuite.TLS_DHE_RSA_WITH_AES_128_GCM_SHA256,
    //                    CipherSuite.TLS_ECDHE_ECDSA_WITH_AES_256_CBC_SHA,
    //                    CipherSuite.TLS_ECDHE_ECDSA_WITH_AES_128_CBC_SHA,
    //                    CipherSuite.TLS_ECDHE_RSA_WITH_AES_128_CBC_SHA,
    //                    CipherSuite.TLS_ECDHE_RSA_WITH_AES_256_CBC_SHA,
    //                    CipherSuite.TLS_ECDHE_ECDSA_WITH_RC4_128_SHA,
    //                    CipherSuite.TLS_ECDHE_RSA_WITH_RC4_128_SHA,
    //                    CipherSuite.TLS_DHE_RSA_WITH_AES_128_CBC_SHA,
    //                    CipherSuite.TLS_DHE_DSS_WITH_AES_128_CBC_SHA,
    //                    CipherSuite.TLS_DHE_RSA_WITH_AES_256_CBC_SHA)
    //            .build();

    //    public CacheResponse(File cacheDirectory) throws Exception {
    //        int cacheSize = 10 * 1024 * 1024; // 10 MiB
    //        Cache cache = new Cache(cacheDirectory, cacheSize);
    //
    //        client = new OkHttpClient.Builder()
    //                .cache(cache)
    //                .build();
    //    }

    fun getRetrofit(): Retrofit? {
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

    fun executePost(url: String, params: UserInfo?, listener: BaseObserver<sang.thai.tran.travelcompanion.model.Response>) {
        if (params == null) {
            return
        }
        val service = getRetrofit()!!.create(APIInterface::class.java)
        val serviceObservable = service.postRegister(url, params)
                .subscribeOn(Schedulers.newThread())
                .observeOn(Schedulers.computation())
                .timeout(CONNECT_TIMEOUT, MILLISECONDS)
        serviceObservable.subscribe(listener)
    }

    fun executeUpload(urlParam: String, imageFile: String, listener: BaseObserver<String>) {
        val file = File(imageFile)
        if (!file.exists()) {
            listener.onFailure(Exception("File is not exist"), "File is not exist")
            return
        }
        val params = HashMap<String, String>()
        val requestBody = RequestBody.create(MediaType.parse("multipart/form-data"), file)
        val requestBodyTmp = MultipartBody.Builder()
                .setType(MultipartBody.FORM)
                .addFormDataPart("accessToken", ApplicationSingleton.getInstance().token)
                .addFormDataPart("data", file.name, requestBody)
                .build()

        val service = getRetrofit()!!.create(APIInterface::class.java)
        service.uploadImage(urlParam, requestBodyTmp)
                .subscribeOn(Schedulers.newThread())
                .observeOn(Schedulers.computation())
                .timeout(REQUEST_TIMEOUT_FOR_UPLOADING, MILLISECONDS)
                .subscribe(listener)
        listener.setUrl(urlParam)
        listener.setParams(params)
    }


    companion object {
        private val CONNECT_TIMEOUT: Long = 30000   // 30 seconds
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
