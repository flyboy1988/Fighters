package com.flyboy.fighters.data.remote

import com.flyboy.fighters.model.Fighter
import com.flyboy.fighters.model.FighterList
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface FightersService {
    @GET("fighters")
    suspend fun getAllFighters() : Response<FighterList>

    @GET("fighters/{id}")
    suspend fun getFighter(@Path("id") id: Int): Response<Fighter>
}