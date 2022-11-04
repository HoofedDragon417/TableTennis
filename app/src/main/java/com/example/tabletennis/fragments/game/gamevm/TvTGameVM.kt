package com.example.tabletennis.fragments.game.gamevm

import android.content.Context
import androidx.lifecycle.MutableLiveData
import com.example.tabletennis.models.GameTeam
import com.example.tabletennis.models.Player
import com.example.tabletennis.models.Teams
import com.github.terrakok.cicerone.Router

class TvTGameVM : BaseGameVM() {

    var teamOne = MutableLiveData<GameTeam>()
    var teamTwo = MutableLiveData<GameTeam>()

    fun setValues(router: Router, array: Array<Player>, pitcher: Teams) {
        val teamOne = GameTeam(array[0],array[1])
        val teamTwo = GameTeam(array[2],array[3])
        this.teamOne.value=teamOne
        this.teamTwo.value=teamTwo
        setValues(router, pitcher)
    }

    fun stopGame(context: Context, winner: Teams) {
        when (winner) {
            Teams.TeamFirst -> {
                save(
                    winner = teamOne.value!!.playerOne,
                    loser = teamTwo.value!!.playerOne,
                    context = context
                )
                save(
                    winner = teamOne.value!!.playerTwo,
                    loser = teamTwo.value!!.playerTwo,
                    context = context
                )
            }
            Teams.TeamSecond -> {
                save(
                    winner = teamTwo.value!!.playerOne,
                    loser = teamOne.value!!.playerOne,
                    context = context
                )
                save(
                    winner = teamTwo.value!!.playerTwo,
                    loser = teamOne.value!!.playerTwo,
                    context = context
                )

            }
        }
        exitGame()
    }
}