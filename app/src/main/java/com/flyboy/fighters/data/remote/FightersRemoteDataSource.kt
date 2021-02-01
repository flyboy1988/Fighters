package com.flyboy.fighters.data.remote

import javax.inject.Inject

class FightersRemoteDataSource @Inject constructor(private val fightersService: FightersService): BaseDataSource(){
    suspend fun getFighters() = getResult {fightersService.getAllFighters() }
    suspend fun getFighter(id: Int) = getResult { fightersService.getFighter(id) }
}