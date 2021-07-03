package com.alirezabashi98.quiz.activity

import android.content.Intent
import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.alirezabashi98.quiz.R
import com.alirezabashi98.quiz.database.dao.QuizDao
import com.alirezabashi98.quiz.database.db.QuizDatabase
import com.alirezabashi98.quiz.database.entitie.QuizModelEntity
import dev.shreyaspatil.MaterialDialog.MaterialDialog

class AddQuizActivity : AppCompatActivity() {

    companion object {

        const val ID_QUIZ = "id"
        const val QUESTION_QUIZ = "question"
        const val ITEM_1_QUIZ = "item_1"
        const val ITEM_2_QUIZ = "item_2"
        const val ITEM_3_QUIZ = "item_3"
        const val ITEM_4_QUIZ = "item_4"
        const val CORRECT_ANSWER_QUIZ = "correctAnswer"

    }

    private var upQuiz = false

    private var correctAnswer: Int = 0

    private var db: QuizDao = QuizDatabase.getMyDatabase(this)!!.quizDAO()

    // cast View
    private lateinit var titleAppBar: TextView
    private lateinit var saveQuiz: TextView
    private lateinit var errorQuiz: TextView
    private lateinit var iconBack: ImageView

    private lateinit var question: TextView

    private lateinit var textItem1: EditText
    private lateinit var textItem2: EditText
    private lateinit var textItem3: EditText
    private lateinit var textItem4: EditText

    private lateinit var checkBoxItem1: CheckBox
    private lateinit var checkBoxItem2: CheckBox
    private lateinit var checkBoxItem3: CheckBox
    private lateinit var checkBoxItem4: CheckBox


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_quiz)

        castView()

        onClick()

        checkedUpQuiz()

    }

    private fun castView() {

        titleAppBar = findViewById(R.id.textView_addQuizActivity_titleAppBar)
        saveQuiz = findViewById(R.id.textView_addQuizActivity_saveQuiz)
        errorQuiz = findViewById(R.id.textView_addQuizActivity_showError)
        iconBack = findViewById(R.id.imageView_addQuizActivity_iconBack)

        question = findViewById(R.id.edit_addQuizActivity_textQuestion)

        textItem1 = findViewById(R.id.editText_addQuizActivity_textResponse_1)
        textItem2 = findViewById(R.id.editText_addQuizActivity_textResponse_2)
        textItem3 = findViewById(R.id.editText_addQuizActivity_textResponse_3)
        textItem4 = findViewById(R.id.editText_addQuizActivity_textResponse_4)

        checkBoxItem1 = findViewById(R.id.checkbox_addQuizActivity_item_1)
        checkBoxItem2 = findViewById(R.id.checkbox_addQuizActivity_item_2)
        checkBoxItem3 = findViewById(R.id.checkbox_addQuizActivity_item_3)
        checkBoxItem4 = findViewById(R.id.checkbox_addQuizActivity_item_4)

    }

    private fun restCheckBox() {
        checkBoxItem1.isChecked = false
        checkBoxItem2.isChecked = false
        checkBoxItem3.isChecked = false
        checkBoxItem4.isChecked = false
    }

    private fun onClickCheckBoxes() {

        // checkBox 1 is checked
        checkBoxItem1.setOnClickListener {
            restCheckBox()
            checkBoxItem1.isChecked = true
            correctAnswer = 1
        }
        // checkBox 2 is checked
        checkBoxItem2.setOnClickListener {
            restCheckBox()
            checkBoxItem2.isChecked = true
            correctAnswer = 2
        }
        // checkBox 3 is checked
        checkBoxItem3.setOnClickListener {
            restCheckBox()
            checkBoxItem3.isChecked = true
            correctAnswer = 3
        }
        // checkBox 4 is checked
        checkBoxItem4.setOnClickListener {
            restCheckBox()
            checkBoxItem4.isChecked = true
            correctAnswer = 4
        }

    }

    private fun onClick() {

        onClickCheckBoxes()

        saveQuiz.setOnClickListener {
            checkedForSaveQuizInDb()
        }

        iconBack.setOnClickListener {
            showMessageExit()
        }

    }

    private fun showMessageExit() {

        MaterialDialog.Builder(this)
            .setTitle("اضافه شدن سوال")
            .setMessage("آیا قصد اضافه کردن سوال رو دارید؟")
            .setCancelable(false)
            .setPositiveButton(
                "اره"
            ) { dialogInterface, _ ->
                dialogInterface.cancel()
                checkedForSaveQuizInDb()
            }
            .setNegativeButton(
                "نه"
            ) { dialogInterface, _ ->
                dialogInterface.cancel()
                startActivity(Intent(this, ManagerActivity::class.java))
                finish()
            }
            .build().show()

    }

    override fun onBackPressed() {
        showMessageExit()
    }

    private fun checkedForSaveQuizInDb() {

        when {
            correctAnswer == 0 -> {
                errorQuiz.text = "گزینه درست را انتخاب کنید"
            }
            question.text.toString().isEmpty() -> {
                errorQuiz.text = "متن سوالارو وارد کنید"
            }
            textItem1.text.toString().isEmpty() -> {
                errorQuiz.text = "متن پاسخ ایتم یک را وارد کنید"
            }
            textItem2.text.toString().isEmpty() -> {
                errorQuiz.text = "متن پاسخ ایتم دو را وارد کنید"
            }
            textItem3.text.toString().isEmpty() -> {
                errorQuiz.text = "متن پاسخ ایتم سه را وارد کنید"
            }
            textItem4.text.toString().isEmpty() -> {
                errorQuiz.text = "متن پاسخ ایتم چهار را وارد کنید"
            }
            else -> {
                addQuizInDb()
                Toast.makeText(this, "سوال اضافه شد", Toast.LENGTH_SHORT).show()
                /*startActivity(Intent(this, MainActivity::class.java))
                startActivity(Intent(this, ManagerActivity::class.java))*/
                finish()

            }
        }

    }

    private fun addQuizInDb() {

        if (upQuiz) {

            db.updateQuiz(
                QuizModelEntity(
                    id = intent.getIntExtra(AddQuizActivity.ID_QUIZ,0),
                    question = question.text.toString(),
                    item_1 = textItem1.text.toString(),
                    item_2 = textItem2.text.toString(),
                    item_3 = textItem3.text.toString(),
                    item_4 = textItem4.text.toString(),
                    correctAnswer = correctAnswer
                )
            )

        } else {

            db.insert(
                QuizModelEntity(
                    question = question.text.toString(),
                    item_1 = textItem1.text.toString(),
                    item_2 = textItem2.text.toString(),
                    item_3 = textItem3.text.toString(),
                    item_4 = textItem4.text.toString(),
                    correctAnswer = correctAnswer
                )
            )
        }

    }

    private fun checkedUpQuiz() {
        if (!intent.getStringExtra(AddQuizActivity.QUESTION_QUIZ.toString()).equals(null)) {

            upQuiz = true

            titleAppBar.text = "ویرایش سوال"

            question.text = intent.getStringExtra(AddQuizActivity.QUESTION_QUIZ).toString()

            textItem1.setText(intent.getStringExtra(AddQuizActivity.ITEM_1_QUIZ))
            textItem2.setText(intent.getStringExtra(AddQuizActivity.ITEM_2_QUIZ))
            textItem3.setText(intent.getStringExtra(AddQuizActivity.ITEM_3_QUIZ))
            textItem4.setText(intent.getStringExtra(AddQuizActivity.ITEM_4_QUIZ))

            when (intent.getIntExtra(AddQuizActivity.CORRECT_ANSWER_QUIZ, 0)) {
                1 -> {
                    checkBoxItem1.isChecked = true
                    correctAnswer = 1
                }
                2 -> {
                    checkBoxItem2.isChecked = true
                    correctAnswer = 2
                }
                3 -> {
                    checkBoxItem3.isChecked = true
                    correctAnswer = 3
                }
                4 -> {
                    checkBoxItem4.isChecked = true
                    correctAnswer = 4
                }
                else -> {
                    errorQuiz.text = "ارور ایتم درست سوال"
                }

            }

        }
    }

}