package com.example.lexiquill.data.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "interruptions")
data class Interruption(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0L,
    @ColumnInfo(name = "scheduled_at")
    val scheduledAt: Long,
    @ColumnInfo(name = "fired_at")
    val firedAt: Long? = null,
    @ColumnInfo(name = "responded_at")
    val respondedAt: Long? = null,
    @ColumnInfo(name = "outcome")
    val outcome: String = "PENDING", // PENDING, ANSWERED, DECLINED, or IGNORED
    @ColumnInfo(name = "mode")
    val mode: String = "RANDOM" // RANDOM or FIXED
)
