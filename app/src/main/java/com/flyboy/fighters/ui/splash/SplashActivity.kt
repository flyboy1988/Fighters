package com.flyboy.fighters.ui.splash

import androidx.appcompat.app.AppCompatActivity
import android.annotation.SuppressLint
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.MotionEvent
import android.view.View
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView
import androidx.lifecycle.Observer
import com.flyboy.fighters.R
import com.flyboy.fighters.ui.MainActivity
import com.flyboy.fighters.utils.CONNECTION
import com.flyboy.fighters.utils.ConnectionLiveData
import kotlinx.android.synthetic.main.activity_splash.*
import kotlinx.android.synthetic.main.toolbar_layout.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.Main
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

/**
 * An example full-screen activity that shows and hides the system UI (i.e.
 * status bar and navigation/system bar) with user interaction.
 */
class SplashActivity : AppCompatActivity() {
    private lateinit var connectionLiveData: ConnectionLiveData
    var isConnected = false

    @SuppressLint("ClickableViewAccessibility")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_splash)
        checkNetworkConnection()
        var isTransitionToEnd = false
        CoroutineScope(Main).launch {
            delay(10)
            motion_layout.transitionToEnd()
            delayAnim()

        }
    }

    private fun checkNetworkConnection() {
        connectionLiveData = ConnectionLiveData(this.applicationContext)
        connectionLiveData.observe(this, Observer {
            isConnected = it
        })
    }

    suspend fun delayAnim() {
        delay(2000)
        val intent=Intent(this, MainActivity::class.java)
        intent.flags=Intent.FLAG_ACTIVITY_CLEAR_TASK or (Intent.FLAG_ACTIVITY_NEW_TASK)
        intent.putExtra(CONNECTION, isConnected)
        startActivity(intent)
    }

}