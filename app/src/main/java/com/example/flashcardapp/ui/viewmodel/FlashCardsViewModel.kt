package com.example.flashcardapp.ui.viewmodel

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.example.flashcardapp.model.data.FlashCardsEntityDataClass
import com.example.flashcardapp.model.data.SetsAndCardsDataClass
import com.example.flashcardapp.model.datastore.DataStoreSingleton
import com.example.flashcardapp.model.repository.RoomRepository
import kotlinx.coroutines.launch

class QuestionsViewModel(private val repository: RoomRepository, application: Application) :
    ViewModel() {

    private val dataStoreSingleton = DataStoreSingleton(application)

    val themeMode: LiveData<Boolean> = dataStoreSingleton.themeMode.asLiveData()

    fun saveThemeToDataStore(isNightMode: Boolean) {
        viewModelScope.launch { dataStoreSingleton.saveThemeToDataStore(isNightMode) }
    }

    private var _setName = MutableLiveData<String>()
    val setName: LiveData<String>
        get() = _setName

    fun setNameSetter(setName: String) {
        _setName.value = setName
    }

    private val correctAnswersLocalList = mutableListOf<Int>()
    private var _correctAnswers = MutableLiveData<List<Int>>()
    val correctAnswers: LiveData<List<Int>>
        get() = _correctAnswers

    private val inCorrectAnswersLocalList = mutableListOf<Int>()
    private var _inCorrectAnswers = MutableLiveData<List<Int>>()
    val inCorrectAnswers: LiveData<List<Int>>
        get() = _inCorrectAnswers

    fun setCorrectAnswer(pageId: Int) {
        if (correctAnswersLocalList.isEmpty() || pageId !in correctAnswersLocalList) {
            correctAnswersLocalList.add(pageId)
            _correctAnswers.value = (_correctAnswers.value ?: emptyList()) + pageId
        }

        if (pageId in inCorrectAnswersLocalList) {
            inCorrectAnswersLocalList.remove(pageId)
        }

        if (_inCorrectAnswers.value?.contains(pageId) == true) {
            _inCorrectAnswers.value = (_inCorrectAnswers.value ?: emptyList()) - pageId
        }
    }

    fun setInCorrectAnswer(pageId: Int) {
        if (inCorrectAnswersLocalList.isEmpty() || pageId !in inCorrectAnswersLocalList) {
            inCorrectAnswersLocalList.add(pageId)
            _inCorrectAnswers.value = (_inCorrectAnswers.value ?: emptyList()) + pageId
        }

        if (pageId in correctAnswersLocalList) {
            correctAnswersLocalList.remove(pageId)
        }

        if (_correctAnswers.value?.contains(pageId) == true) {
            _correctAnswers.value = (_correctAnswers.value ?: emptyList()) - pageId
        }
    }

    fun clearAllAnswers() {
        correctAnswersLocalList.clear()
        inCorrectAnswersLocalList.clear()

        _correctAnswers.value = emptyList()
        _inCorrectAnswers.value = emptyList()
    }

    private fun insertCard(card: FlashCardsEntityDataClass) {
        viewModelScope.launch {
            repository.insertCard(card)
        }
    }

    fun isNullQuestion(setName: String): LiveData<Boolean> {
        return repository.isNullQuestion(setName).asLiveData()
    }

    fun updateCardIfQuestionNull(setName: String, question: String, answer: String) {
        viewModelScope.launch {
            repository.updateCardIfQuestionNull(setName, question, answer)
        }
    }

    fun updateCardById(cardId: Int, question: String, answer: String) {
        viewModelScope.launch {
            repository.updateCardById(cardId, question, answer)
        }
    }

    fun createCardRecord(
        set: String = setName.value.toString(),
        question: String?,
        answer: String?
    ) {
        val cardRecord = FlashCardsEntityDataClass(0, set, question, answer)
        insertCard(cardRecord)
    }

    fun deleteCardById(cardId: Int) {
        viewModelScope.launch {
            repository.deleteCardById(cardId)
        }
    }

    fun deleteSetByName(setName: String) {
        viewModelScope.launch {
            repository.deleteSetByName(setName)
        }
    }

    fun isDatabaseEmpty(): LiveData<Boolean> {
        return repository.isDatabaseEmpty().asLiveData()
    }

    fun getCardsBySetName(setName: String): LiveData<List<FlashCardsEntityDataClass>> {
        return repository.getCardsBySetName(setName).asLiveData()
    }

    fun getAllSetsNames(): LiveData<List<String>> {
        return repository.getAllSetsNames().asLiveData()
    }

    fun getSetsAndCards(): LiveData<List<SetsAndCardsDataClass>> {
        return repository.getSetsAndCards().asLiveData()
    }

    fun getCardById(cardId: Int): LiveData<FlashCardsEntityDataClass> {
        return repository.getCardById(cardId).asLiveData()
    }

    fun updateSetName(setName: String, setNameNew: String) {
        viewModelScope.launch { repository.updateSetName(setName, setNameNew) }
    }

}

class QuestionsViewModelFactory(
    private val repository: RoomRepository,
    private val application: Application
) :
    ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        @Suppress("UNCHECKED_CAST")
        if (modelClass.isAssignableFrom(QuestionsViewModel::class.java)) {
            return QuestionsViewModel(repository, application) as T
        }

        throw IllegalArgumentException("Unknown ViewModel class")
    }
}