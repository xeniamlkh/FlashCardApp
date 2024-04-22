package com.example.flashcardapp

import android.app.Application
import com.example.flashcardapp.model.data.FlashCardsDatabase
import com.example.flashcardapp.model.repository.RoomRepository

class FlashCardsApplication: Application() {
    val database: FlashCardsDatabase by lazy { FlashCardsDatabase.getDatabase(this) }
    val repository: RoomRepository by lazy { RoomRepository(database.questionsDao()) }
}