package com.alirezabashi98.quiz.database.entitie

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "quizModel")
data class QuizModelEntity(

    @PrimaryKey(autoGenerate = true)
    val id : Int? = null,

    val question : String,
    val item_1 : String,
    val item_2 : String,
    val item_3 : String,
    val item_4 : String,
    val correctAnswer : Int
)