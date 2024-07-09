package com.ocean.checknetconnect

import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.Observer
import com.ocean.checknetconnect.databinding.ActivityMainBinding
import com.ocean.checknetconnect.old.CheckInternetConnection
import com.ocean.checknetconnect.old.ConnectivityObserver
import com.ocean.checknetconnect.ref1.ConnectivityViewModel

class MainActivity : AppCompatActivity() {//, ConnectivityListener {

    private lateinit var checkInternetConnection: CheckInternetConnection
    private lateinit var binding: ActivityMainBinding

    private lateinit var connectivityObserver: ConnectivityObserver
    private val connectivityViewModel: ConnectivityViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }


//        checkInternetConnection = CheckInternetConnection(this)
//        checkInternetConnection.connectivityListener = this
//        connectivityObserver = ConnectivityObserver(this)
//        lifecycle.addObserver(connectivityObserver)
        connectivityViewModel.connectivityLiveData.observe(this, Observer { isConnected ->
            if (isConnected){
                Toast.makeText(this, "Internet is available", Toast.LENGTH_SHORT).show()
            }else{
                Toast.makeText(this, "No internet connection", Toast.LENGTH_SHORT).show()
            }
        })
    }

//    override fun onConnectionState(state: ConnectionState) {
//        when (state) {
//            ConnectionState.CONNECTED -> {
//                // Handle the connected state
//                // E.g., show a connected message or perform online tasks
//                println("Connected")
//            }
//            ConnectionState.SLOW -> {
//                // Handle the slow connection state
//                // E.g., show a slow connection message or optimize tasks
//                println("Slow Connection")
//                Snackbar.make(binding.root, "Slow Connection", Snackbar.LENGTH_SHORT).show()
//            }
//            ConnectionState.DISCONNECTED -> {
//                // Handle the disconnected state
//                // E.g., show a disconnected message or disable online features
//                println("Disconnected")
//                Snackbar.make(binding.root, "Disconnected", Snackbar.LENGTH_SHORT).show()
//            }
//        }
//    }
}