package com.flyboy.fighters.ui

import android.content.Context
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.flyboy.fighters.data.repository.FightersRepository
import com.flyboy.fighters.utils.ConnectionLiveData
import dagger.hilt.android.qualifiers.ApplicationContext

/**
 * Created by Ali Alani on 2/25/2021 Email: ali.aslani1367@gmail.com.
 */

class MainViewModel @ViewModelInject constructor( @ApplicationContext private val context: Context) : ViewModel() {

private val _isConnected=ConnectionLiveData(context)

    fun getConnectionStatus (){
        _isConnected.value
    }

}