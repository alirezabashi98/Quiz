package com.alirezabashi98.quiz.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.alirezabashi98.quiz.R
import com.alirezabashi98.quiz.database.dao.QuizDao
import com.alirezabashi98.quiz.database.db.QuizDatabase
import com.alirezabashi98.quiz.utility.Constants
import com.alirezabashi98.quiz.utility.ConvertTo
import dev.shreyaspatil.MaterialDialog.MaterialDialog

class ManagerActivity : AppCompatActivity() {

    private lateinit var db : QuizDao

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_manager)

        castView()

        addDataTest()

    }



    private fun castView(){

        db = QuizDatabase.getMyDatabase(this)!!.quizDAO()

    }

    private fun addDataTest(){

        if (db.getAllQuiz().isNullOrEmpty()){
            MaterialDialog.Builder(this)
                .setTitle("سوالای یافت نشد")
                .setMessage("میخوای سوالی پیش فرض اضافه کنی؟")
                .setCancelable(false)
                .setPositiveButton(
                    "اره سوالای پیش فرض اضافه کن"
                ) { dialogInterface, _ ->
                    dialogInterface.cancel()
                    db.insertAll(ConvertTo.QuizDbConvertToAllQuizDb(Constants.getQuestions()))
                    Toast.makeText(this,"دیتاهای پیش فرض اضافه شدن",Toast.LENGTH_SHORT).show()
                }
                .setNegativeButton(
                    "نه و خروج"
                ) { dialogInterface, _ ->
                    dialogInterface.cancel()
                    finishAffinity()
//                    finish()
                }
                .build().show()
        }

    }
}