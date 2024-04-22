package com.example.flashcardapp.model.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = arrayOf(FlashCardsEntityDataClass::class), version = 4, exportSchema = false)
abstract class FlashCardsDatabase : RoomDatabase() {

    abstract fun questionsDao(): FlashCardsDao

    companion object {
        @Volatile
        private var INSTANCE: FlashCardsDatabase? = null

        fun getDatabase(context: Context): FlashCardsDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    FlashCardsDatabase::class.java,
                    "questions_database"
                ).fallbackToDestructiveMigration()
                    .build()

                INSTANCE = instance

                instance
            }
        }
    }
}