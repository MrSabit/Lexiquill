package com.example.lexiquill.data.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "words",
    indices = [Index(value = ["text"], unique = true)]
)
data class Word(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0L,
    @ColumnInfo(name = "text")
    val text: String,
    @ColumnInfo(name = "meaning")
    val meaning: String? = null,
    @ColumnInfo(name = "added_at")
    val addedAt: Long = System.currentTimeMillis(),
    @ColumnInfo(name = "box")
    val box: Int = 1,
    @ColumnInfo(name = "last_reviewed_at")
    val lastReviewedAt: Long? = null
)
