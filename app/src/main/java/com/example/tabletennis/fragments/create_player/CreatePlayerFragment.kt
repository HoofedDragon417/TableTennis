package com.example.tabletennis.fragments.create_player

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.tabletennis.R
import com.example.tabletennis.databinding.FragmentCreatePlayerBinding
import com.example.tabletennis.models.Gender
import com.example.tabletennis.models.Player
import com.github.terrakok.cicerone.Router

class CreatePlayerFragment(private val router: Router) : Fragment() {

    private var _binding: FragmentCreatePlayerBinding? = null
    private val binding get() = requireNotNull(_binding)

    private val viewModel: CreatePlayerVM by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCreatePlayerBinding.inflate(inflater, container, false)
        viewModel.setRouter(router = router)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        /**
         * Создаёт нового игрока по имени и гендеру (разная аватарка).
         * */
        binding.btnAddPlayer.setOnClickListener {
            val playerName = binding.etPlayerName.text.toString()
            val playerGender = when {
                binding.rbtnMale.isChecked -> Gender.Male
                binding.rbtnFemale.isChecked -> Gender.Female
                binding.rbtnAlien.isChecked -> Gender.Alien
                else -> {
                    Toast.makeText(context, getString(R.string.choose_gender), Toast.LENGTH_SHORT)
                        .show()
                    return@setOnClickListener
                }
            }

            val player = Player(name = playerName, gender = playerGender)
            viewModel.createRec(player, requireContext())
        }

        /**
         * Выходит с экрана.
         * */
        binding.ivArrowBack.setOnClickListener {
            viewModel.exitFragment()
        }
    }
}