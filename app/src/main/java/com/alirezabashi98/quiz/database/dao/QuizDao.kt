package com.alirezabashi98.quiz.database.dao

import androidx.room.*
import com.alirezabashi98.quiz.database.entitie.QuizModelEntity

@Dao
interface QuizDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(quiz: QuizModelEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(quiz: List<QuizModelEntity>)

    @Query("SELECT * FROM quizModel")
    fun getAllQuiz(): List<QuizModelEntity>

    @Query("SELECT * FROM quizModel WHERE question = :question")
    fun getQuizByTitle(question: String): List<QuizModelEntity>

    @Query("SELECT * FROM quizModel WHERE id = :id")
    fun getQuizById(id: Int): QuizModelEntity

    @Delete
    fun delete(quiz: QuizModelEntity)

    @Update
    fun updateQuiz(quiz: QuizModelEntity)


}