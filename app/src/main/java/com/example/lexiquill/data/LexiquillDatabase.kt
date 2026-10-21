package com.example.lexiquill.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.lexiquill.data.dao.InterruptionDao
import com.example.lexiquill.data.dao.ReviewDao
import com.example.lexiquill.data.dao.WordDao
import com.example.lexiquill.data.entity.Interruption
import com.example.lexiquill.data.entity.Review
import com.example.lexiquill.data.entity.Word

@Database(
    entities = [Word::class, Review::class, Interruption::class],
    version = 1,
    exportSchema = false
)
abstract class LexiquillDatabase : RoomDatabase() {
    abstract fun wordDao(): WordDao
    abstract fun reviewDao(): ReviewDao
    abstract fun interruptionDao(): InterruptionDao

    companion object {
        @Volatile
        private var INSTANCE: LexiquillDatabase? = null

        fun getDatabase(context: Context): LexiquillDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    LexiquillDatabase::class.java,
                    "lexiquill_database"
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }
}
