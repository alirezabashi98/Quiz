package com.alirezabashi98.quiz.adapter

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.alirezabashi98.quiz.R
import com.alirezabashi98.quiz.activity.AddQuizActivity
import com.alirezabashi98.quiz.database.dao.QuizDao
import com.alirezabashi98.quiz.database.db.QuizDatabase
import com.alirezabashi98.quiz.model.QuestionModel
import com.alirezabashi98.quiz.utility.ConvertTo

class ViewManagerQuizAdapter(var DataListQuiz: MutableList<QuestionModel>,var checkedNullQuestion: CheckedNullQuestion) :
    RecyclerView.Adapter<ViewManagerQuizAdapter.ViewHolderViewManagerQuizAdapter>() {

    private lateinit var context: Context
    private lateinit var db : QuizDao

    inner class ViewHolderViewManagerQuizAdapter(view: View) : RecyclerView.ViewHolder(view) {

        val titleQuiz: TextView = view.findViewById(R.id.tv_itemView_titleQuix)
        val deleteQuiz: ImageView = view.findViewById(R.id.img_itemView_delete)
        val editQuiz: ImageView = view.findViewById(R.id.itemView_imageView_edit)

        fun setDataView(data: QuestionModel) {

            titleQuiz.text = data.question

        }

    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolderViewManagerQuizAdapter {
        //set view adapter
        val view = ViewHolderViewManagerQuizAdapter(
            LayoutInflater.from(parent.context).inflate(R.layout.view_item_quiz, parent, false)
        )

        // set context
        context = parent.context

        // set database
        setDb(parent.context)

        return view
    }

    override fun onBindViewHolder(holder: ViewHolderViewManagerQuizAdapter, position: Int) {

        holder.setDataView(DataListQuiz[position])

        // set onClick button delete item
        onClick(holder,position)

    }

    override fun getItemCount(): Int = DataListQuiz.size

    private fun deleteItemQuiz(position: Int){

        db.delete(ConvertTo.QuizDbConvertToQuizDb(DataListQuiz[position]))

        DataListQuiz.removeAt(position)

        // rest recyclerView
        notifyItemRemoved(position)
        notifyItemRangeChanged(position, DataListQuiz.size)

        // If there is no question, show the animation
        if (DataListQuiz.size == 0){
            checkedNullQuestion.setckedisNull()
        }

    }
    private fun onClick(holder: ViewHolderViewManagerQuizAdapter,position: Int){

        holder.deleteQuiz.setOnClickListener {

            deleteItemQuiz(position)

        }

        holder.editQuiz.setOnClickListener {
            val upQuiz = Intent(context,AddQuizActivity::class.java)
            upQuiz.putExtra(AddQuizActivity.ID_QUIZ,DataListQuiz[position].id)
            upQuiz.putExtra(AddQuizActivity.QUESTION_QUIZ,DataListQuiz[position].question)
            upQuiz.putExtra(AddQuizActivity.ITEM_1_QUIZ,DataListQuiz[position].item_1)
            upQuiz.putExtra(AddQuizActivity.ITEM_2_QUIZ,DataListQuiz[position].item_2)
            upQuiz.putExtra(AddQuizActivity.ITEM_3_QUIZ,DataListQuiz[position].item_3)
            upQuiz.putExtra(AddQuizActivity.ITEM_4_QUIZ,DataListQuiz[position].item_4)
            upQuiz.putExtra(AddQuizActivity.CORRECT_ANSWER_QUIZ,DataListQuiz[position].correctAnswer)
            context.startActivity(upQuiz)

        }

    }

    private fun setDb(context: Context){

        db = QuizDatabase.getMyDatabase(context)!!.quizDAO()

    }

}

interface CheckedNullQuestion{

    fun setckedisNull()

}