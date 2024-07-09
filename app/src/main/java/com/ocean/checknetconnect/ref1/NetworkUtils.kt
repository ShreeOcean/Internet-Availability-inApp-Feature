package com.ocean.checknetconnect.ref1

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities

/** The NetworkUtils class is designed to check for internet connectivity in an Android application. */
object NetworkUtils {

    fun isNetworkAvailable(context: Context): Boolean{
        /** This line retrieves an instance of the ConnectivityManager system service,
         *  which is used to access network state and perform network-related operations. **/
        val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

        /** The activeNetwork property returns the currently active network or null if there is no active network.
         * If there is no active network, the function returns false. */
        val network = connectivityManager.activeNetwork ?: return false

        /** The getNetworkCapabilities method returns an instance of NetworkCapabilities for the given network.
         * If the capabilities are not available, the function returns false. */
        val activeNetwork = connectivityManager.getNetworkCapabilities(network)?: return false

        /** Checking network transport types */
        return  when{
            activeNetwork.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> true
            activeNetwork.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> true
            else -> false
        }
    }
}