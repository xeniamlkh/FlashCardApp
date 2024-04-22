package com.example.flashcardapp.ui.alertdialogs

import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.activityViewModels
import com.example.flashcardapp.FlashCardsApplication
import com.example.flashcardapp.R
import com.example.flashcardapp.ui.viewmodel.QuestionsViewModel
import com.example.flashcardapp.ui.viewmodel.QuestionsViewModelFactory
import com.google.android.material.snackbar.Snackbar

private const val ARG_PARAM_CARD_ID = "card_id"

class DeleteCardAlertDialog : DialogFragment() {

    private val viewModel: QuestionsViewModel by activityViewModels {
        QuestionsViewModelFactory(
            (activity?.application as FlashCardsApplication).repository,
            activity?.application as FlashCardsApplication
        )
    }

    private var cardId: Int? = null

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {

        arguments?.let {
            cardId = it.getInt(ARG_PARAM_CARD_ID)
        }

        return activity?.let {
            val builder = AlertDialog.Builder(it)

            builder.setMessage(getString(R.string.warning_delete_card))
                .setPositiveButton(getString(R.string.delete_cap)) { _, _ ->

                    if (cardId != null) {
                        viewModel.deleteCardById(cardId!!)
                    }

                    Snackbar.make(
                        requireActivity().findViewById(android.R.id.content),
                        R.string.card_deleted,
                        Snackbar.LENGTH_SHORT
                    )
                        .setAnchorView(R.id.add_new_card_btn)
                        .show()
                }
                .setNegativeButton(getString(R.string.cancel_cap)) { _, _ -> }

            builder.create()
        } ?: throw IllegalStateException("Activity cannot be null")
    }

    companion object {
        fun newInstance(cardId: Int) = DeleteCardAlertDialog().apply {
            arguments = Bundle().apply {
                putInt(ARG_PARAM_CARD_ID, cardId)
            }
        }
    }
}