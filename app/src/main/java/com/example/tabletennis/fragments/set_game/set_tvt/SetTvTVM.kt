package com.example.tabletennis.fragments.set_game.set_tvt

import android.content.Context
import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.tabletennis.R
import com.example.tabletennis.Screens
import com.example.tabletennis.models.Player
import com.example.tabletennis.models.Teams
import com.example.tabletennis.storage.DataBase
import com.github.terrakok.cicerone.Router

class SetTvTVM : ViewModel() {
    private var routerNull: Router? = null
    private val router get() = requireNotNull(routerNull)

    val listOfPlayers = MutableLiveData<ArrayList<Player>>()
    var teamOnePlayerOne = MutableLiveData<Player?>()
    var teamOnePlayerTwo = MutableLiveData<Player?>()
    var teamTwoPlayerOne = MutableLiveData<Player?>()
    var teamTwoPlayerTwo = MutableLiveData<Player?>()

    fun setRouter(router: Router) {
        routerNull = router
    }

    fun getListOfPlayers(context: Context) {
        listOfPlayers.value = DataBase(context).viewPlayers()
    }

    fun createPlayer() = router.navigateTo(Screens.CreatePlayer(router = router))

    fun setPlayers(player: Player, context: Context) {

        if (teamOnePlayerOne.value == null) {
            if (player.id == teamOnePlayerTwo.value?.id || player.id == teamTwoPlayerOne.value?.id
                || player.id == teamTwoPlayerTwo.value?.id
            ) {
                Toast.makeText(context, R.string.another_players, Toast.LENGTH_SHORT).show()
                return
            } else {
                teamOnePlayerOne.value = player
                return
            }

        }
        if (teamOnePlayerTwo.value == null) {
            if (player.id == teamOnePlayerOne.value?.id || player.id == teamTwoPlayerOne.value?.id
                || player.id == teamTwoPlayerTwo.value?.id
            ) {
                Toast.makeText(context, R.string.another_players, Toast.LENGTH_SHORT).show()
                return
            } else {
                teamOnePlayerTwo.value = player
                return
            }
        }
        if (teamTwoPlayerOne.value == null) {
            if (player.id == teamOnePlayerOne.value?.id || player.id == teamOnePlayerTwo.value?.id
                || player.id == teamTwoPlayerTwo.value?.id
            ) {
                Toast.makeText(context, R.string.another_players, Toast.LENGTH_SHORT).show()
                return
            } else {
                teamTwoPlayerOne.value = player
                return
            }
        }
        if (teamTwoPlayerTwo.value == null) {
            if (player.id == teamOnePlayerOne.value?.id || player.id == teamOnePlayerTwo.value?.id
                || player.id == teamTwoPlayerOne.value?.id
            ) {
                Toast.makeText(context, R.string.another_players, Toast.LENGTH_SHORT).show()
                return
            } else {
                teamTwoPlayerTwo.value = player
                return
            }
        }
    }

    fun clearPlayer(team: Teams) {
        when (team) {
            Teams.TeamFirst -> {
                teamOnePlayerOne.value = null
                teamOnePlayerTwo.value = null
            }
            Teams.TeamSecond -> {
                teamTwoPlayerOne.value = null
                teamTwoPlayerTwo.value = null
            }
        }
    }

    fun startGame(pitcherTeam: Teams, context: Context) {
        if (teamOnePlayerOne.value == null || teamOnePlayerTwo.value == null || teamTwoPlayerOne.value == null || teamTwoPlayerTwo.value == null) {
            Toast.makeText(context, context.getString(R.string.choose_players), Toast.LENGTH_SHORT)
                .show()
            return
        } else {
            val arrayOfPlayers = arrayOf(
                teamOnePlayerOne.value!!,
                teamOnePlayerTwo.value!!,
                teamTwoPlayerOne.value!!,
                teamTwoPlayerTwo.value!!
            )
            router.navigateTo(
                Screens.StartTvTGame(
                    router = router,
                    pitcher = pitcherTeam,
                    players = arrayOfPlayers
                )
            )
        }
    }
}