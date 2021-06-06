package com.alirezabashi98.quiz.model

data class QuestionModel(
    val id : Int,
    val question : String,
    val item_1 : String,
    val item_2 : String,
    val item_3 : String,
    val item_4 : String,
    val correctAnswer : Int
)
