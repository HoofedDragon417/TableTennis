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

    /**
     * @param commonPoints отображает общий счёт, необходим для правил игры.
     * @param teamOnePoints отображает счёт первой команды (или игрока).
     * @param teamTwoPoints отображает счёт второй команды (или игрока).
     * */
    private var commonPoints = 0
    val teamOnePoints = MutableLiveData(0)
    val teamTwoPoints = MutableLiveData(0)
    var pitcherTeam = MutableLiveData<Teams>()

    /**
     * Устанавливает роутер для последующей навигации и подающую сторону.
     *
     * @param router роутер для навигации.
     * @param pitcher подающая сторона.
     * */
    fun setValues(router: Router, pitcher: Teams) {
        routerNull = router
        pitcherTeam.value = pitcher
    }

    /**
     * Отслеживает кто подаёт на данный момент. Если общий счёт меньше 20, то падающий сменяется каждую вторую подачу.
     * Если же счёт больше или равен 20, то падающий сменяется каждую падачу.
     * */
    private fun gameRules() {
        commonPoints = requireNotNull(teamOnePoints.value) + requireNotNull(teamTwoPoints.value)

        when (commonPoints < 20) {
            true -> {
                if (commonPoints % 2 == 0) changePitcher()
            }
            false -> changePitcher()
        }
    }

    private fun changePitcher() {
        when ((requireNotNull(pitcherTeam.value))) {
            Teams.TeamFirst -> pitcherTeam.value = Teams.TeamSecond
            Teams.TeamSecond -> pitcherTeam.value = Teams.TeamFirst
        }
    }

    /**
     * Увеличивает счёт одной из сторон в зависимости от передаваемого параметра.
     *
     * @param player enum класс игроков.
     * */
    fun increasePoints(player: Teams) {
        when (player) {
            Teams.TeamFirst -> {
                teamOnePoints.value = requireNotNull(teamOnePoints.value) + 1
            }
            Teams.TeamSecond -> {
                teamTwoPoints.value = requireNotNull(teamTwoPoints.value) + 1
            }
        }
        gameRules()
    }

    /**
     * Уменьшает счёт одной из сторон в зависимости от передаваемого параметра.
     *
     * @param player enum класс игроков.
     * */
    fun decreasePoints(player: Teams) {
        when (player) {
            Teams.TeamFirst -> {
                teamOnePoints.value = requireNotNull(teamOnePoints.value) - 1
            }
            Teams.TeamSecond -> {
                teamTwoPoints.value = requireNotNull(teamTwoPoints.value) - 1
            }
        }
        gameRules()
    }

    /**
     * Сохраняет итоги игры в локальную базу данных.
     *
     * @param winner показывает победителя.
     * @param loser показывает проигравшего.
     * @param context необходим для работы с базой данных.
     * */
    fun save(winner: Player, loser: Player, context: Context) {
        winner.wins += 1
        winner.winRate = setWinRates(winner)
        loser.loses += 1
        loser.winRate = setWinRates(loser)
        DataBase(context).updatePlayers(winner)
        DataBase(context).updatePlayers(loser)
    }

    /**Считает winrate игрока.
     *
     * @param player игрок, winrate которого необходимо найти.
     * @return значение от 0 до 1.*/
    private fun setWinRates(player: Player): Double {
        return player.wins.toDouble() / (player.wins + player.loses).toDouble()
    }

    /**Выход с экрана игры.*/
    fun exitGame() {
        router.exit()
    }
}