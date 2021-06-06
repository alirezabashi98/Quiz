package com.alirezabashi98.quiz.activity

import android.annotation.SuppressLint
import android.content.Intent
import android.graphics.Color
import android.graphics.Typeface
import android.os.Bundle
import android.os.CountDownTimer
import android.view.View
import android.widget.Button
import android.widget.ProgressBar
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.alirezabashi98.quiz.R
import com.alirezabashi98.quiz.model.QuestionModel
import com.alirezabashi98.quiz.utility.Constants
import dev.shreyaspatil.MaterialDialog.MaterialDialog


class QuizMainActivity : AppCompatActivity(), View.OnClickListener {

    private lateinit var timerQuiz: TextView
    private lateinit var quizQuestion: TextView
    private lateinit var txtItem1: TextView
    private lateinit var txtItem2: TextView
    private lateinit var txtItem3: TextView
    private lateinit var txtItem4: TextView
    private lateinit var btnSubmit: Button
    private lateinit var progressBar: ProgressBar
    private lateinit var txtProgressBar: TextView

    private val mQuestionList: ArrayList<QuestionModel> = Constants.getQuestions()
    private var mCurrentPosition: Int = 1
    private var mSelectedPosition: Int = 0
    private var mCurrentAnswers: Int = 0
    private var mCurrentWrongAnswers: Int = 0
    private var mCurrentUnanswered: Int = 0

    private var mUnanswered: Boolean = false

    private var mClickBtnSubmit: Boolean = false

    private val startTimer = 12_000L
    var timer = startTimer
    private lateinit var quizTimer: CountDownTimer


    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_quiz_main)

        castView()

        setQuestion()

        startQuizTimer()

        setOnClick()

    }

    private fun castView() {

        timerQuiz = findViewById(R.id.tv_quizMain_showTimer)
        quizQuestion = findViewById(R.id.tv_quizMain_question)

        progressBar = findViewById(R.id.progress_quizMain_progressBar)
        txtProgressBar = findViewById(R.id.tv_quizMain_progressBar)

        txtItem1 = findViewById(R.id.tv_quizMain_item_1)
        txtItem2 = findViewById(R.id.tv_quizMain_item_2)
        txtItem3 = findViewById(R.id.tv_quizMain_item_3)
        txtItem4 = findViewById(R.id.tv_quizMain_item_4)

        btnSubmit = findViewById(R.id.btn_quizMain_submit)

    }

    @SuppressLint("SetTextI18n")
    private fun setQuestion() {

        val question = mQuestionList[mCurrentPosition - 1]

        defaultOptionsView()

        progressBar.max = mQuestionList.size
        progressBar.progress = mCurrentPosition

        txtProgressBar.text = "$mCurrentPosition/${mQuestionList.size}"

        quizQuestion.text = question.question

        txtItem1.text = question.item_1
        txtItem2.text = question.item_2
        txtItem3.text = question.item_3
        txtItem4.text = question.item_4

    }

    private fun defaultOptionsView() {

        val options = ArrayList<TextView>()

        if (mCurrentPosition == mQuestionList.size) {
            btnSubmit.text = "پایان"

        } else {
            btnSubmit.text = "ثبت"
        }

        options.add(0, txtItem1)
        options.add(1, txtItem2)
        options.add(2, txtItem3)
        options.add(3, txtItem4)

        for (option in options) {
            option.setTextColor(Color.parseColor("#7a8089"))
            option.typeface = Typeface.DEFAULT
            option.background = ContextCompat.getDrawable(
                this,
                R.drawable.default_option_border_bg
            )
        }

    }

    private fun setOnClick() {

        txtItem1.setOnClickListener(this)
        txtItem2.setOnClickListener(this)
        txtItem3.setOnClickListener(this)
        txtItem4.setOnClickListener(this)
        btnSubmit.setOnClickListener(this)

    }

    override fun onClick(v: View?) {

        when (v?.id) {
            R.id.tv_quizMain_item_1 -> selectedOptionView(txtItem1, 1)
            R.id.tv_quizMain_item_2 -> selectedOptionView(txtItem2, 2)
            R.id.tv_quizMain_item_3 -> selectedOptionView(txtItem3, 3)
            R.id.tv_quizMain_item_4 -> selectedOptionView(txtItem4, 4)
            R.id.btn_quizMain_submit -> {

                mClickBtnSubmit = true

                if (mSelectedPosition == 0) {

                    if (!mUnanswered) {
                        mCurrentUnanswered++
                    }

                    mCurrentPosition++

                    when {
                        mCurrentPosition <= mQuestionList.size -> {
                            setQuestion()
                            mClickBtnSubmit = false
                            mUnanswered = false
                        }
                        else -> {

                            quizTimer.cancel()
                            val goToResult = Intent(this, ResultActivity::class.java)
                            goToResult.putExtra(
                                Constants.CORRECT_ANSWERS,
                                mCurrentAnswers.toString()
                            )
                            goToResult.putExtra(
                                Constants.TOTAL_QUESTIONS,
                                mQuestionList.size.toString()
                            )
                            goToResult.putExtra(
                                Constants.CORRECT_WRONG_ANSWERS,
                                mCurrentWrongAnswers.toString()
                            )
                            goToResult.putExtra(
                                Constants.CORRECT_UNANSWERED,
                                mCurrentUnanswered.toString()
                            )
                            startActivity(goToResult)
                            finish()
                        }
                    }
                } else {
                    val question = mQuestionList[mCurrentPosition - 1]
                    if (question.correctAnswer != mSelectedPosition) {
                        answerView(mSelectedPosition, R.drawable.wrong_option_border_bg)
                        mCurrentWrongAnswers++
                    } else {
                        mCurrentAnswers++
                    }
                    answerView(question.correctAnswer, R.drawable.correct_option_border_bg)

                    if (mCurrentPosition == mQuestionList.size) {
                        btnSubmit.text = "پایان"
                    } else {
                        btnSubmit.text = "سوال بعدی"
                    }

                    mSelectedPosition = 0

                }

            }
        }

    }

    private fun selectedOptionView(tv: TextView, selectedOptionNumber: Int) {

        if (!mClickBtnSubmit) {

            mUnanswered = true

            defaultOptionsView()
            mSelectedPosition = selectedOptionNumber

            tv.setTextColor(Color.parseColor("#363a43"))
            tv.setTypeface(tv.typeface, Typeface.BOLD)
            tv.background = ContextCompat.getDrawable(
                this,
                R.drawable.selected_option_border_bg
            )
        }
    }

    private fun answerView(answer: Int, drawableView: Int) {

        when (answer) {
            1 -> {
                txtItem1.background = ContextCompat.getDrawable(
                    this,
                    drawableView
                )

                txtItem1.setTextColor(Color.parseColor("#ffffff"))

            }
            2 -> {
                txtItem2.background = ContextCompat.getDrawable(
                    this,
                    drawableView
                )

                txtItem2.setTextColor(Color.parseColor("#ffffff"))

            }
            3 -> {
                txtItem3.background = ContextCompat.getDrawable(
                    this,
                    drawableView
                )

                txtItem3.setTextColor(Color.parseColor("#ffffff"))

            }
            4 -> {
                txtItem4.background = ContextCompat.getDrawable(
                    this,
                    drawableView
                )

                txtItem4.setTextColor(Color.parseColor("#ffffff"))
            }
        }

    }

    private fun startQuizTimer() {

        quizTimer = object : CountDownTimer(timer, 1_000) {

            override fun onTick(millisUntilFinished: Long) {
                timer = millisUntilFinished
                setTextQuizTimer()
            }

            override fun onFinish() {

                MaterialDialog.Builder(this@QuizMainActivity)
                    .setTitle("پایان زمان")
                    .setMessage("میخوای دوباره بازی کنی یا امتیازتو ببینی؟")
                    .setCancelable(false)
                    .setPositiveButton(
                        "امتیازو ببینم"
                    ) { _, _ ->
                        // Delete Operation
                        val goToResult = Intent(this@QuizMainActivity, ResultActivity::class.java)

                        goToResult.putExtra(
                            Constants.CORRECT_ANSWERS,
                            mCurrentAnswers.toString()
                        )
                        goToResult.putExtra(
                            Constants.TOTAL_QUESTIONS,
                            mQuestionList.size.toString()
                        )
                        goToResult.putExtra(
                            Constants.CORRECT_WRONG_ANSWERS,
                            mCurrentWrongAnswers.toString()
                        )
                        if (!mClickBtnSubmit) {
                            goToResult.putExtra(
                                Constants.CORRECT_UNANSWERED,
                                ((mQuestionList.size - mCurrentPosition) + 1 + mCurrentUnanswered).toString()
                            )
                        } else {
                            goToResult.putExtra(
                                Constants.CORRECT_UNANSWERED,
                                ((mQuestionList.size - mCurrentPosition) + mCurrentUnanswered).toString()
                            )
                        }
                        startActivity(goToResult)
                        finish()
                    }
                    .setNegativeButton(
                        "دوباره شروع کن"
                    ) { _, _ ->
                        startActivity(Intent(this@QuizMainActivity, QuizMainActivity::class.java))
                    }
                    .build().show()

            }
        }.start()

    }

    private fun setTextQuizTimer() {
        val formatTime = String.format("%02d:%02d", (timer / 1000) / 60, (timer / 1000) % 60)
        timerQuiz.text = formatTime
    }

}