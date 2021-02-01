package com.flyboy.fighters.ui.mainFragment

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.flyboy.fighters.data.repository.FightersRepository
import com.flyboy.fighters.model.FighterList
import com.flyboy.fighters.model.Result
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch


class FightersViewModel @ViewModelInject constructor(val repository: FightersRepository): ViewModel() {
    private val _fighterList = MutableLiveData<Result<FighterList>?>()
    val fighterList = _fighterList
    init {
        fetchFighters()
    }

    private fun fetchFighters() {
        viewModelScope.launch {
            repository.fetchAllFighters().collect() {
                _fighterList.value = it
            }
        }
    }

}