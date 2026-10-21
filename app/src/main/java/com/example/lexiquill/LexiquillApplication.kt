package com.example.lexiquill

import android.app.Application
import com.example.lexiquill.data.LexiquillDatabase

class LexiquillApplication : Application() {
    val database by lazy { LexiquillDatabase.getDatabase(this) }
}
