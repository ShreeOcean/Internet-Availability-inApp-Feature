package com.ocean.checknetconnect.ref1

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.widget.Toast

class NetworkChangeReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context?, intent: Intent?) {
        if (context?.let { NetworkUtils.isNetworkAvailable(it) } == true){
            Toast.makeText(context, "Internet is available", Toast.LENGTH_SHORT).show()
        }else{
            Toast.makeText(context, "No internet connection", Toast.LENGTH_SHORT).show()
        }
    }
}