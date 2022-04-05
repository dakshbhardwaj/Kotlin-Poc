package com.example.findmyage

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build

object Constants {

    const val API_ID : String = "ce15a3f0b53146d9531d520e688e709d";
    const val BASE_URL: String = "https://api.openweathermap.org/data/"
    const val METRIC_UNIT: String = "metric"

    fun isNetWorkAvailable(context: Context): Boolean {
        val connectivityManager =
            context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager;

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            val network = connectivityManager.activeNetwork ?: return false
            val activityManager =
                connectivityManager.getNetworkCapabilities(network) ?: return false

            return when {
                activityManager.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> true
                activityManager.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> true
                activityManager.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) -> true
                else -> return false
            }
        } else {
            val networkInfo = connectivityManager.activeNetworkInfo
            return networkInfo != null && networkInfo.isConnectedOrConnecting
        }
    }
}