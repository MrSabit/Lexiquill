package com.example.lexiquill.data.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.lexiquill.data.entity.Interruption
import kotlinx.coroutines.flow.Flow

@Dao
interface InterruptionDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(interruption: Interruption): Long

    @Update
    suspend fun update(interruption: Interruption)

    @Query("SELECT * FROM interruptions ORDER BY scheduled_at DESC")
    fun getAllInterruptionsFlow(): Flow<List<Interruption>>

    @Query("SELECT * FROM interruptions WHERE id = :id")
    suspend fun getInterruptionById(id: Long): Interruption?

    @Delete
    suspend fun delete(interruption: Interruption)

    @Query("DELETE FROM interruptions")
    suspend fun deleteAllInterruptions()
}
