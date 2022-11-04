package com.example.tabletennis.fragments.set_game.set_tvt

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.tabletennis.R
import com.example.tabletennis.databinding.FragmentSetTvtPlayersBinding
import com.example.tabletennis.models.Gender
import com.example.tabletennis.models.Player
import com.example.tabletennis.models.Teams
import com.example.tabletennis.recycleradapters.SetPlayerAdapter
import com.github.terrakok.cicerone.Router

class SetTvTFragment(private val router: Router) : Fragment() {

    private var _binding: FragmentSetTvtPlayersBinding? = null
    private val binding get() = requireNotNull(_binding)

    private val viewModel: SetTvTVM by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSetTvtPlayersBinding.inflate(inflater, container, false)
        viewModel.setRouter(router = router)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.getListOfPlayers(requireActivity())
        var list = ArrayList<Player>()
        var adapter = SetPlayerAdapter(requireContext(), list)

        viewModel.listOfPlayers.observe(requireActivity()) {
            if (it.isEmpty())
                binding.tvMessage.text = requireContext().getString(R.string.empty_message)
            else {
                list = it
                adapter = SetPlayerAdapter(requireContext(), it)
                binding.rvPlayers.adapter = adapter
            }
        }

        adapter.setOnItemClickListener(object : SetPlayerAdapter.OnItemClickListener {
            override fun onItemClick(position: Int) {
                viewModel.setPlayers(list[position], requireActivity())
            }
        })

        viewModel.teamOnePlayerOne.observe(requireActivity()) {
            if (it == null) clearFields(binding.tvTeamOnePlayerOne, binding.ivTeamOnePlayerOne)
            else {
                binding.tvTeamOnePlayerOne.text = it.name
                binding.ivTeamOnePlayerOne.setImageResource(
                    when (it.gender) {
                        Gender.Male -> R.drawable.avatar_male
                        Gender.Female -> R.drawable.avatar_female
                        Gender.Alien -> R.drawable.avatar_alien
                    }
                )
            }
        }

        viewModel.teamOnePlayerTwo.observe(requireActivity()) {
            if (it == null) clearFields(binding.tvTeamOnePlayerTwo, binding.ivTeamOnePlayerTwo)
            else {
                binding.tvTeamOnePlayerTwo.text = it.name
                binding.ivTeamOnePlayerTwo.setImageResource(
                    when (it.gender) {
                        Gender.Male -> R.drawable.avatar_male
                        Gender.Female -> R.drawable.avatar_female
                        Gender.Alien -> R.drawable.avatar_alien
                    }
                )
            }
        }

        viewModel.teamTwoPlayerOne.observe(requireActivity()) {
            if (it == null) {
                clearFields(binding.tvTeamTwoPlayerOne, binding.ivTeamTwoPlayerOne)
            } else {
                binding.tvTeamTwoPlayerOne.text = it.name
                binding.ivTeamTwoPlayerOne.setImageResource(
                    when (it.gender) {
                        Gender.Male -> R.drawable.avatar_male
                        Gender.Female -> R.drawable.avatar_female
                        Gender.Alien -> R.drawable.avatar_alien
                    }
                )
            }
        }

        viewModel.teamTwoPlayerTwo.observe(requireActivity()) {
            if (it == null) clearFields(binding.tvTeamTwoPlayerTwo, binding.ivTeamTwoPlayerTwo)
            else {
                binding.tvTeamTwoPlayerTwo.text = it.name
                binding.ivTeamTwoPlayerTwo.setImageResource(
                    when (it.gender) {
                        Gender.Male -> R.drawable.avatar_male
                        Gender.Female -> R.drawable.avatar_female
                        Gender.Alien -> R.drawable.avatar_alien
                    }
                )
            }
        }

        binding.ivTeamOnePlayerOne.setOnLongClickListener {
            viewModel.clearPlayer(team = Teams.TeamFirst)
            true
        }

        binding.ivTeamOnePlayerTwo.setOnLongClickListener {
            viewModel.clearPlayer(team = Teams.TeamFirst)
            true
        }

        binding.ivTeamTwoPlayerOne.setOnLongClickListener {
            viewModel.clearPlayer(team = Teams.TeamSecond)
            true
        }

        binding.ivTeamTwoPlayerTwo.setOnLongClickListener {
            viewModel.clearPlayer(team = Teams.TeamSecond)
            true
        }

        binding.abtnCreatePlayer.setOnClickListener {
            viewModel.createPlayer()
        }

        binding.ivTeamOnePlayerOne.setOnClickListener {
            viewModel.startGame(pitcherTeam = Teams.TeamFirst, context = requireContext())
        }

        binding.ivTeamOnePlayerTwo.setOnClickListener {
            viewModel.startGame(pitcherTeam = Teams.TeamFirst, context = requireContext())
        }

        binding.ivTeamTwoPlayerOne.setOnClickListener {
            viewModel.startGame(pitcherTeam = Teams.TeamSecond, context = requireContext())
        }

        binding.ivTeamTwoPlayerTwo.setOnClickListener {
            viewModel.startGame(pitcherTeam = Teams.TeamSecond, context = requireContext())
        }
    }

    private fun clearFields(textView: TextView, imageView: ImageView) {
        textView.text = null
        imageView.setImageResource(0)
    }


}