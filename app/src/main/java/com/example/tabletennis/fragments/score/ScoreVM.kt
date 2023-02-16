package com.example.tabletennis.fragments.score

import android.content.Context
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.tabletennis.models.Player
import com.example.tabletennis.storage.DataBase
import com.github.terrakok.cicerone.Router

class ScoreVM : ViewModel() {

    private var routerNull: Router? = null
    private val router get() = requireNotNull(routerNull)

    fun setRouter(router: Router) {
        routerNull = router
    }

    val listOfPlayers = MutableLiveData<ArrayList<Player>>()

    /**Возвращает [ArrayList] игроков.*/
    fun getListOfPlayers(context: Context) {
        listOfPlayers.value = DataBase(context).viewPlayers()
    }
}