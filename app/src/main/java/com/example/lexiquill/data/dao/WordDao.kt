package com.example.lexiquill.data.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.lexiquill.data.entity.Word
import kotlinx.coroutines.flow.Flow

@Dao
interface WordDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(word: Word): Long

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertAll(words: List<Word>)

    @Update
    suspend fun update(word: Word)

    @Delete
    suspend fun delete(word: Word)

    @Query("SELECT * FROM words ORDER BY added_at DESC")
    fun getAllWordsFlow(): Flow<List<Word>>

    @Query("SELECT * FROM words ORDER BY added_at DESC")
    suspend fun getAllWords(): List<Word>

    @Query("SELECT COUNT(*) FROM words")
    fun getWordCountFlow(): Flow<Int>

    @Query("SELECT * FROM words WHERE id = :wordId")
    suspend fun getWordById(wordId: Long): Word?

    @Query("DELETE FROM words")
    suspend fun deleteAllWords()
}
