package com.example.demoretrofit.utils

import android.app.Activity
import android.app.Application
import android.app.KeyguardManager
import android.content.ComponentCallbacks2
import android.content.Context
import android.content.res.Configuration
import android.os.Bundle
import android.util.Log
import com.example.demoretrofit.app.AppController

class ApplicationLifecycleHandler : Application.ActivityLifecycleCallbacks, ComponentCallbacks2 {

    companion object {
        val TAG: String = ApplicationLifecycleHandler::class.java.simpleName
        var isInBackground: Boolean = false
    }

    override fun onActivityPaused(p0: Activity) {

        if (AppController.context != null) {

            val myKM =
                AppController.context!!.getSystemService(Context.KEYGUARD_SERVICE) as KeyguardManager
            if (myKM.inKeyguardRestrictedInputMode()) {
                isInBackground = true
//                if (AppController.getInstance().CHATVIEWMODEL != null)
//                    AppController.getInstance().CHATVIEWMODEL.appBackgound()
                Log.d(TAG, "app went to locked")
            } else {
                Log.d(TAG, "app went to unlocked")
            }
        }
    }

    override fun onActivityStarted(p0: Activity) {
        Log.d(TAG, "app went to Destoryed")
    }

    override fun onActivityDestroyed(p0: Activity) {
        Log.d(TAG, "app went to Destoryed")
    }

    override fun onActivitySaveInstanceState(p0: Activity, p1: Bundle) {
        Log.d(TAG, "app went to Destoryed")
    }

    override fun onActivityStopped(p0: Activity) {
        Log.d(TAG, "app went to Destoryed")
    }

    override fun onActivityCreated(p0: Activity, p1: Bundle?) {
        Log.d(TAG, "app went to Destoryed")
    }

    override fun onActivityResumed(p0: Activity) {
        if (isInBackground) {
            Log.d(TAG, "app went to foreground")
            isInBackground = false
//            if (CommunityApplication.getInstance().CHATVIEWMODEL != null) {
//                CommunityApplication.getInstance().CHATVIEWMODEL.chatNotify()
//            }
        }
    }

    /*Control Memory Likage */
    override fun onLowMemory() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onConfigurationChanged(p0: Configuration) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onTrimMemory(p0: Int) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }


}