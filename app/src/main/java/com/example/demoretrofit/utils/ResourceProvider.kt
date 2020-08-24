package com.example.demoretrofit.utils

import android.content.Context
import androidx.core.content.ContextCompat

class ResourceProvider(var mContext: Context) {
    
    fun getString(resId: Int): String {
        return mContext.getString(resId)
    }

    fun getString(resId: Int, value: String): String {
        return mContext.getString(resId, value)
    }

    fun getColor(resId: Int): Int {
        return ContextCompat.getColor(mContext, resId)
    }

}