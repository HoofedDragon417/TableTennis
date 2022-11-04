package com.example.tabletennis.fragments.game.game_fields

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.tabletennis.R
import com.example.tabletennis.databinding.FragmentGameBinding
import com.example.tabletennis.fragments.game.gamevm.TvTGameVM
import com.example.tabletennis.models.Player
import com.example.tabletennis.models.Teams
import com.github.terrakok.cicerone.Router

class TvTGameFragment(
    private val router: Router,
    private val players: Array<Player>,
    private val pitcher: Teams,
) : Fragment() {
    private var _binding: FragmentGameBinding? = null
    private val binding get() = requireNotNull(_binding)

    private val viewModel: TvTGameVM by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentGameBinding.inflate(inflater, container, false)
        viewModel.setValues(router = router, array = players, pitcher = pitcher)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.teamOne.observe(requireActivity()) {
            binding.tvTeamOneName.text =
                requireContext().getString(
                    R.string.tvt_teams_name,
                    it.playerOne.name,
                    it.playerTwo.name
                )
        }

        viewModel.teamTwo.observe(requireActivity()) {
            binding.tvTeamTwoName.text =
                requireContext().getString(
                    R.string.tvt_teams_name,
                    it.playerOne.name,
                    it.playerTwo.name
                )
        }

        viewModel.pitcherTeam.observe(requireActivity()) {
            when (it) {
                Teams.TeamFirst -> setImages(firstID = R.drawable.pitcher_sign, secondID = 0)
                Teams.TeamSecond -> setImages(secondID = R.drawable.pitcher_sign, firstID = 0)
                null -> {}
            }
        }

        viewModel.teamOnePoints.observe(requireActivity()) {
            binding.tvTeamOneScore.text = it.toString()
        }

        viewModel.teamTwoPoints.observe(requireActivity()) {
            binding.tvTeamTwoScore.text = it.toString()
        }

        binding.tvTeamOneScore.setOnClickListener {
            viewModel.increasePoints(player = Teams.TeamFirst)
        }

        binding.tvTeamTwoScore.setOnClickListener {
            viewModel.increasePoints(player = Teams.TeamSecond)
        }

        binding.tvTeamOneScore.setOnLongClickListener {
            viewModel.decreasePoints(player = Teams.TeamFirst)
            true
        }

        binding.tvTeamTwoScore.setOnLongClickListener {
            viewModel.decreasePoints(player = Teams.TeamSecond)
            true
        }

        binding.tvTeamOneName.setOnClickListener {
            viewModel.stopGame(context = requireContext(), winner = Teams.TeamFirst)
        }

        binding.tvTeamTwoName.setOnClickListener {
            viewModel.stopGame(context = requireContext(), winner = Teams.TeamSecond)
        }
    }

    private fun setImages(firstID: Int, secondID: Int) {
        binding.ivPlayerOnePitcher.setImageResource(firstID)
        binding.ivPlayerTwoPitcher.setImageResource(secondID)
    }
}