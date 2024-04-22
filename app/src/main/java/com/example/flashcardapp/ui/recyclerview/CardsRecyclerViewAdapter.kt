package com.example.flashcardapp.ui.recyclerview

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.flashcardapp.databinding.CardLineBinding
import com.example.flashcardapp.model.data.FlashCardsEntityDataClass

interface MenuClickListener {
    fun onMenuClick(menuView: View, cardId: Int)
}

class CardsRecyclerViewAdapter(
    private val questions: List<FlashCardsEntityDataClass>,
    private val menuClickListener: MenuClickListener
) :
    RecyclerView.Adapter<CardsRecyclerViewAdapter.CardsViewHolder>() {

    inner class CardsViewHolder(binding: CardLineBinding) : RecyclerView.ViewHolder(binding.root) {
        val questionTextView: TextView
        val answerTextView: TextView
        val menuBtn: FrameLayout
        var cardId: Int = -1

        init {
            questionTextView = binding.questionText
            answerTextView = binding.answerText
            menuBtn = binding.menuOptionsBtn

            menuBtn.setOnClickListener {
                if (adapterPosition >= 0) {
                    menuClickListener.onMenuClick(
                        it, cardId
                    )
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CardsViewHolder {
        val binding = CardLineBinding
            .inflate(LayoutInflater.from(parent.context), parent, false)
        return CardsViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return questions.size
    }

    override fun onBindViewHolder(holder: CardsViewHolder, position: Int) {
        val questionItem = questions[position]
        holder.questionTextView.text = questionItem.question
        holder.answerTextView.text = questionItem.answer
        holder.cardId = questionItem.id
    }
}