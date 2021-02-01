package com.flyboy.fighters.ui.detailsFragment

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.*
import com.flyboy.fighters.data.repository.FightersRepository
import com.flyboy.fighters.model.Fighter
import com.flyboy.fighters.model.Result
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.onStart

class DetailsViewModel @ViewModelInject constructor(private val repository: FightersRepository) :ViewModel(){
    private var _id = MutableLiveData<Int>()
    private val _fighter: LiveData<Result<Fighter>?> = _id.distinctUntilChanged().switchMap {
        liveData {
            repository.fetchFighter(it).onStart {
                emit(Result.loading())
            }.collect {
                emit(it)
            }
        }
    }
    val movie = _fighter

    fun getFighterDetail(id: Int) {
        _id.value = id
    }
}