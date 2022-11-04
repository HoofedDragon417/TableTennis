package com.example.tabletennis.fragments.game.gamevm

import android.content.Context
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.tabletennis.models.Player
import com.example.tabletennis.models.Teams
import com.example.tabletennis.storage.DataBase
import com.github.terrakok.cicerone.Router

open class BaseGameVM : ViewModel() {
    private var routerNull: Router? = null
    private val router get() = requireNotNull(routerNull)

    var commonPoints = 0
    var teamOnePoints = MutableLiveData(0)
    var teamTwoPoints = MutableLiveData(0)
    var pitcherTeam = MutableLiveData<Teams>()

    fun setValues(router: Router, pitcher: Teams) {
        routerNull = router
        pitcherTeam.value = pitcher
    }

    private fun gameRules() {
        commonPoints = teamOnePoints.value!! + teamTwoPoints.value!!

        when (commonPoints < 20) {
            true -> {
                if (commonPoints % 2 == 0) changePitcher()
            }
            else -> changePitcher()
        }
    }

    private fun changePitcher() {
        when (pitcherTeam.value!!) {
            Teams.TeamFirst -> pitcherTeam.value = Teams.TeamSecond
            Teams.TeamSecond -> pitcherTeam.value = Teams.TeamFirst
        }
    }

    fun increasePoints(player: Teams) {
        when (player) {
            Teams.TeamFirst -> {
                teamOnePoints.value = teamOnePoints.value!! + 1
                gameRules()
            }
            Teams.TeamSecond -> {
                teamTwoPoints.value = teamTwoPoints.value!! + 1
                gameRules()
            }
        }
    }

    fun decreasePoints(player: Teams) {
        when (player) {
            Teams.TeamFirst -> {
                teamOnePoints.value = teamOnePoints.value!! - 1
                gameRules()
            }
            Teams.TeamSecond -> {
                teamTwoPoints.value = teamTwoPoints.value!! - 1
                gameRules()
            }
        }
    }

    fun save(winner: Player, loser: Player, context: Context) {
        winner.wins += 1
        winner.winRate = setWinRates(winner)
        loser.loses += 1
        loser.winRate = setWinRates(loser)
        DataBase(context).updatePlayers(winner)
        DataBase(context).updatePlayers(loser)
    }

    fun exitGame() {
        router.exit()
    }

    private fun setWinRates(player: Player): Double {
        return player.wins.toDouble() / (player.wins + player.loses).toDouble()
    }
}