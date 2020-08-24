package com.example.demoretrofit.app

import android.annotation.SuppressLint
import android.app.Application
import android.content.ComponentCallbacks2
import android.content.Context
import android.content.Intent
import com.example.demoretrofit.BuildConfig
import com.example.demoretrofit.utils.ApplicationLifecycleHandler
import com.example.demoretrofit.utils.ResourceProvider
import java.lang.Exception

class AppController : Application() {

    private var mResourceProvider: ResourceProvider? = null
    private var user_DOMAIN_NAME: String? = null
    private var androidDefaultUEH: Thread.UncaughtExceptionHandler? = null

    companion object {
        @SuppressLint("StaticFieldLeak")
        var instance: AppController? = null
        @SuppressLint("StaticFieldLeak")
        var context: Context? = null
            private set
        internal val TAG: String = AppController::class.java!!.getSimpleName()
    }

    override fun onCreate() {
        try {
            super.onCreate()
            val applicationLifecycleHandler: ApplicationLifecycleHandler =
                ApplicationLifecycleHandler()
            registerActivityLifecycleCallbacks(applicationLifecycleHandler)
            registerComponentCallbacks(applicationLifecycleHandler)

            instance = this
            context = this.applicationContext

            if (!BuildConfig.DEBUG) {
                androidDefaultUEH = Thread.getDefaultUncaughtExceptionHandler()
                Thread.setDefaultUncaughtExceptionHandler(handler)
            }

        } catch (e: Exception) {
            e.printStackTrace()
        }
    }


    //Instance creation for all Activity
    @Synchronized
    fun getInstance(): AppController = instance!!


    private val handler = Thread.UncaughtExceptionHandler { thread, ex ->
        //androidDefaultUEH.uncaughtException(thread, ex);
        /* ExceptionTrack.getInstance().TrackLog(ex, Constants.SERVER_URL, 1)
         if (SharedPreferenceData.getInstance().getDataInSharedPreferences(
                 getContext(),
                 Constants.USER_MATRID
             ).length() !== 0
         ) {
             startActivity(
                 Intent(getContext(), HomeScreenActivity::class.java).addFlags(
                     Intent.FLAG_ACTIVITY_CLEAR_TOP
                             or Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
                 )
             )
             System.exit(1)
         } else {
             startActivity(
                 Intent(getContext(), Splash::class.java).addFlags(
                     (Intent.FLAG_ACTIVITY_CLEAR_TOP
                             or Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK)
                 )
             )
             System.exit(1)
         }*/
    }

    /* pls you cannot access resource( like R.drawable) from viewmodel instead of that use this function*/
    fun getResourceProvider(): ResourceProvider {
        if (mResourceProvider == null)
            mResourceProvider = ResourceProvider(this)

        return mResourceProvider!!
    }


    override fun onTrimMemory(level: Int) {
        super.onTrimMemory(ComponentCallbacks2.TRIM_MEMORY_UI_HIDDEN)
    }

    override fun onLowMemory() {
        System.gc()
        super.onLowMemory()
    }

    fun getContext(): Context {
        if (instance == null)
            instance = AppController()
        return instance!!
    }

    fun baseContext(): Context {
        return context!!
    }

    fun getUser_DOMAIN_NAME(): String {
        return user_DOMAIN_NAME!!
    }

    fun setUser_DOMAIN_NAME(user_DOMAIN_NAME: String) {
        this.user_DOMAIN_NAME = user_DOMAIN_NAME
    }


    override fun attachBaseContext(base: Context) {
        super.attachBaseContext(base)
        if (BuildConfig.DEBUG)
            androidx.multidex.MultiDex.install(this)
    }


}