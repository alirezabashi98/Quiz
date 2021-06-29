package com.alirezabashi98.quiz.activity

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.alirezabashi98.quiz.R

class MainActivity : AppCompatActivity() {

    private lateinit var btnStartQuiz: Button
    private lateinit var btnManagerQuiz: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        castView()

        btnStartQuiz.setOnClickListener {
            startActivity(Intent(this, QuizMainActivity::class.java))
        }

        btnManagerQuiz.setOnClickListener {
            startActivity(Intent(this, ManagerActivity::class.java))
        }

    }

    private fun castView() {

        btnStartQuiz = findViewById(R.id.btn_mainActivity_startQuiz)
        btnManagerQuiz = findViewById(R.id.btn_mainActivity_manager_quiz)

    }
}