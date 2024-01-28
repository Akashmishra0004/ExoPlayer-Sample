package com.loyalty.player.utils

import android.content.Context
import android.net.ConnectivityManager


internal object NetworkUtil {
    fun getConnectivityStatusString(context: Context): Boolean? {
        var status: String? = null

        var isConnected = false
        val connectivityManager =
            context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val activeNetInfo = connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE)
        val isConnected1 = activeNetInfo != null && activeNetInfo.isConnected

        isConnected = if (connectivityManager.activeNetworkInfo !=null) {
            when (connectivityManager.activeNetworkInfo?.type) {
                ConnectivityManager.TYPE_WIFI -> {
                    true
                }
                ConnectivityManager.TYPE_MOBILE -> {
                    println("connectivityManager.activeNetworkInfo?.type-->${connectivityManager.activeNetworkInfo?.subtype}")
                    true
                }
                else -> {
                    false
                }
            }
        }else{
            false
        }
        return isConnected
    }
}