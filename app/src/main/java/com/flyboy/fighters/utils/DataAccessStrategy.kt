package com.flyboy.fighters.utils

import androidx.lifecycle.LiveData
import androidx.lifecycle.liveData
import androidx.lifecycle.map
import com.flyboy.fighters.model.Result
import kotlinx.coroutines.Dispatchers

//fun <T, A> performGetOperation(databaseQuery: () -> LiveData<T>,
//                               networkCall: suspend () -> Result<A>,
//                               saveCallResult: suspend (A) -> Unit): LiveData<Result<T>> =
//    liveData(Dispatchers.IO) {
//        emit(Result.loading())
//        val source = databaseQuery.invoke().map { Result.success(it) }
//        emitSource(source)
//
//        val responseStatus = networkCall.invoke()
//        if (responseStatus.status == Result.Status.SUCCESS) {
//            saveCallResult(responseStatus.data!!)
//
//        } else if (responseStatus.status == Result.Status.ERROR) {
//            emit(Result.error(responseStatus.message!!,null))
//            emitSource(source)
//        }
//    }