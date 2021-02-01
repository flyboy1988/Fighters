package com.flyboy.fighters.data.local

import androidx.room.*
import com.flyboy.fighters.model.Fighter
@Dao
interface FighterDao {
    @Query("SELECT * FROM fighter order by id DESC")
    fun getAll(): List<Fighter>?

    @Query("SELECT * FROM fighter WHERE id=:id")
    fun getFighterDetails(id:Int): Fighter?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(movies: List<Fighter>)

    @Delete
    fun delete(movie: Fighter)

    @Delete
    fun deleteAll(movie: List<Fighter>)
}