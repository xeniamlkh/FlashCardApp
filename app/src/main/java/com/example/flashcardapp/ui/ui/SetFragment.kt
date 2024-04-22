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
import com.example.flashcardapp.databinding.FragmentSetBinding
import com.example.flashcardapp.ui.viewmodel.QuestionsViewModel
import com.example.flashcardapp.ui.viewmodel.QuestionsViewModelFactory

class SetFragment : Fragment() {

    private var _binding: FragmentSetBinding? = null
    private val binding
        get() = _binding!!

    private val viewModel: QuestionsViewModel by activityViewModels {
        QuestionsViewModelFactory(
            (activity?.application as FlashCardsApplication).repository,
            activity?.application as FlashCardsApplication
        )
    }

    private val args: SetFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentSetBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        if (!args.setName.isNullOrEmpty()) {
            binding.setNameEditText.setText(args.setName)
        }

        binding.saveSetBtn.setOnClickListener {
            if (binding.setNameEditText.text.isNullOrBlank()) {
                binding.warningSetNameRequiredTextView.visibility = View.VISIBLE
            } else {
                saveOrUpdateSet(binding.setNameEditText.text.toString())
            }
        }
    }

    private fun returnBackMethod() {
        view?.findNavController()
            ?.navigate(SetFragmentDirections.actionSetFragmentToListOfSetsFragment())
    }

    private fun saveOrUpdateSet(setName: String) {
        if (args.setName.isNullOrEmpty()) {
            viewModel.createCardRecord(set = setName, null, null)
            returnBackMethod()
        } else {
            viewModel.updateSetName(setName = args.setName.toString(), setNameNew = setName)
            returnBackMethod()
        }
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }
}