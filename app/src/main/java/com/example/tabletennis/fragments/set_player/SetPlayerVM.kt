package com.example.tabletennis.fragments.set_player

import android.content.Context
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.tabletennis.Screens
import com.example.tabletennis.models.GamePlayers
import com.example.tabletennis.models.Pitcher
import com.example.tabletennis.models.Player
import com.example.tabletennis.storage.DataBase
import com.github.terrakok.cicerone.Router

class SetPlayerVM : ViewModel() {

    private var routerNull: Router? = null
    private val router get() = requireNotNull(routerNull)

    fun setRouter(router: Router) {
        routerNull = router
    }

    val listOfPlayers = MutableLiveData<ArrayList<Player>>()
    var playerOne = MutableLiveData<Player?>()
    var playerTwo = MutableLiveData<Player?>()

    init {
        Log.e("Test", "${playerOne.value}")
    }

    fun getListOfPlayers(context: Context) {
        listOfPlayers.value = DataBase(context).viewPlayers()
    }

    fun createPlayer() = router.navigateTo(Screens.CreatePlayer(router = router))

    fun setPlayers(player: Player) {
        if (playerOne.value == null)
            playerOne.value = player
        else playerTwo.value = player
    }

    fun clearPlayer(position: Int) {
        if (position == 0)
            playerOne.value = null

        if (position == 1)
            playerTwo.value = null
    }

    fun startGame(pitcher: Pitcher) {
        val game =
            GamePlayers(
                playerOne = playerOne.value!!,
                playerTwo = playerTwo.value!!,
                pitcher = pitcher
            )
        router.navigateTo(Screens.StartGame(router, gamePlayers = game))
    }
}