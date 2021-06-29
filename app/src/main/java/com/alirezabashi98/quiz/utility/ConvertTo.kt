package com.alirezabashi98.quiz.utility

import com.alirezabashi98.quiz.database.entitie.QuizModelEntity
import com.alirezabashi98.quiz.model.QuestionModel

class ConvertTo {

    fun QuizDbConvertToQuestionModel(it: QuizModelEntity): QuestionModel =
        QuestionModel(
            it.id!!,
            it.question,
            it.item_1,
            it.item_2,
            it.item_3,
            it.item_4,
            it.correctAnswer
        )

    fun QuizDbConvertToAllQuestionModel(it: List<QuizModelEntity>): List<QuestionModel>{
        val list = ArrayList<QuestionModel>()

        it.forEach {
            list.add(
                QuestionModel(
                    it.id!!,
                    it.question,
                    it.item_1,
                    it.item_2,
                    it.item_3,
                    it.item_4,
                    it.correctAnswer
                )
            )
        }

        return list
    }

    fun QuizDbConvertToQuizDb(it: QuestionModel): QuizModelEntity =
        QuizModelEntity(it.id,it.question,it.item_1,it.item_2,it.item_3,it.item_4,it.correctAnswer)

    fun QuizDbConvertToQuizDb(it: List<QuestionModel>): List<QuizModelEntity>{
        val list = ArrayList<QuizModelEntity>()

        it.forEach {
            list.add(
                QuizModelEntity(
                    it.id,
                    it.question,
                    it.item_1,
                    it.item_2,
                    it.item_3,
                    it.item_4,
                    it.correctAnswer
                )
            )
        }

        return list
    }

}