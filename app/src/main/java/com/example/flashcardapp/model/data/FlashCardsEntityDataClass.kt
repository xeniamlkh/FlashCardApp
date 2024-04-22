package com.example.flashcardapp.model.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "questions_table")
data class FlashCardsEntityDataClass(
    @PrimaryKey(autoGenerate = true) var id: Int,
    @ColumnInfo(name = "set name") val setName: String,
    @ColumnInfo val question: String?,
    @ColumnInfo val answer: String?
)