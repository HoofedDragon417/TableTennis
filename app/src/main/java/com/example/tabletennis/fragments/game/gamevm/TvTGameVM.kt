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

    /**
     * Устанавливает команду игроков и подающую сторону.
     *
     * @param array массив игроков.
     * @param pitcher подающая комада.
     * @param router устанавливает навигацию.
     * */
    fun setValues(router: Router, array: Array<Player>, pitcher: Teams) {
        val teamOne = GameTeam(array[0],array[1])
        val teamTwo = GameTeam(array[2],array[3])
        this.teamOne.value=teamOne
        this.teamTwo.value=teamTwo
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
            Teams.TeamFirst -> {
                save(
                    winner = requireNotNull(teamOne.value).playerOne,
                    loser = requireNotNull(teamTwo.value).playerOne,
                    context = context
                )
                save(
                    winner = requireNotNull(teamOne.value).playerTwo,
                    loser = requireNotNull(teamTwo.value).playerTwo,
                    context = context
                )
            }
            Teams.TeamSecond -> {
                save(
                    winner = requireNotNull(teamTwo.value).playerOne,
                    loser = requireNotNull(teamOne.value).playerOne,
                    context = context
                )
                save(
                    winner = requireNotNull(teamTwo.value).playerTwo,
                    loser = requireNotNull(teamOne.value).playerTwo,
                    context = context
                )

            }
        }
        exitGame()
    }
}