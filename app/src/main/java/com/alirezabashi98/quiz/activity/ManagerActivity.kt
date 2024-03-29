package com.alirezabashi98.quiz.activity

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.airbnb.lottie.LottieAnimationView
import com.alirezabashi98.quiz.R
import com.alirezabashi98.quiz.adapter.CheckedNullQuestion
import com.alirezabashi98.quiz.adapter.ViewManagerQuizAdapter
import com.alirezabashi98.quiz.database.dao.QuizDao
import com.alirezabashi98.quiz.database.db.QuizDatabase
import com.alirezabashi98.quiz.model.QuestionModel
import com.alirezabashi98.quiz.utility.Constants
import com.alirezabashi98.quiz.utility.ConvertTo
import com.google.android.material.floatingactionbutton.FloatingActionButton
import dev.shreyaspatil.MaterialDialog.MaterialDialog

class ManagerActivity : AppCompatActivity(), CheckedNullQuestion {

    private lateinit var db: QuizDao

    private lateinit var recyclerView: RecyclerView
    private lateinit var lottieAnimationView: LottieAnimationView
    private lateinit var fab: FloatingActionButton

    private var DataAdapter: MutableList<QuestionModel> = mutableListOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_manager)

        castView()

        reset()

        setOnClickViews()

    }

    private fun setOnClickViews() {

        // FloatingActionButton go to activity add Quiz
        fab.setOnClickListener {
            startActivity(Intent(this, AddQuizActivity::class.java))
        }

    }

    private fun castView() {

        recyclerView = findViewById(R.id.rec_managerActivity_listQuiz)
        recyclerView.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)


        lottieAnimationView = findViewById(R.id.lottie_managerActivity_nullQuiz)

        fab = findViewById(R.id.fab_mangerActivity_addQuiz)

        db = QuizDatabase.getMyDatabase(this)!!.quizDAO()

    }

    private fun addDataTest() {

        if (db.getAllQuiz().isNullOrEmpty()) {
            MaterialDialog.Builder(this)
                .setTitle("سوالای یافت نشد")
                .setMessage("میخوای سوالی پیش فرض اضافه کنی؟")
                .setCancelable(false)
                .setPositiveButton(
                    "اره سوالای پیش فرض اضافه کن"
                ) { dialogInterface, _ ->
                    dialogInterface.cancel()
                    db.insertAll(ConvertTo.QuizDbConvertToAllQuizDb(Constants.getQuestions()))
                    Toast.makeText(this, "دیتاهای پیش فرض اضافه شدن", Toast.LENGTH_SHORT).show()

                    reset()

                }
                .setNegativeButton(
                    "نه"
                ) { dialogInterface, _ ->
                    dialogInterface.cancel()
                }
                .build().show()
        }

    }

    private fun setView() {

        if (db.getAllQuiz().isEmpty()) {
            recyclerView.visibility = View.GONE
            lottieAnimationView.visibility = View.VISIBLE
        } else {
            recyclerView.visibility = View.VISIBLE
            lottieAnimationView.visibility = View.GONE
        }

    }

    private fun setDataAdapter() {

        for (item in db.getAllQuiz()) {

            DataAdapter.add(
                ConvertTo.QuizDbConvertToQuestionModel(item)
            )

        }

    }

    private fun setAdapter() {

        recyclerView.adapter = ViewManagerQuizAdapter(DataAdapter, this)

    }

    override fun setckedisNull() {
        reset()
    }

    private fun reset() {

        setView()
        addDataTest()
        setDataAdapter()
        setAdapter()

    }

}