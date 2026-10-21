package com.example.lexiquill.data.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.lexiquill.data.entity.Review
import kotlinx.coroutines.flow.Flow

@Dao
interface ReviewDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(review: Review): Long

    @Query("SELECT * FROM reviews ORDER BY reviewed_at DESC")
    fun getAllReviewsFlow(): Flow<List<Review>>

    @Query("SELECT * FROM reviews WHERE word_id = :wordId")
    fun getReviewsForWordFlow(wordId: Long): Flow<List<Review>>

    @Delete
    suspend fun delete(review: Review)

    @Query("DELETE FROM reviews")
    suspend fun deleteAllReviews()
}
