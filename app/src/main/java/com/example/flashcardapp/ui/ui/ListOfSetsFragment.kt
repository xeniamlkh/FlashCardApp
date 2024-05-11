package com.example.flashcardapp.ui.ui

import android.os.Build
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.PopupMenu
import androidx.fragment.app.activityViewModels
import androidx.navigation.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.flashcardapp.FlashCardsApplication
import com.example.flashcardapp.R
import com.example.flashcardapp.databinding.FragmentListOfSetsBinding
import com.example.flashcardapp.ui.alertdialogs.DeleteSetAlertDialog
import com.example.flashcardapp.ui.recyclerview.RecyclerViewItemClickListener
import com.example.flashcardapp.ui.recyclerview.SetsRecyclerViewAdapter
import com.example.flashcardapp.ui.viewmodel.QuestionsViewModel
import com.example.flashcardapp.ui.viewmodel.QuestionsViewModelFactory
import com.google.android.material.snackbar.Snackbar

class ListOfSetsFragment : Fragment(), RecyclerViewItemClickListener {

    private var _binding: FragmentListOfSetsBinding? = null
    private val binding
        get() = _binding!!

    private val viewModel: QuestionsViewModel by activityViewModels {
        QuestionsViewModelFactory(
            (activity?.application as FlashCardsApplication).repository,
            activity?.application as FlashCardsApplication
        )
    }

    private lateinit var recyclerViewAdapter: SetsRecyclerViewAdapter
    private lateinit var spinnerAdapter: ArrayAdapter<*>
    private var currentSetName: String? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentListOfSetsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val screenWidth = requireContext().resources.displayMetrics.widthPixels
        val screenOrientation = requireActivity().resources.configuration.orientation

        viewModel.isDatabaseEmpty().observe(this.viewLifecycleOwner) { booleanAnswer ->
            if (booleanAnswer == true) {
                binding.spinner.visibility = View.GONE
                binding.spinnerTitleTextView.visibility = View.GONE
                binding.showSetBtn.visibility = View.GONE
                binding.exampleSetCardView.visibility = View.VISIBLE
                if (screenWidth < 1080) {
                    binding.examplePracticeBtn.textSize = 12f
                    binding.exampleAddNewCardBtn.textSize = 12f
                }
            }
        }

        viewModel.setName.observe(this.viewLifecycleOwner) { setName ->
            currentSetName = setName
            binding.showSetBtn.text = "\"$setName\""
        }

        viewModel.getAllSetsNames().observe(this.viewLifecycleOwner) { setsNamesList ->
            spinnerAdapter =
                ArrayAdapter(requireContext(), android.R.layout.simple_spinner_item, setsNamesList)
            spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            binding.spinner.adapter = spinnerAdapter
            binding.spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(
                    parent: AdapterView<*>?, view: View?,
                    position: Int, id: Long
                ) {
                    viewModel.setNameSetter(setsNamesList[position])
                }

                override fun onNothingSelected(parent: AdapterView<*>?) {
                    Snackbar
                        .make(
                            binding.spinner,
                            getString(R.string.please_select_a_set), Snackbar.LENGTH_SHORT
                        )
                        .show()
                }
            }
        }

        binding.showSetBtn.setOnClickListener {
            view.findNavController().navigate(
                ListOfSetsFragmentDirections
                    .actionListOfSetsFragmentToListOfCardsFragment(currentSetName.toString())
            )
        }

        viewModel.getSetsAndCards().observe(this.viewLifecycleOwner) { setsAndQuestionsList ->

            recyclerViewAdapter = if (screenWidth < 1080) {
                SetsRecyclerViewAdapter(setsAndQuestionsList, this, 12f)
            } else if (screenWidth in 1081..1280 && screenOrientation == 2) {
                SetsRecyclerViewAdapter(setsAndQuestionsList, this, 0f)
            } else {
                SetsRecyclerViewAdapter(setsAndQuestionsList, this, 15f)
            }

            binding.setsListRecyclerView.adapter = recyclerViewAdapter

            if (screenOrientation == 1) {
                binding.setsListRecyclerView.layoutManager =
                    LinearLayoutManager(requireContext())
            } else if (screenOrientation == 2) {
                binding.setsListRecyclerView.layoutManager =
                    GridLayoutManager(requireContext(), 2)
            }
        }

        binding.addNewSetBtn.setOnClickListener {
            view.findNavController().navigate(
                ListOfSetsFragmentDirections
                    .actionListOfSetsFragmentToSetFragment(null)
            )
        }
    }

    override fun onItemClick(setName: String) {
        view?.findNavController()?.navigate(
            ListOfSetsFragmentDirections
                .actionListOfSetsFragmentToListOfCardsFragment(setName)
        )
    }

    override fun onPracticeBtnClick(setName: String) {
        viewModel.clearAllAnswers()

        view?.findNavController()?.navigate(
            ListOfSetsFragmentDirections.actionListOfSetsFragmentToPracticeFragment(setName)
        )
    }

    override fun onAddNewCardBtnClick(setName: String) {
        view?.findNavController()?.navigate(
            ListOfSetsFragmentDirections.actionListOfSetsFragmentToCardFragment(
                setName,
                -1
            )
        )
    }

    override fun onMenuClick(menuView: View, setName: String) {
        val popupMenu = PopupMenu(requireContext(), menuView)
        popupMenu.inflate(R.menu.options_menu)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            popupMenu.setForceShowIcon(true)
        }

        popupMenu.setOnMenuItemClickListener(object : PopupMenu.OnMenuItemClickListener {
            override fun onMenuItemClick(item: MenuItem?): Boolean {
                when (item?.itemId) {
                    R.id.delete_btn -> {

                        DeleteSetAlertDialog.newInstance(setName)
                            .show(parentFragmentManager, "DELETE_SET")
                        return true
                    }

                    R.id.edit_btn -> {
                        view?.findNavController()?.navigate(
                            ListOfSetsFragmentDirections
                                .actionListOfSetsFragmentToSetFragment(setName)
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