package com.example.tabletennis.fragments.game

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.tabletennis.R
import com.example.tabletennis.databinding.FragmentGameBinding
import com.example.tabletennis.models.GamePlayers
import com.example.tabletennis.models.Pitcher
import com.example.tabletennis.models.Players
import com.example.tabletennis.models.Winner
import com.github.terrakok.cicerone.Router

class GameFragment(private val router: Router, private val gameModel: GamePlayers) : Fragment() {

    private var _binding: FragmentGameBinding? = null
    private val binding get() = requireNotNull(_binding)

    private val viewModel: GameVM by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentGameBinding.inflate(inflater, container, false)
        viewModel.setValues(gameModel = gameModel, router = router)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.tvPlayerOneName.text = gameModel.playerOne.name
        binding.tvPlayerTwoName.text = gameModel.playerTwo.name

        viewModel.pitcher.observe(requireActivity()) {
            when (it) {
                Pitcher.PlayerOne -> setImages(firstID = R.drawable.pitcher_sign, secondID = 0)
                Pitcher.PlayerTwo -> setImages(secondID = R.drawable.pitcher_sign, firstID = 0)
            }
        }

        viewModel.playerOneScore.observe(requireActivity()) {
            binding.tvPlayerOneScore.text = it.toString()
        }

        viewModel.playerTwoScore.observe(requireActivity()) {
            binding.tvPlayerTwoScore.text = it.toString()
        }

        binding.tvPlayerOneScore.setOnClickListener {
            viewModel.upScore(player = Players.First)
        }

        binding.tvPlayerTwoScore.setOnClickListener {
            viewModel.upScore(player = Players.Second)
        }

        binding.tvPlayerOneName.setOnClickListener {
            viewModel.stopGame(context = requireActivity(), winner = Winner.PlayerOne)
        }

        binding.tvPlayerTwoName.setOnClickListener {
            viewModel.stopGame(context = requireActivity(), winner = Winner.PlayerTwo)
        }
    }

    private fun setImages(firstID: Int, secondID: Int) {
        binding.ivPlayerOnePitcher.setImageResource(firstID)
        binding.ivPlayerTwoPitcher.setImageResource(secondID)
    }
}