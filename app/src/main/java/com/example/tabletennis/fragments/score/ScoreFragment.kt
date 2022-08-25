package com.example.tabletennis.fragments.score

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.tabletennis.R
import com.example.tabletennis.databinding.FragmentScoreBinding
import com.example.tabletennis.recycleradapters.ScorePlayerAdapter
import com.github.terrakok.cicerone.Router

class ScoreFragment(private val router: Router) : Fragment() {

    private var _binding: FragmentScoreBinding? = null
    private val binding get() = requireNotNull(_binding)

    private val viewModel: ScoreVM by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentScoreBinding.inflate(inflater, container, false)
        viewModel.setRouter(router = router)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.getListOfPlayers(requireActivity())

        viewModel.listOfPlayers.observe(requireActivity()) {
            if (it.isEmpty())
                binding.tvMessage.text = requireContext().getString(R.string.empty_message)
            else
                binding.rvScore.adapter = ScorePlayerAdapter(requireContext(), it)
        }
    }


}