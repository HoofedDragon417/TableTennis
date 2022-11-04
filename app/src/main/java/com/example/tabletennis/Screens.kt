package com.example.tabletennis

import com.example.tabletennis.fragments.create_player.CreatePlayerFragment
import com.example.tabletennis.fragments.game.game_fields.OvOGameFragment
import com.example.tabletennis.fragments.game.game_fields.TvTGameFragment
import com.example.tabletennis.fragments.main.MainFragment
import com.example.tabletennis.fragments.score.ScoreFragment
import com.example.tabletennis.fragments.set_game.set_ovo.SetOvOFragment
import com.example.tabletennis.fragments.set_game.set_tvt.SetTvTFragment
import com.example.tabletennis.models.Player
import com.example.tabletennis.models.Teams
import com.github.terrakok.cicerone.Router
import com.github.terrakok.cicerone.androidx.FragmentScreen

object Screens {
    fun Main(router: Router) = FragmentScreen { MainFragment(router = router) }

    fun CreatePlayer(router: Router) = FragmentScreen { CreatePlayerFragment(router = router) }

    fun Score(router: Router) = FragmentScreen { ScoreFragment(router = router) }

    fun SetOvOPlayers(router: Router) = FragmentScreen { SetOvOFragment(router = router) }

    fun SetTvTPlayers(router: Router) = FragmentScreen { SetTvTFragment(router = router) }

    fun StartOvOGame(router: Router, pitcher: Teams, players: Array<Player>) =
        FragmentScreen { OvOGameFragment(router = router, pitcher = pitcher, players = players) }

    fun StartTvTGame(router: Router, pitcher: Teams, players: Array<Player>) =
        FragmentScreen { TvTGameFragment(router = router, pitcher = pitcher, players = players) }
}