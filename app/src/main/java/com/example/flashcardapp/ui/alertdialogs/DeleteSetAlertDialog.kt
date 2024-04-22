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

private const val ARG_PARAM_SET_NAME = "set_name"

class DeleteSetAlertDialog : DialogFragment() {

    private val viewModel: QuestionsViewModel by activityViewModels {
        QuestionsViewModelFactory(
            (activity?.application as FlashCardsApplication).repository,
            activity?.application as FlashCardsApplication
        )
    }

    private var setName: String? = null

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {

        arguments?.let {
            setName = it.getString(ARG_PARAM_SET_NAME)
        }

        return activity?.let {
            val builder = AlertDialog.Builder(it)
            builder.setMessage(
                getString(R.string.warning_delete_set)
            )
                .setPositiveButton(getString(R.string.delete_cap)) { _, _ ->
                    if (!setName.isNullOrEmpty()) {
                        viewModel.deleteSetByName(setName!!)
                    }

                    Snackbar.make(
                        requireActivity().findViewById(android.R.id.content),
                        R.string.set_deleted, Snackbar.LENGTH_SHORT
                    )
                        .setAnchorView(R.id.add_new_set_btn)
                        .show()
                }
                .setNegativeButton(getString(R.string.cancel_cap)) { _, _ -> }
            builder.create()
        } ?: throw IllegalStateException("Activity cannot be null")
    }

    companion object {
        fun newInstance(setName: String) = DeleteSetAlertDialog().apply {
            arguments = Bundle().apply {
                putString(ARG_PARAM_SET_NAME, setName)
            }
        }
    }
}