package com.example.flashcardapp.ui.recyclerview

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.FrameLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.flashcardapp.databinding.SetLineBinding
import com.example.flashcardapp.model.data.SetsAndCardsDataClass
import com.google.android.material.card.MaterialCardView

interface RecyclerViewItemClickListener {
    fun onItemClick(setName: String)
    fun onPracticeBtnClick(setName: String)
    fun onAddNewCardBtnClick(setName: String)
    fun onMenuClick(menuView: View, setName: String)
}

class SetsRecyclerViewAdapter(
    private val sets: List<SetsAndCardsDataClass>,
    private val itemClickListener: RecyclerViewItemClickListener,
    private val textSize: Float
) :
    RecyclerView.Adapter<SetsRecyclerViewAdapter.SetsViewHolder>() {

    inner class SetsViewHolder(binding: SetLineBinding) : RecyclerView.ViewHolder(binding.root) {
        val setName: TextView
        val cardsNumber: TextView
        val practiceBtn: Button
        val menuBtn: FrameLayout
        val setView: MaterialCardView
        val addCardBtn: Button

        init {
            setName = binding.setNameText
            cardsNumber = binding.numberOfCardsText
            practiceBtn = binding.practiceBtn
            menuBtn = binding.menuOptionsBtn
            setView = binding.setCardView
            addCardBtn = binding.addNewCardBtn

            practiceBtn.textSize = textSize
            addCardBtn.textSize = textSize

            setView.setOnClickListener {
                if (adapterPosition >= 0) {
                    itemClickListener.onItemClick(setName.text.toString())
                }
            }

            practiceBtn.setOnClickListener {
                if (adapterPosition >= 0) {
                    itemClickListener.onPracticeBtnClick(setName.text.toString())
                }
            }

            menuBtn.setOnClickListener {
                if (adapterPosition >= 0) {
                    itemClickListener.onMenuClick(it, setName.text.toString())
                }
            }

            addCardBtn.setOnClickListener {
                if (adapterPosition >= 0) {
                    itemClickListener.onAddNewCardBtnClick(setName.text.toString())
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SetsViewHolder {
        val binding = SetLineBinding
            .inflate(LayoutInflater.from(parent.context), parent, false)
        return SetsViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return sets.size
    }

    override fun onBindViewHolder(holder: SetsViewHolder, position: Int) {
        val setItem = sets[position]
        holder.setName.text = setItem.setName
        holder.cardsNumber.text = "Cards number = ${setItem.cardsNumber}"
    }
}