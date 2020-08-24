package com.example.demoretrofit.view.interfaces

import android.view.View

interface OnItemClickListener {
    fun onItemClick(pos: Int, item: View): Unit
}