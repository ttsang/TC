package sang.thai.tran.travelcompanion.retrofit;


import android.annotation.SuppressLint;
import android.support.annotation.NonNull;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.IOException;
import java.security.cert.CertificateException;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import javax.net.ssl.SSLContext;
import javax.net.ssl.X509TrustManager;

import io.reactivex.Observable;
import io.reactivex.schedulers.Schedulers;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;
import sang.thai.tran.travelcompanion.BuildConfig;

import static java.util.concurrent.TimeUnit.MILLISECONDS;

/*
 * Created by Sang Heo Map
 */
public class HttpRetrofitClientBase {
    private static final long CONNECT_TIMEOUT = 30000;   // 30 seconds
    private static final String GOOGLE_API_URL = "https://maps.googleapis.com/maps/api/geocode/";
    private static final String GOOGLE_API_URL_OUTPUT = "json";
    private static final String TAG = HttpRetrofitClientBase.class.getSimpleName();
    private Retrofit retrofit;
    private Retrofit retrofitFile;
    private Retrofit retrofitGoogle;
    private boolean isLocalUrl;

    private static HttpRetrofitClientBase instance;

    public static HttpRetrofitClientBase getInstance() {
        if (instance == null) {
            instance = new HttpRetrofitClientBase();
        }
        return instance;
    }

    public Retrofit initialize(String url) {
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

        Gson gson = new GsonBuilder()
                .setDateFormat("yyyy-MM-dd'T'HH:mm:ssZ")
                .setLenient()
                .create();
        OkHttpClient client = buildClient(interceptor);

        // Retrofit
        return new Retrofit.Builder()
                .baseUrl(url)
                .client(client)
                .addConverterFactory(ScalarsConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();
    }

    public String getBaseUrl() {
        return "http://assistant.uniquetour.biz/";
    }


    private OkHttpClient buildClient(HttpLoggingInterceptor interceptor) {
        final OkHttpClient.Builder okHttpClientBuilder = new OkHttpClient.Builder();
        okHttpClientBuilder.connectTimeout(30, TimeUnit.SECONDS);
        okHttpClientBuilder.readTimeout(CONNECT_TIMEOUT, TimeUnit.MILLISECONDS);
        okHttpClientBuilder.writeTimeout(CONNECT_TIMEOUT, TimeUnit.MILLISECONDS);
        if (BuildConfig.DEBUG) {
            okHttpClientBuilder.addInterceptor(interceptor);
//            okHttpClientBuilder.addNetworkInterceptor(new StethoInterceptor());
        }
        try {
            // Create a trust manager that does not validate certificate chains
            final X509TrustManager[] trustAllCerts = new X509TrustManager[]{
                    new X509TrustManager() {
                        @SuppressLint("TrustAllX509TrustManager")
                        @Override
                        public void checkClientTrusted(java.security.cert.X509Certificate[] chain, String authType) {
                        }

                        @Override
                        public void checkServerTrusted(java.security.cert.X509Certificate[] chain, String authType) throws CertificateException {
                            if (chain == null) {
                                throw new IllegalArgumentException("checkServerTrusted: X509Certificate array is null");
                            }

                            if (!(chain.length > 0)) {
                                throw new IllegalArgumentException("checkServerTrusted: X509Certificate is empty");
                            }
                            try {
                                chain[0].checkValidity();
                            } catch (Exception e) {
                                throw new CertificateException("Certificate not valid or trusted.");
                            }
                        }

                        @Override
                        public java.security.cert.X509Certificate[] getAcceptedIssuers() {
                            return null;
                        }
                    }
            };

            // Install the all-trusting trust manager
            final SSLContext sslContext = SSLContext.getInstance("SSL");
            sslContext.init(null, trustAllCerts, new java.security.SecureRandom());
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

        } catch (Exception e) {
            throw new RuntimeException(e);
        }

//        okHttpClientBuilder.connectionSpecs(Collections.singletonList(spec));
        okHttpClientBuilder.addNetworkInterceptor(new Interceptor() {
            @Override
            public Response intercept(@NonNull Chain chain) throws IOException {

                Request original = chain.request();
                // Request customization: add request headers
                Request.Builder requestBuilder = original.newBuilder();
//                        .addHeader("Content-Type", "application/json")
//                        .addHeader("Accept", "*/*");

//                String authorization = AppHelper.sp.getString(API_AUTHORIZATION_CODE,"");
//                if (!TextUtils.isEmpty(authorization)) {
//                    requestBuilder.addHeader(AUTHORIZATION, authorization);
//                }
                Request request = requestBuilder.build();
                Response response = chain.proceed(request);
                Log.d(TAG, "response Code : " + response);
                return response;
            }
        });

        return okHttpClientBuilder.build();
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

    public Retrofit getRetrofit() {
        if (retrofit == null) {
            retrofit = initialize(getBaseUrl());
        }
        return retrofit;
    }

    private Retrofit getRetrofitGoogle() {
        if (retrofitGoogle == null) {
            retrofitGoogle = initialize(GOOGLE_API_URL);
        }
        return retrofitGoogle;
    }


    public void loginFunction(final String url, final Map<String, String> params, final BaseObserver<sang.thai.tran.travelcompanion.model.Response> listener) {
        if (params == null ) {
            return;
        }
        final APIInterface service = getRetrofit().create(APIInterface.class);
        final Observable<sang.thai.tran.travelcompanion.model.Response> serviceObservable = service.postLogin(url, params)
                .subscribeOn(Schedulers.newThread())
                .observeOn(Schedulers.computation())
                .timeout(CONNECT_TIMEOUT, MILLISECONDS);
        serviceObservable.subscribe(listener);
    }

    public void executePost(final String url, final Map<String, String> params, final BaseObserver<String> listener) {
        if (params == null ) {
            return;
        }
        final APIInterface service = getRetrofit().create(APIInterface.class);
        final Observable<String> serviceObservable = service.post(url, params)
                .subscribeOn(Schedulers.newThread())
                .observeOn(Schedulers.computation())
                .timeout(CONNECT_TIMEOUT, MILLISECONDS);
        serviceObservable.subscribe(listener);
    }


}
