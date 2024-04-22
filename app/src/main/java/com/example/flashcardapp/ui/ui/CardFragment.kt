package com.example.flashcardapp.ui.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.navigation.findNavController
import androidx.navigation.fragment.navArgs
import com.example.flashcardapp.FlashCardsApplication
import com.example.flashcardapp.R
import com.example.flashcardapp.databinding.FragmentCardBinding
import com.example.flashcardapp.ui.viewmodel.QuestionsViewModel
import com.example.flashcardapp.ui.viewmodel.QuestionsViewModelFactory
import com.google.android.material.snackbar.Snackbar

class CardFragment : Fragment() {

    private var _binding: FragmentCardBinding? = null
    private val binding
        get() = _binding!!

    private val viewModel: QuestionsViewModel by activityViewModels {
        QuestionsViewModelFactory(
            (activity?.application as FlashCardsApplication).repository,
            activity?.application as FlashCardsApplication
        )
    }

    private val args: CardFragmentArgs by navArgs()
    private var setHasNullQuestion: Boolean = false

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentCardBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.setNameTextView.text = args.setName

        if (args.cardId != -1) {
            viewModel.getCardById(args.cardId).observe(this.viewLifecycleOwner) { card ->
                binding.apply {
                    questionEditView.setText(card.question)
                    answerEditView.setText(card.answer)
                }
            }
        }

        viewModel.isNullQuestion(args.setName)
            .observe(this.viewLifecycleOwner) { booleanAnswer ->
                setHasNullQuestion = booleanAnswer
            }

        binding.saveCardBtn.setOnClickListener {
            if (binding.questionEditView.text.isNullOrBlank()) {
                binding.warningQuestionRequiredTextView.visibility = View.VISIBLE
            } else {
                saveOrUpdateCard(
                    args.setName,
                    args.cardId,
                    binding.questionEditView.text.toString(),
                    binding.answerEditView.text.toString()
                )
            }
        }
    }

    private fun saveOrUpdateCard(setName: String, cardId: Int, question: String, answer: String) {
        if (args.cardId == -1) {
            if (setHasNullQuestion) {
                viewModel.updateCardIfQuestionNull(setName, question, answer)
                binding.questionEditView.text?.clear()
                binding.answerEditView.text?.clear()
                showSnackBar()

            } else {
                viewModel.createCardRecord(setName, question, answer)
                binding.questionEditView.text?.clear()
                binding.answerEditView.text?.clear()
                showSnackBar()
            }
        } else {
            viewModel.updateCardById(cardId, question, answer)
            view?.findNavController()
                ?.navigate(CardFragmentDirections.actionCardFragmentToListOfCardsFragment(setName))
        }
    }

    private fun showSnackBar() {
        Snackbar.make(binding.root, R.string.card_saved, Snackbar.LENGTH_SHORT).show()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}