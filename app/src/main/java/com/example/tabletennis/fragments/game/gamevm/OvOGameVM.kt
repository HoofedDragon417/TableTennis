package com.example.tabletennis.fragments.game.gamevm

import android.content.Context
import androidx.lifecycle.MutableLiveData
import com.example.tabletennis.models.Player
import com.example.tabletennis.models.Teams
import com.github.terrakok.cicerone.Router

class OvOGameVM : BaseGameVM() {

    var playerOne = MutableLiveData<Player>()
    var playerTwo = MutableLiveData<Player>()

    /**
     * Устанавливает игроков и подающих.
     *
     * @param array массив игроков, учавствующих в игре.
     * @param pitcher подающий игрок.
     * @param router навигация по приложению.
     * */
    fun setValues(router: Router, array: Array<Player>, pitcher: Teams) {
        playerOne.value = array[0]
        playerTwo.value = array[1]
        setValues(router, pitcher)
    }

    /**
     * Останавливает игру и сохраняет значение в локальную БД.
     *
     * @param winner победитель.
     * @param context для работы с БД.
     * */
    fun stopGame(context: Context, winner: Teams) {
        when (winner) {
            Teams.TeamFirst -> save(
                winner = requireNotNull(playerOne.value),
                loser = requireNotNull(playerTwo.value),
                context = context
            )
            Teams.TeamSecond -> save(
                winner = requireNotNull(playerTwo.value),
                loser = requireNotNull(playerOne.value),
                context = context
            )
        }
        exitGame()
    }
}