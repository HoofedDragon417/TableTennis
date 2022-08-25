package com.example.tabletennis.fragments.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.tabletennis.databinding.FragmentMainBinding
import com.github.terrakok.cicerone.Router

class MainFragment(private val router: Router) : Fragment() {

    private var _binding: FragmentMainBinding? = null
    private val binding get() = requireNotNull(_binding)

    private val viewModel: MainVM by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMainBinding.inflate(inflater, container, false)
        viewModel.setRouter(router = router)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.btnExit.setOnClickListener {
            viewModel.exit()
        }

        binding.btnStartGame.setOnClickListener {
            viewModel.startGame()
        }

        binding.btnScore.setOnClickListener {
            viewModel.score()
        }
    }
}