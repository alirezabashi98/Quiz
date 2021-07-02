package com.alirezabashi98.quiz.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.*
import com.alirezabashi98.quiz.R
import com.alirezabashi98.quiz.database.dao.QuizDao
import com.alirezabashi98.quiz.database.db.QuizDatabase
import com.alirezabashi98.quiz.database.entitie.QuizModelEntity
import com.alirezabashi98.quiz.model.QuestionModel
import dev.shreyaspatil.MaterialDialog.MaterialDialog

class AddQuizActivity : AppCompatActivity() {

    private var correctAnswer : Int = 0

    private var db : QuizDao = QuizDatabase.getMyDatabase(this)!!.quizDAO()

    // cast View
    private lateinit var titleAppBar : TextView
    private lateinit var saveQuiz : TextView
    private lateinit var errorQuiz : TextView
    private lateinit var iconBack : ImageView

    private lateinit var question : TextView

    private lateinit var textItem1 : EditText
    private lateinit var textItem2 : EditText
    private lateinit var textItem3 : EditText
    private lateinit var textItem4 : EditText

    private lateinit var checkBoxItem1 : CheckBox
    private lateinit var checkBoxItem2 : CheckBox
    private lateinit var checkBoxItem3 : CheckBox
    private lateinit var checkBoxItem4 : CheckBox



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_quiz)

        castView()

        onClick()

    }

    private fun castView(){

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

    private fun restCheckBox(){
        checkBoxItem1.isChecked = false
        checkBoxItem2.isChecked = false
        checkBoxItem3.isChecked = false
        checkBoxItem4.isChecked = false
    }

    private fun onClickCheckBoxes(){

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

    private fun onClick(){

        onClickCheckBoxes()

        saveQuiz.setOnClickListener {
            checkedForSaveQuizInDb()
        }

        iconBack.setOnClickListener {
            showMessageExit()
        }

    }

    private fun showMessageExit(){

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
                startActivity(Intent(this,ManagerActivity::class.java))
                finish()
            }
            .build().show()

    }

    override fun onBackPressed() {
        showMessageExit()
    }

    private fun checkedForSaveQuizInDb(){

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
                Toast.makeText(this,"سوال اضافه شد",Toast.LENGTH_SHORT).show()
                startActivity(Intent(this,ManagerActivity::class.java))
                finish()
            }
        }

    }

    private fun addQuizInDb(){

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