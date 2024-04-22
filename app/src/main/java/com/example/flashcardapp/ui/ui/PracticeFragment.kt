package com.example.flashcardapp.ui.ui

import android.animation.AnimatorInflater
import android.animation.AnimatorSet
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import androidx.cardview.widget.CardView
import androidx.fragment.app.activityViewModels
import androidx.navigation.findNavController
import androidx.navigation.fragment.navArgs
import androidx.viewpager2.widget.ViewPager2
import com.example.flashcardapp.FlashCardsApplication
import com.example.flashcardapp.R
import com.example.flashcardapp.databinding.FragmentPracticeBinding
import com.example.flashcardapp.ui.recyclerview.ViewPagerRecyclerAdapter
import com.example.flashcardapp.ui.viewmodel.QuestionsViewModel
import com.example.flashcardapp.ui.viewmodel.QuestionsViewModelFactory

class PracticeFragment : Fragment() {

    private var _binding: FragmentPracticeBinding? = null
    private val binding
        get() = _binding!!

    private val viewModel: QuestionsViewModel by activityViewModels {
        QuestionsViewModelFactory(
            (activity?.application as FlashCardsApplication).repository,
            activity?.application as FlashCardsApplication
        )
    }

    private val args: PracticeFragmentArgs by navArgs()

    private var viewPagerItem: ViewPager2? = null
    private lateinit var viewPagerRecyclerAdapter: ViewPagerRecyclerAdapter

    private lateinit var frontAnim: AnimatorSet
    private lateinit var backAnim: AnimatorSet
    private var isFront = true

    private var cardsListSize: Int = 0
    private var pageId: Int = 0
    private var currentPosition: Int = 0

    private var isLastPageSwiped: Boolean = false
    private var counterPageScroll: Int = 0

    private lateinit var progressBar: ProgressBar
    private var progressStatus: Int = 0

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentPracticeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        progressBar = binding.progressBar
        binding.setNameTextView.text = args.setName

        frontAnim = AnimatorInflater.loadAnimator(
            requireContext(), R.animator.front_animator
        ) as AnimatorSet

        backAnim = AnimatorInflater.loadAnimator(
            requireContext(), R.animator.back_animator
        ) as AnimatorSet

        viewModel.getCardsBySetName(args.setName)
            .observe(this.viewLifecycleOwner) { cardsListBySetName ->
                viewPagerRecyclerAdapter = ViewPagerRecyclerAdapter(cardsListBySetName)
                viewPagerItem = binding.viewPager
                viewPagerItem?.adapter = viewPagerRecyclerAdapter
                viewPagerItem?.setPageTransformer(pageTransformer)
                viewPagerItem?.registerOnPageChangeCallback(pageChangeCallback)

                cardsListSize = cardsListBySetName.size
                progressBar.max = cardsListBySetName.size - 1
            }

        binding.trueBtn.setOnClickListener {
            viewModel.setCorrectAnswer(pageId)
            nextPage()
        }

        binding.falseBtn.setOnClickListener {
            viewModel.setInCorrectAnswer(pageId)
            nextPage()
        }
    }

    private val pageChangeCallback = object : ViewPager2.OnPageChangeCallback() {

        override fun onPageScrolled(
            position: Int,
            positionOffset: Float,
            positionOffsetPixels: Int
        ) {
            if (position == cardsListSize - 1 && positionOffset == 0F) {
                if (counterPageScroll != 0) {
                    isLastPageSwiped = true
                    view?.findNavController()
                        ?.navigate(
                            PracticeFragmentDirections
                                .actionPracticeFragmentToResultFragment(args.setName)
                        )
                }
                counterPageScroll++
            } else {
                counterPageScroll = 0
            }
        }

        override fun onPageSelected(position: Int) {
            super.onPageSelected(position)

            pageId = position

            if (position > currentPosition) {
                progressStatus++
                progressBar.progress = progressStatus
                currentPosition = position
            } else if (position < currentPosition) {
                progressStatus--
                progressBar.progress = progressStatus
                currentPosition = position
            }
        }
    }

    private val pageTransformer = ViewPager2.PageTransformer { page, _ ->

        val frontCardView = page.findViewById<CardView>(R.id.front_card_view)
        val backCardView = page.findViewById<CardView>(R.id.back_card_view)

        isFront = true
        frontCardView.alpha = 1F
        backCardView.alpha = 0F
        frontCardView.rotationY = 0F
        backCardView.rotationY = 180F

        val scale: Float = requireContext().resources.displayMetrics.density
        frontCardView.cameraDistance = 8000 * scale
        backCardView.cameraDistance = 8000 * scale

        page.setOnClickListener {
            if (isFront) {
                frontAnim.setTarget(frontCardView)
                backAnim.setTarget(backCardView)
                frontAnim.start()
                backAnim.start()
                isFront = false
            } else {
                frontAnim.setTarget(backCardView)
                backAnim.setTarget(frontCardView)
                frontAnim.start()
                backAnim.start()
                isFront = true
            }
        }
    }

    private fun nextPage() {
        if (pageId == cardsListSize - 1) {
            view?.findNavController()
                ?.navigate(
                    PracticeFragmentDirections
                        .actionPracticeFragmentToResultFragment(args.setName)
                )
        }

        pageId++
        viewPagerItem?.currentItem = pageId
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}