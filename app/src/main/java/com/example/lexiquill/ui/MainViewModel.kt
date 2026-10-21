package com.example.lexiquill.ui

import android.app.Application
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.lexiquill.LexiquillApplication
import com.example.lexiquill.data.entity.Word
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MainViewModel(application: Application) : AndroidViewModel(application) {
    private val database = (application as LexiquillApplication).database
    private val wordDao = database.wordDao()

    val wordCount: StateFlow<Int> = wordDao.getWordCountFlow()
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = 0
        )

    val words: StateFlow<List<Word>> = wordDao.getAllWordsFlow()
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = emptyList()
        )

    var wordText by mutableStateOf("")
        private set

    var meaningText by mutableStateOf("")
        private set

    var statusMessage by mutableStateOf<String?>(null)
        private set

    fun updateWordText(text: String) {
        wordText = text
        if (statusMessage != null) {
            statusMessage = null
        }
    }

    fun updateMeaningText(meaning: String) {
        meaningText = meaning
        if (statusMessage != null) {
            statusMessage = null
        }
    }

    fun addWord() {
        val trimmed = wordText.trim().lowercase()
        if (trimmed.isEmpty()) {
            statusMessage = "Word cannot be empty"
            return
        }
        if (trimmed.length > 60) {
            statusMessage = "Word must be 60 characters or less"
            return
        }

        val meaningTrimmed = meaningText.trim().takeIf { it.isNotEmpty() }

        viewModelScope.launch(Dispatchers.IO) {
            val word = Word(
                text = trimmed,
                meaning = meaningTrimmed,
                addedAt = System.currentTimeMillis()
            )
            val rowId = wordDao.insert(word)
            withContext(Dispatchers.Main) {
                if (rowId == -1L) {
                    statusMessage = "Already saved"
                } else {
                    wordText = ""
                    meaningText = ""
                    statusMessage = "Word added successfully"
                }
            }
        }
    }

    fun deleteWord(word: Word) {
        viewModelScope.launch(Dispatchers.IO) {
            wordDao.delete(word)
            withContext(Dispatchers.Main) {
                statusMessage = "Word deleted"
            }
        }
    }

    fun clearStatusMessage() {
        statusMessage = null
    }

    fun clearAllWords() {
        viewModelScope.launch(Dispatchers.IO) {
            wordDao.deleteAllWords()
            withContext(Dispatchers.Main) {
                statusMessage = "All words cleared"
            }
        }
    }
}
