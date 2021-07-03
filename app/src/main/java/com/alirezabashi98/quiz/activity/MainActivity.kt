package com.alirezabashi98.quiz.activity

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.alirezabashi98.quiz.R
import com.alirezabashi98.quiz.database.dao.QuizDao
import com.alirezabashi98.quiz.database.db.QuizDatabase
import dev.shreyaspatil.MaterialDialog.MaterialDialog

class MainActivity : AppCompatActivity() {

    private lateinit var btnStartQuiz: Button
    private lateinit var btnManagerQuiz: Button

    private lateinit var db: QuizDao

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        castView()

        onClick()

    }

    private fun castView() {

        btnStartQuiz = findViewById(R.id.btn_mainActivity_startQuiz)
        btnManagerQuiz = findViewById(R.id.btn_mainActivity_manager_quiz)

        db = QuizDatabase.getMyDatabase(this)!!.quizDAO()

    }

    private fun onClick() {

        btnStartQuiz.setOnClickListener {
            if (db.getAllQuiz().isNotEmpty()) {
                startActivity(Intent(this, QuizMainActivity::class.java))
            } else {
                MaterialDialog.Builder(this)
                    .setTitle("سوالای یافت نشد")
                    .setMessage("میخوای سوالی اضافه کنی؟")
                    .setCancelable(false)
                    .setPositiveButton(
                        "اره"
                    ) { dialogInterface, _ ->
                        dialogInterface.cancel()
                        startActivity(Intent(this, ManagerActivity::class.java))
                    }
                    .setNegativeButton(
                        "نه"
                    ) { dialogInterface, _ ->
                        dialogInterface.cancel()
                    }
                    .build().show()
            }

        }

        btnManagerQuiz.setOnClickListener {
            startActivity(Intent(this, ManagerActivity::class.java))
        }

    }
}