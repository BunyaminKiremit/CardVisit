package com.example.cardvisit.configs

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import com.example.cardvisit.dao.CardDao
import com.example.cardvisit.models.Card


@Database(entities = [Card::class], version = 3)
abstract class AppDataBase:RoomDatabase() {
    abstract fun cardDao(): CardDao
    companion object {
        private val MIGRATION_2_3: Migration = object : Migration(2, 3) {
            override fun migrate(database: SupportSQLiteDatabase) {
                // Göç işlemlerini burada gerçekleştirin
            }
        }
    }
}


