package com.ocean.checknetconnect.ref1

import android.content.Context
import android.widget.Toast
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.OnLifecycleEvent

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