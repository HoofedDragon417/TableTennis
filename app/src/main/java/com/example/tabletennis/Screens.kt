package com.example.tabletennis

import com.example.tabletennis.fragments.create_player.CreatePlayerFragment
import com.example.tabletennis.fragments.game.GameFragment
import com.example.tabletennis.fragments.main.MainFragment
import com.example.tabletennis.fragments.score.ScoreFragment
import com.example.tabletennis.fragments.set_player.SetPlayerFragment
import com.example.tabletennis.models.GamePlayers
import com.github.terrakok.cicerone.Router
import com.github.terrakok.cicerone.androidx.FragmentScreen

object Screens {
    fun Main(router: Router) = FragmentScreen { MainFragment(router = router) }
    fun CreatePlayer(router: Router) = FragmentScreen { CreatePlayerFragment(router = router) }
    fun SetPlayers(router: Router) = FragmentScreen { SetPlayerFragment(router = router) }
    fun Score(router: Router) = FragmentScreen { ScoreFragment(router = router) }
    fun StartGame(router: Router, gamePlayers: GamePlayers) =
        FragmentScreen { GameFragment(router = router, gameModel = gamePlayers) }
}