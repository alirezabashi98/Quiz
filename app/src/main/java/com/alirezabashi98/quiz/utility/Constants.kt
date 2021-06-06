package com.alirezabashi98.quiz.utility

import com.alirezabashi98.quiz.model.QuestionModel

object Constants {

    const val TOTAL_QUESTIONS : String = "total_question"
    const val CORRECT_ANSWERS : String = "correct_answers"
    const val CORRECT_WRONG_ANSWERS : String = "correct_wrong_answers"
    const val CORRECT_UNANSWERED : String = "correct_unanswered"

    fun getQuestions():ArrayList<QuestionModel>{

        val questionsList = ArrayList<QuestionModel>()

        val quiz1 = QuestionModel(
            1,
            "پایتخت ایران کدام است؟",
            "مشهد",
            "تهران",
            "قم",
            "شیراز",
            2
        )

        val quiz2 = QuestionModel(
            2,
            "بهترین کشور برای برنامه نویسی کدام است؟",
            "آمریکا",
            "کانادا",
            "المان",
            "دانمارک",
            1
        )

        questionsList.add(quiz1)
        questionsList.add(quiz2)

        return questionsList
    }

}