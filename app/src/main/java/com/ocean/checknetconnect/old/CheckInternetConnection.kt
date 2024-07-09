package com.ocean.checknetconnect.old

import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response
import java.lang.IllegalArgumentException

enum class ConnectionState {
    CONNECTED, SLOW, DISCONNECTED
}

interface ConnectivityListener {
    fun onConnectionState(state: ConnectionState)
}

class CheckInternetConnection(
    private val lifecycleOwner: LifecycleOwner?,
    private val url: String = "https://www.google.com",
    private val leastLifecycleState: Lifecycle.State = Lifecycle.State.RESUMED
){
    var connectivityListener: ConnectivityListener? = null
        set(value) {
            if (value != null){
                checkConnection(lifecycleOwner, url, leastLifecycleState){
                    value.onConnectionState(it)
                }
            }
            field = value
        }
}

fun checkConnection(
    lifecycleOwner: LifecycleOwner?,
    url: String = "",
    leastLifecycleState: Lifecycle.State = Lifecycle.State.RESUMED
){
    if (lifecycleOwner == null) throw java.lang.IllegalArgumentException("Unable to find lifecycle scope.")

    checkConnection(lifecycleOwner, url, leastLifecycleState){
        (lifecycleOwner as? ConnectivityListener)?.onConnectionState(it)
    }
}

fun checkConnection(
    lifecycleOwner: LifecycleOwner?,
    url: String = "",
    leastLifecycleState: Lifecycle.State = Lifecycle.State.RESUMED,
    onConnectionState: (connectionState: ConnectionState) -> Unit
){
    val scope = lifecycleOwner?.lifecycleScope
        ?: throw java.lang.IllegalArgumentException("Unable to find lifecycle scope")

    when(leastLifecycleState){
        Lifecycle.State.RESUMED -> scope.launchWhenResumed {
            checkConnection(url, onConnectionState)
        }

        Lifecycle.State.STARTED -> scope.launchWhenStarted {
            checkConnection(url, onConnectionState)
        }

        Lifecycle.State.CREATED -> scope.launchWhenCreated {
            checkConnection(url, onConnectionState)
        }

        else -> throw IllegalArgumentException("leastLifecycleState should be on of CREATED, STARTED, RESUMED")
    }
}

private suspend fun checkConnection(
    url: String,
    onConnectionState: (connectionState: ConnectionState) -> Unit
){
    val client: OkHttpClient = OkHttpClient().newBuilder().build()

    withContext(Dispatchers.IO){

        var currentState = ConnectionState.CONNECTED

        while (true){
            val startTime = System.currentTimeMillis()
            val request: Request = Request.Builder().url(url)
                .method("GET", null).build()

            val response: Response? = try {
                client.newCall(request).execute()
            }catch (e: Exception){
                null
            }

            val responseCode = response?.code ?: 500
            val timeTaken = System.currentTimeMillis() - startTime

            response?.close()

            withContext(Dispatchers.Main){
                val connectionState = if (!responseCode.toString().startsWith("2")){
                    ConnectionState.DISCONNECTED
                } else {
                    if (timeTaken > 2000) ConnectionState.SLOW else ConnectionState.CONNECTED
                }
                if (connectionState != currentState){
                    onConnectionState(connectionState)
                    currentState = connectionState
                }
            }

            kotlinx.coroutines.delay(30000)//check after 30 seconds
        }
    }
}