package com.example.tabletennis.fragments.set_player

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.tabletennis.R
import com.example.tabletennis.databinding.FragmentSetPlayerBinding
import com.example.tabletennis.models.Gender
import com.example.tabletennis.models.Pitcher
import com.example.tabletennis.models.Player
import com.example.tabletennis.recycleradapters.SetPlayerAdapter
import com.github.terrakok.cicerone.Router

class SetPlayerFragment(private val router: Router) : Fragment() {
    private var _binding: FragmentSetPlayerBinding? = null
    private val binding get() = requireNotNull(_binding)

    private val viewModel: SetPlayerVM by viewModels()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSetPlayerBinding.inflate(inflater, container, false)
        viewModel.setRouter(router)
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

        viewModel.playerOne.observe(requireActivity()) {
            if (it == null) {
                setImageOne(0)
                binding.tvPlayerOneName.text = null
            } else {
                when (it.gender) {
                    Gender.Male -> setImageOne(R.drawable.avatar_male)
                    Gender.Female -> setImageOne(R.drawable.avatar_female)
                    Gender.Alien -> setImageOne(R.drawable.avatar_alien)
                }
                binding.tvPlayerOneName.text = it.name
            }
        }

        viewModel.playerTwo.observe(requireActivity()) {
            if (it == null) {
                setImageTwo(0)
                binding.tvPlayerTwoName.text = null
            } else {
                when (it.gender) {
                    Gender.Male -> setImageTwo(R.drawable.avatar_male)
                    Gender.Female -> setImageTwo(R.drawable.avatar_female)
                    Gender.Alien -> setImageTwo(R.drawable.avatar_alien)
                }
                binding.tvPlayerTwoName.text = it.name
            }
        }

        binding.abtnCreatePlayer.setOnClickListener {
            viewModel.createPlayer()
        }

        adapter.setOnItemClickListener(object : SetPlayerAdapter.onItemClickListener {
            override fun onItemClick(position: Int) {
                viewModel.setPlayers(list[position])
            }
        })

        binding.ivPlayerOneAvatar.setOnLongClickListener {
            viewModel.clearPlayer(position = 0)
            true
        }

        binding.ivPlayerTwoAvatar.setOnLongClickListener {
            viewModel.clearPlayer(position = 1)
            true
        }

        binding.ivPlayerOneAvatar.setOnClickListener {
            viewModel.startGame(Pitcher.PlayerOne)
        }

        binding.ivPlayerTwoAvatar.setOnClickListener {
            viewModel.startGame(Pitcher.PlayerTwo)
        }
    }

    private fun setImageTwo(id: Int) {
        binding.ivPlayerTwoAvatar.setImageResource(id)
    }

    private fun setImageOne(id: Int) {
        binding.ivPlayerOneAvatar.setImageResource(id)
    }

}