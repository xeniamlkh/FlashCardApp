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
import com.example.flashcardapp.databinding.FragmentResultBinding
import com.example.flashcardapp.ui.viewmodel.QuestionsViewModel
import com.example.flashcardapp.ui.viewmodel.QuestionsViewModelFactory

class ResultFragment : Fragment() {

    private var _binding: FragmentResultBinding? = null
    private val binding
        get() = _binding!!

    private val viewModel: QuestionsViewModel by activityViewModels {
        QuestionsViewModelFactory(
            (activity?.application as FlashCardsApplication).repository,
            activity?.application as FlashCardsApplication
        )
    }

    private val args: ResultFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentResultBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.correctAnswers.observe(this.viewLifecycleOwner) { correctAnswersList ->
            binding.correctAnswersTextView.text =
                buildString {
                    append(getString(R.string.correct_answers))
                    append(correctAnswersList.size)
                }
        }

        viewModel.inCorrectAnswers.observe(this.viewLifecycleOwner) { inCorrectAnswersList ->
            binding.incorrectAnswersTextView.text =
                buildString {
                    append(getString(R.string.incorrect_answers))
                    append(inCorrectAnswersList.size)
                }
        }

        binding.repeatBtn.setOnClickListener {
            viewModel.clearAllAnswers()

            view.findNavController()
                .navigate(
                    ResultFragmentDirections
                        .actionResultFragmentToPracticeFragment(args.setName)
                )
        }
        binding.doneBtn.setOnClickListener {
            viewModel.clearAllAnswers()

            view.findNavController()
                .navigate(ResultFragmentDirections.actionResultFragmentToListOfSetsFragment())
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


}