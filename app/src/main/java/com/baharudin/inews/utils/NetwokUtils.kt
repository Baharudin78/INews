package com.baharudin.inews.utils

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities

fun isNetWorkConnected(context: Context) : Boolean {
    val connetivity = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
    val activityNetwork = connetivity.activeNetwork
    val networkCapabilities = connetivity.getNetworkCapabilities(activityNetwork)
    return networkCapabilities != null &&
            networkCapabilities.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)
}