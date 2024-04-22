package com.example.flashcardapp.model.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow


@Dao
interface FlashCardsDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertCard(card: FlashCardsEntityDataClass)

    @Query("UPDATE questions_table SET question = :question, answer = :answer WHERE `set name` = :setName AND question IS NULL")
    suspend fun updateCardIfQuestionNull(setName: String, question: String, answer: String)

    @Query("UPDATE questions_table SET question = :question, answer = :answer WHERE id= :cardId")
    suspend fun updateCardById(cardId: Int, question: String, answer: String)

    @Query("SELECT (SELECT COUNT(*) FROM questions_table WHERE `set name` == :setName AND question IS NULL) != 0")
    fun isNullQuestion(setName: String): Flow<Boolean>

    @Query("DELETE FROM questions_table WHERE id=:cardId")
    suspend fun deleteCardById(cardId: Int)

    @Query("SELECT (SELECT COUNT(*) FROM questions_table) == 0")
    fun isEmpty(): Flow<Boolean>

    @Query("SELECT * FROM questions_table WHERE `set name` == :setName AND question IS NOT NULL")
    fun getListOfCardsBySetName(setName: String): Flow<List<FlashCardsEntityDataClass>>

    @Query("SELECT DISTINCT `set name` FROM questions_table ORDER BY `set name`")
    fun getAllSetsNames(): Flow<List<String>>

    @Query("SELECT `set name`, COUNT(question) FROM questions_table GROUP BY `set name`")
    fun getSetsAndCards(): Flow<List<SetsAndCardsDataClass>>

    @Query("SELECT * FROM questions_table WHERE id = :cardId")
    fun getCardById(cardId: Int): Flow<FlashCardsEntityDataClass>

    @Query("DELETE FROM questions_table WHERE `set name` == :setName")
    suspend fun deleteSetByName(setName: String)

    @Query("UPDATE questions_table SET `set name` = :setNameNew WHERE `set name` == :setName")
    suspend fun updateSetName(setName: String, setNameNew: String)

}