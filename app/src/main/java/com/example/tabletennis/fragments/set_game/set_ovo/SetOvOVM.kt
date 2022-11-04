package com.example.tabletennis.fragments.set_game.set_ovo

import android.content.Context
import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.tabletennis.R
import com.example.tabletennis.Screens
import com.example.tabletennis.models.Gender
import com.example.tabletennis.models.Player
import com.example.tabletennis.models.Teams
import com.example.tabletennis.storage.DataBase
import com.github.terrakok.cicerone.Router

class SetOvOVM : ViewModel() {

    private var routerNull: Router? = null
    private val router get() = requireNotNull(routerNull)
    private val arrayOfPlayers =
        arrayOf(Player(name = "", gender = Gender.Alien), Player(name = "", gender = Gender.Alien))

    fun setRouter(router: Router) {
        routerNull = router
    }

    val listOfPlayers = MutableLiveData<ArrayList<Player>>()
    var playerOne = MutableLiveData<Player?>()
    var playerTwo = MutableLiveData<Player?>()

    fun getListOfPlayers(context: Context) {
        listOfPlayers.value = DataBase(context).viewPlayers()
    }

    fun createPlayer() = router.navigateTo(Screens.CreatePlayer(router = router))

    fun setPlayers(player: Player, context: Context) {
        if (playerOne.value == null) {
            if (player.id == playerTwo.value?.id) {
                Toast.makeText(context, R.string.another_players, Toast.LENGTH_SHORT).show()
                return
            } else {
                playerOne.value = player
                return
            }
        }

        if (playerTwo.value == null) {
            if (player.id == playerOne.value?.id) {
                Toast.makeText(context, R.string.another_players, Toast.LENGTH_SHORT).show()
                return
            } else {
                playerTwo.value = player
                return
            }
        }
    }

    fun clearPlayer(player: Teams) {
        when (player) {
            Teams.TeamFirst -> playerOne.value = null
            Teams.TeamSecond -> playerTwo.value = null
        }
    }

    fun startGame(pitcherPlayer: Teams, context: Context) {
        if ((playerOne.value == null) or (playerTwo.value == null)) {
            Toast.makeText(context, context.getString(R.string.choose_players), Toast.LENGTH_SHORT)
                .show()
            return
        } else {
            arrayOfPlayers[0] = playerOne.value!!
            arrayOfPlayers[1] = playerTwo.value!!
            router.navigateTo(
                Screens.StartOvOGame(
                    router = router,
                    pitcherPlayer,
                    players = arrayOfPlayers
                )
            )
        }
    }
}