package com.ocean.checknetconnect.old

import android.content.Context
import android.widget.Toast
import androidx.lifecycle.LifecycleObserver
import com.ocean.checknetconnect.ref1.NetworkUtils

class ConnectivityObserver(private val context: Context): LifecycleObserver {

//    @OnLifecycleEvent(Lifecycle.Event.ON_START)
//    fun onEnterForeground(){
//        checkNetwork()
//    }

    private fun checkNetwork() {
        if (NetworkUtils.isNetworkAvailable(context)){
            Toast.makeText(context, "Internet is available", Toast.LENGTH_SHORT).show()
        }else{
            Toast.makeText(context, "No internet connection", Toast.LENGTH_SHORT).show()
        }
    }
}