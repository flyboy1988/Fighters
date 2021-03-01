package com.flyboy.fighters.ui

import android.annotation.SuppressLint
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.view.menu.MenuBuilder
import androidx.appcompat.widget.Toolbar
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupWithNavController
import com.flyboy.fighters.R
import com.flyboy.fighters.databinding.ActivityMainBinding
import com.flyboy.fighters.utils.CONNECTION
import com.flyboy.fighters.utils.ConnectionLiveData
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.toolbar_layout.*

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private lateinit var connectionLiveData: ConnectionLiveData
   private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
         binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val isConnected = intent.getBooleanExtra(CONNECTION, false)
        if (!isConnected) {
            Log.d("xxxxxxxxxxxxxxxx", "Am i connected :$isConnected")
            connectivity_status.setImageResource(R.drawable.ic_streamline_icon_wifi_delete_140x140)
        } else connectivity_status.setImageResource(R.drawable.ic_streamline_icon_wifi_check_140x140)
        connectionLiveData = ConnectionLiveData(this.applicationContext)
        connectionLiveData.observe(this, Observer {

            when (it) {
                true -> connectivity_status.setImageResource(R.drawable.ic_streamline_icon_wifi_check_140x140)
                false -> connectivity_status.setImageResource(R.drawable.ic_streamline_icon_wifi_delete_140x140)
            }

        })


        val navHostFragment: NavHostFragment =
            supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        val navController: NavController = navHostFragment.navController

        val appBarConfiguration: AppBarConfiguration = AppBarConfiguration(navController.graph)
        val toolbar = binding.includedBar.toolbar
        toolbar.setupWithNavController(navController, appBarConfiguration)
        setToolbar(toolbar)

    }

    private fun setToolbar(toolbar: Toolbar) {
        setSupportActionBar(toolbar)
        binding.includedBar.toolbar.overflowIcon = ContextCompat.getDrawable(
            applicationContext,
            R.drawable.ic_iconfinder_dots_vertical_more_setting_user_interface_web_app_4567719
        )

    }

    @SuppressLint("RestrictedApi")
    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)
        if (menu is MenuBuilder) {
            menu.setOptionalIconsVisible(true)
        }
        return super.onCreateOptionsMenu(menu)
        // return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean = when (item.itemId) {
        R.id.action_settings -> {
            Toast.makeText(this, "settings", Toast.LENGTH_SHORT).show()
            true
        }
        R.id.action_sign_out -> {

            true
        }
        else -> super.onOptionsItemSelected(item)
    }
}