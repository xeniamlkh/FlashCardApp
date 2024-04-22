package com.example.flashcardapp.model.repository

import com.example.flashcardapp.model.data.FlashCardsEntityDataClass
import com.example.flashcardapp.model.data.FlashCardsDao
import com.example.flashcardapp.model.data.SetsAndCardsDataClass
import kotlinx.coroutines.flow.Flow

class RoomRepository(private val questionsDao: FlashCardsDao) {

    suspend fun insertCard(card: FlashCardsEntityDataClass) {
        questionsDao.insertCard(card)
    }

    suspend fun updateCardIfQuestionNull(setName: String, question: String, answer: String) {
        questionsDao.updateCardIfQuestionNull(setName, question, answer)
    }

    suspend fun updateCardById(cardId: Int, question: String, answer: String) {
        questionsDao.updateCardById(cardId, question, answer)
    }

    fun isNullQuestion(setName: String): Flow<Boolean> {
        return questionsDao.isNullQuestion(setName)
    }

    suspend fun deleteCardById(cardId: Int) {
        questionsDao.deleteCardById(cardId)
    }

    fun isDatabaseEmpty(): Flow<Boolean> {
        return questionsDao.isEmpty()
    }

    fun getCardsBySetName(setName: String): Flow<List<FlashCardsEntityDataClass>> {
        return questionsDao.getListOfCardsBySetName(setName)
    }

    fun getAllSetsNames(): Flow<List<String>> {
        return questionsDao.getAllSetsNames()
    }

    fun getSetsAndCards(): Flow<List<SetsAndCardsDataClass>> {
        return questionsDao.getSetsAndCards()
    }

    fun getCardById(cardId: Int): Flow<FlashCardsEntityDataClass> {
        return questionsDao.getCardById(cardId)
    }

    suspend fun deleteSetByName(setName: String) {
        questionsDao.deleteSetByName(setName)
    }

    suspend fun updateSetName(setName: String, setNameNew: String) {
        questionsDao.updateSetName(setName, setNameNew)
    }
}