package com.example.lexiquill.data.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "reviews",
    foreignKeys = [
        ForeignKey(
            entity = Word::class,
            parentColumns = ["id"],
            childColumns = ["word_id"],
            onDelete = ForeignKey.CASCADE
        )
    ],
    indices = [Index(value = ["word_id"])]
)
data class Review(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0L,
    @ColumnInfo(name = "word_id")
    val wordId: Long,
    @ColumnInfo(name = "reviewed_at")
    val reviewedAt: Long = System.currentTimeMillis(),
    @ColumnInfo(name = "correct")
    val correct: Boolean
)
