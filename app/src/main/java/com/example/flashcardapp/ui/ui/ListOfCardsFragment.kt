package com.example.flashcardapp.ui.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.PopupMenu
import androidx.fragment.app.activityViewModels
import androidx.navigation.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.flashcardapp.FlashCardsApplication
import com.example.flashcardapp.R
import com.example.flashcardapp.databinding.FragmentListOfCardsBinding
import com.example.flashcardapp.ui.alertdialogs.DeleteCardAlertDialog
import com.example.flashcardapp.ui.recyclerview.MenuClickListener
import com.example.flashcardapp.ui.viewmodel.QuestionsViewModel
import com.example.flashcardapp.ui.viewmodel.QuestionsViewModelFactory
import com.example.flashcardapp.ui.recyclerview.CardsRecyclerViewAdapter

class ListOfCardsFragment : Fragment(), MenuClickListener {
    private var _binding: FragmentListOfCardsBinding? = null
    private val binding
        get() = _binding!!

    private val viewModel: QuestionsViewModel by activityViewModels {
        QuestionsViewModelFactory(
            (activity?.application as FlashCardsApplication).repository,
            activity?.application as FlashCardsApplication
        )
    }

    private lateinit var adapter: CardsRecyclerViewAdapter

    private val args: ListOfCardsFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentListOfCardsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.recyclerViewTitleTextView.text = args.setName

        viewModel.getCardsBySetName(args.setName)
            .observe(this.viewLifecycleOwner) { cardsListBySetName ->
                adapter = CardsRecyclerViewAdapter(cardsListBySetName, this)
                binding.cardsListRecyclerView.adapter = adapter

                if (requireActivity().resources.configuration.orientation == 1) {
                    binding.cardsListRecyclerView.layoutManager =
                        LinearLayoutManager(requireContext())
                } else if (requireActivity().resources.configuration.orientation == 2) {
                    binding.cardsListRecyclerView.layoutManager =
                        GridLayoutManager(requireContext(), 2)
                }
            }

        binding.addNewCardBtn.setOnClickListener {
            view.findNavController().navigate(
                ListOfCardsFragmentDirections.actionListOfCardsFragmentToCardFragment(
                    args.setName, -1
                )
            )
        }

        binding.practiceBtn.setOnClickListener {
            viewModel.clearAllAnswers()

            view.findNavController().navigate(
                ListOfCardsFragmentDirections
                    .actionListOfCardsFragmentToPracticeFragment(args.setName)
            )
        }
    }

    override fun onMenuClick(menuView: View, cardId: Int) {
        val popupMenu = PopupMenu(requireContext(), menuView)
        popupMenu.inflate(R.menu.options_menu)
        popupMenu.setForceShowIcon(true)

        popupMenu.setOnMenuItemClickListener(object : PopupMenu.OnMenuItemClickListener {
            override fun onMenuItemClick(item: MenuItem?): Boolean {
                when (item?.itemId) {
                    R.id.delete_btn -> {
                        DeleteCardAlertDialog.newInstance(cardId)
                            .show(parentFragmentManager, "DELETE_CARD")
                        return true
                    }

                    R.id.edit_btn -> {
                        view?.findNavController()?.navigate(
                            ListOfCardsFragmentDirections.actionListOfCardsFragmentToCardFragment(
                                args.setName, cardId
                            )
                        )
                        return true
                    }

                    else -> {
                        return false
                    }
                }
            }

        })
        popupMenu.show()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
