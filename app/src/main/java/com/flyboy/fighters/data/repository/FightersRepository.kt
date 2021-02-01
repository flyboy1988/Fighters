package com.flyboy.fighters.data.repository

import android.util.Log
import com.flyboy.fighters.data.local.FighterDao
import com.flyboy.fighters.data.remote.FightersRemoteDataSource
import com.flyboy.fighters.model.Fighter
import com.flyboy.fighters.model.FighterList
import com.flyboy.fighters.model.Result
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class FightersRepository @Inject constructor(
    private val fighterRemoteDataSource: FightersRemoteDataSource,
    private val fighterDao: FighterDao
) {
    suspend fun fetchAllFighters(): Flow<Result<FighterList>?> {
        return flow {
            emit(Result.loading())
            val result = fighterRemoteDataSource.getFighters()
            emit(result)
            //Cache to database if response is successful
            if (result.status == Result.Status.SUCCESS) {
                result.data?.results?.let { it ->
                    fighterDao.deleteAll(it)
                    fighterDao.insertAll(it)
                }
            } else
                emit(fetchFightersCached())


        }.flowOn(Dispatchers.IO)
    }

    private fun fetchFightersCached(): Result<FighterList>? =
        fighterDao.getAll()?.let {
            Result.success(FighterList(it))
        }

    suspend fun fetchFighter(id: Int): Flow<Result<Fighter>?> {
        return flow {
            val result = fighterRemoteDataSource.getFighter(id)
            emit(Result.loading())
            if (result.status == Result.Status.SUCCESS)
                emit(fighterRemoteDataSource.getFighter(id))
            else
                emit(fetchFighterDetailsCached(id))

        }.flowOn(Dispatchers.IO)
    }

    private fun fetchFighterDetailsCached(id: Int): Result<Fighter>? =
        fighterDao.getFighterDetails(id)?.let {
            Log.i("xxx", it?.name!!)
            Result.success(it)
        }

}