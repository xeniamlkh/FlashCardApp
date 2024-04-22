package com.example.flashcardapp.ui.recyclerview

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.flashcardapp.databinding.CardItemBinding
import com.example.flashcardapp.model.data.FlashCardsEntityDataClass

class ViewPagerRecyclerAdapter(
    private val questions: List<FlashCardsEntityDataClass>) :
    RecyclerView.Adapter<ViewPagerRecyclerAdapter.ViewPagerHolder>() {

    inner class ViewPagerHolder(binding: CardItemBinding) : RecyclerView.ViewHolder(binding.root) {
        val questionText: TextView
        val answerText: TextView

        init {
            questionText = binding.questionText
            answerText = binding.answerText
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewPagerHolder {
        val binding = CardItemBinding
            .inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewPagerHolder(binding)
    }

    override fun getItemCount(): Int {
        return questions.size
    }

    override fun onBindViewHolder(holder: ViewPagerHolder, position: Int) {
        val questionItem = questions[position]
        holder.questionText.text = questionItem.question
        holder.answerText.text = questionItem.answer
    }
}