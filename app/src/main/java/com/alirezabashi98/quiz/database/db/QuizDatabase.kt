package com.alirezabashi98.quiz.database.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.alirezabashi98.quiz.database.dao.QuizDao
import com.alirezabashi98.quiz.database.entitie.QuizModelEntity

@Database(
    entities = [QuizModelEntity::class],
    version = 1
)
abstract class QuizDatabase : RoomDatabase() {

    abstract fun quizDAO(): QuizDao

    companion object {

        private var INSTANCE: QuizDatabase? = null

        fun getMyDatabase(context: Context): QuizDatabase? {

            if (INSTANCE == null) {

                INSTANCE =
                    Room.databaseBuilder(
                        context.applicationContext,
                        QuizDatabase::class.java,
                        "quizDb"
                    )
                        .allowMainThreadQueries()
                        .build()

            }

            return INSTANCE

        }

    }

}