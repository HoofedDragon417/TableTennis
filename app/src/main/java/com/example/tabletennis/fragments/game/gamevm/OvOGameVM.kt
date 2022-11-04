package com.example.tabletennis.fragments.game.gamevm

import android.content.Context
import androidx.lifecycle.MutableLiveData
import com.example.tabletennis.models.Player
import com.example.tabletennis.models.Teams
import com.github.terrakok.cicerone.Router

class OvOGameVM : BaseGameVM() {

    var playerOne = MutableLiveData<Player>()
    var playerTwo = MutableLiveData<Player>()

    fun setValues(router: Router, array: Array<Player>, pitcher: Teams) {
        playerOne.value = array[0]
        playerTwo.value = array[1]
        setValues(router, pitcher)
    }

    fun stopGame(context: Context, winner: Teams) {
        when (winner) {
            Teams.TeamFirst -> save(
                winner = playerOne.value!!,
                loser = playerTwo.value!!,
                context = context
            )
            Teams.TeamSecond -> save(
                winner = playerTwo.value!!,
                loser = playerOne.value!!,
                context = context
            )
        }
        exitGame()
    }
}