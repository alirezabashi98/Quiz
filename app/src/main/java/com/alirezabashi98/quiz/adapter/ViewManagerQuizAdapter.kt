package com.alirezabashi98.quiz.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.alirezabashi98.quiz.R
import com.alirezabashi98.quiz.database.dao.QuizDao
import com.alirezabashi98.quiz.database.db.QuizDatabase
import com.alirezabashi98.quiz.model.QuestionModel
import com.alirezabashi98.quiz.utility.ConvertTo

class ViewManagerQuizAdapter(var DataListQuiz: MutableList<QuestionModel>,var checkedNullQuestion: CheckedNullQuestion) :
    RecyclerView.Adapter<ViewManagerQuizAdapter.ViewHolderViewManagerQuizAdapter>() {

    private lateinit var db : QuizDao

    inner class ViewHolderViewManagerQuizAdapter(view: View) : RecyclerView.ViewHolder(view) {

        val titleQuiz: TextView = view.findViewById(R.id.tv_itemView_titleQuix)
        val deleteQuiz: ImageView = view.findViewById(R.id.img_itemView_delete)

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
        if (DataListQuiz.size == 1){
            checkedNullQuestion.setckedisNull()
        }

    }
    private fun onClick(holder: ViewHolderViewManagerQuizAdapter,position: Int){

        holder.deleteQuiz.setOnClickListener {

            deleteItemQuiz(position)

        }

    }

    private fun setDb(context: Context){

        db = QuizDatabase.getMyDatabase(context)!!.quizDAO()

    }

}

interface CheckedNullQuestion{

    fun setckedisNull()

}