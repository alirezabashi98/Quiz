package com.alirezabashi98.quiz.activity

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.alirezabashi98.quiz.R
import com.alirezabashi98.quiz.utility.Constants

class ResultActivity : AppCompatActivity(), View.OnClickListener {

    private lateinit var btnFinish: Button

    private lateinit var txtAnswers: TextView
    private lateinit var txtWongAnswers: TextView
    private lateinit var txtUnanswered: TextView


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_result)

        castView()

        onClick()

        setValue()
    }

    @SuppressLint("SetTextI18n")
    private fun setValue() {

        try {
            val total = intent.getStringExtra(Constants.TOTAL_QUESTIONS).toString()
            val answers = intent.getStringExtra(Constants.CORRECT_ANSWERS).toString()
            val wongAnswers = intent.getStringExtra(Constants.CORRECT_WRONG_ANSWERS).toString()
            val unanswered = intent.getStringExtra(Constants.CORRECT_UNANSWERED).toString()


            txtAnswers.text = "$answers از $total درست بود"

            txtWongAnswers.text = "$wongAnswers از $total اشتباه بود"

            txtUnanswered.text = "$unanswered از $total بی پاسخ بود"


        }catch (ex : Exception){
            Toast.makeText(this,"error",Toast.LENGTH_SHORT).show()
        }
    }

    private fun onClick() {

        btnFinish.setOnClickListener(this)

    }

    private fun castView() {

        btnFinish = findViewById(R.id.btn_result_finish)

        txtAnswers = findViewById(R.id.tv_result_answers)
        txtWongAnswers = findViewById(R.id.tv_result_wongAnswers)
        txtUnanswered = findViewById(R.id.tv_result_unanswered)

    }

    override fun onClick(v: View?) {

        when (v?.id) {

            R.id.btn_result_finish -> finish()

        }

    }
}