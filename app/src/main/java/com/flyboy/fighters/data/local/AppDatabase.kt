package com.flyboy.fighters.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.flyboy.fighters.model.Fighter

@Database(entities = [Fighter::class], version = 1)
@TypeConverters(RollConverters::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun fighterDao(): FighterDao
}