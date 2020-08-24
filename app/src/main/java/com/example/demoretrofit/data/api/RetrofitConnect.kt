package com.example.demoretrofit.data.api

import android.annotation.SuppressLint
import android.os.Build
import android.util.Log
import com.example.demoretrofit.utils.Constants
import com.example.demoretrofit.utils.ExceptionTrack
import com.example.demoretrofit.utils.IntegerDefault0Adapter
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import okhttp3.ConnectionSpec
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.TlsVersion
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.io.IOException
import java.net.UnknownHostException
import java.security.KeyStore
import java.util.*
import java.util.concurrent.TimeUnit
import javax.net.ssl.SSLContext
import javax.net.ssl.TrustManagerFactory
import javax.net.ssl.X509TrustManager

class RetrofitConnect {

    private var httpClient: OkHttpClient.Builder? = null
    private var httpClientGlide: OkHttpClient.Builder? = null
    private var retroApiServices: ApiServices? = null
    private var retryCount = 0


    companion object {
        //        @SuppressLint("StaticFieldLeak")
//        internal val INSTANCE = RetrofitConnect()
//TLS 1.2 ver support
        private fun enableTls12OnPreLollipop(client: OkHttpClient.Builder): OkHttpClient.Builder? {

            if (Build.VERSION.SDK_INT >= 16 && Build.VERSION.SDK_INT < 22) {
                try {
                    val sc = SSLContext.getInstance("TLSv1.2")
                    sc.init(null, null, null)
                    val trustManagerFactory =
                        TrustManagerFactory.getInstance(TrustManagerFactory.getDefaultAlgorithm())
                    trustManagerFactory.init(null as KeyStore?)
                    val trustManagers = trustManagerFactory.trustManagers
                    check(!(trustManagers.size != 1 || trustManagers[0] !is X509TrustManager)) {
                        "Unexpected default trust managers:" + Arrays.toString(
                            trustManagers
                        )
                    }
                    val trustManager = trustManagers[0] as X509TrustManager
                    client.sslSocketFactory(Tls12SocketFactory(sc.socketFactory), trustManager)
                    val cs = ConnectionSpec.Builder(ConnectionSpec.MODERN_TLS)
                        .tlsVersions(TlsVersion.TLS_1_2)
                        .build()
                    val specs = ArrayList<ConnectionSpec>()
                    specs.add(cs)
                    specs.add(ConnectionSpec.COMPATIBLE_TLS)
                    specs.add(ConnectionSpec.CLEARTEXT)
                    client.connectionSpecs(specs)
                } catch (exc: Exception) {
                    exc.message
//                    ExceptionTrack.instance.TrackLog(exc)
                }

            }
            return client
        }


    }

    private object Holder {
        internal val INSTANCE = RetrofitConnect()
    }

    fun getInstance(): RetrofitConnect {
        return Holder.INSTANCE
    }

    /*Initi retrofit with base Url*/
    fun retrofit(UrlBasePath: String): ApiServices {

        if (httpClient == null) {

            val interceptor = HttpLoggingInterceptor()
            interceptor.level = HttpLoggingInterceptor.Level.BODY
            val client = OkHttpClient.Builder()
                .addInterceptor(Interceptor { chain ->
                    val original = chain.request()
                    //header
                    val request = original.newBuilder()
                        .header("Content-Type", "application/json")
                        .method(original.method(), original.body())
                        .build()
                    return@Interceptor chain.proceed(request)
                })
                .addInterceptor(interceptor)
                //  .connectTimeout(100, TimeUnit.SECONDS)
                // .readTimeout(100, TimeUnit.SECONDS)
                .build()

            httpClient = OkHttpClient.Builder()
            httpClient?.connectTimeout(Constants.TIMEOUTCONNECTION, TimeUnit.MILLISECONDS)
            httpClient?.readTimeout(Constants.TIMEOUTSOCKET, TimeUnit.MILLISECONDS)
            httpClient?.addInterceptor(interceptor)
            //httpClient.addNetworkInterceptor(new StethoInterceptor());
            //httpClient.retryOnConnectionFailure(false);
            enableTls12OnPreLollipop(httpClient!!)

            val logging = HttpLoggingInterceptor()
            logging.level = HttpLoggingInterceptor.Level.BODY
            httpClient!!.addInterceptor(logging)

        }

        retroApiServices = Retrofit.Builder().baseUrl(UrlBasePath)
            .addConverterFactory(ToStringConverterFactory())
            .addConverterFactory(GsonConverterFactory.create(gsonMapper()))
            .client(httpClient!!.build())
            .build().create(ApiServices::class.java)
        return retroApiServices!!
    }

    fun gsonMapper(): Gson {
        return GsonBuilder()
            .registerTypeAdapter(Int::class.java, IntegerDefault0Adapter())
            .registerTypeAdapter(Int::class.javaPrimitiveType, IntegerDefault0Adapter())
            .serializeNulls()
            .create()
    }

    fun <T> AddToEnqueue(
        baseCall: retrofit2.Call<T>,
        listener: ApiRequestListener,
        ReqType: String
    ) {
//        baseCall.enqueue(object : retrofit2.Callback<T> {
//            //pls
//            // var  listener: ApiRequestListener=this@MainModel
//            override fun onResponse(call: retrofit2.Call<T>, response: retrofit2.Response<T>) {
//                try {
//                    if (null != response.body()) {
//
//                        if (response.code() == 200) {
//                            listener.onReceiveResult(ReqType, response)
//                        } else {
//                            ExceptionTrack.instance
//                                .TrackFailure(
//                                    false,
//                                    null!!,
//                                    "" + ReqType,
//                                    "" + call.request().url()
//                                )
////                            GAAnalyticsOperations.getInstance().sendAnalyticsEvent(
////                                CommunityApplication.getInstance().getContext(),
////                                CommunityApplication.getInstance().getResources().getString(R.string.category_API_Service_Error),
////                                CommunityApplication.getInstance().getResources().getString(R.string.action_impression),
////                                "ServerResponseException",
////                                1
////                            )
//                            retryCount++
//                            if (retryCount <= 1) {
//                                call.clone().enqueue(this)
//                            } else {
//                                listener.onReceiveError(ReqType, "2")
//                                call.cancel()
//                            }
//                        }
//                    } else {
//                        ExceptionTrack.instance
//                            .TrackFailure(false, null!!, "" + ReqType, "" + call.request().url())
////                        GAAnalyticsOperations.getInstance().sendAnalyticsEvent(
////                            CommunityApplication.getInstance().getContext(),
////                            CommunityApplication.getInstance().getResources().getString(R.string.category_API_Service_Error),
////                            CommunityApplication.getInstance().getResources().getString(R.string.action_impression),
////                            "ServerResponseException",
////                            2
////                        )
//                        retryCount++
//                        if (retryCount <= 1) {
//                            call.clone().enqueue(this)
//                        } else {
//                            listener.onReceiveError(ReqType, "3")
//                            call.cancel()
//                        }
//                    }
//                } catch (e: Exception) {
//                    ExceptionTrack.instance.TrackLog(e, "" + call.request().url(), ReqType.toInt())
//                }
//
//            }
//
//            override fun onFailure(call: retrofit2.Call<T>, t: Throwable) {
//                try {
//                    if (call.isCanceled || "Canceled" == t.message)
//                        return
//
////                    GAAnalyticsOperations.getInstance().sendAnalyticsEvent(
////                        CommunityApplication.getInstance().getContext(),
////                        CommunityApplication.getInstance().getResources().getString(R.string.category_API_Service_Error),
////                        CommunityApplication.getInstance().getResources().getString(R.string.action_impression),
////                        t.message,
////                        1
////                    )
//
//                    if (t is UnknownHostException)
//                        ExceptionTrack.instance.TrackFailure(
//                            true,
//                            t,
//                            "" + ReqType,
//                            "" + call.request().url()
//                        )
//                    else
//                        ExceptionTrack.instance.TrackFailure(
//                            false,
//                            t,
//                            "" + ReqType,
//                            "" + call.request().url()
//                        )
//                    retryCount++
//                    if (retryCount <= 1) {
//                        call.clone().enqueue(this)
//                    } else {
//                        listener.onReceiveError(ReqType, "1")
//                        call.cancel()
//                    }
//                } catch (e: Exception) {
//                    ExceptionTrack.instance.TrackLog(e, "" + call.request().url(), ReqType.toInt())
//                }
//
//            }
//
//        })


        baseCall.enqueue(object : Callback<T> {

            override fun onFailure(call: Call<T>?, t: Throwable?) {
                Log.v("retrofit", "call failed" + t.toString())
            }

            override fun onResponse(call: Call<T>?, response: Response<T>?) {

                Log.i("res", call.toString())

                if (response?.body() != null) {
                    if (response.code() == 200) {
                        listener.onReceiveResult(ReqType, response)
                    }
                }
            }

        })
    }


    @Throws(IOException::class)
    fun <T> dataConvertor(response: retrofit2.Response<*>, cls: Class<T>): T? {
        val cmmLoginParser: T?
        if (response.body() is String) {
            cmmLoginParser = gsonMapper().fromJson(response.body()!!.toString(), cls)
        } else {
            cmmLoginParser = (response as retrofit2.Response<T>).body()
        }
        return cmmLoginParser
    }


}