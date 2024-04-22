package com.example.flashcardapp.model.data

import androidx.room.ColumnInfo

data class SetsAndCardsDataClass(
    @ColumnInfo(name = "set name") val setName: String,
    @ColumnInfo(name = "COUNT(question)") val cardsNumber: String
)
