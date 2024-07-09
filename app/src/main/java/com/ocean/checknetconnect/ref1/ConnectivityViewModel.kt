package com.ocean.checknetconnect.ref1

import android.app.Application
import androidx.lifecycle.AndroidViewModel

class ConnectivityViewModel(application: Application): AndroidViewModel(application) {

    val connectivityLiveData = ConnectivityLiveData(application)
}