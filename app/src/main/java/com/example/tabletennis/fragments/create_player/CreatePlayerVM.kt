package com.example.tabletennis.fragments.create_player

import android.content.Context
import androidx.lifecycle.ViewModel
import com.example.tabletennis.models.Player
import com.example.tabletennis.storage.DataBase
import com.github.terrakok.cicerone.Router

class CreatePlayerVM : ViewModel() {

    private var routerNull: Router? = null
    private val router get() = requireNotNull(routerNull)

    fun setRouter(router: Router) {
        routerNull = router
    }

    fun createRec(player: Player, context: Context) {
        DataBase(context).savePlayer(player)
    }

    fun exitFragment() = router.exit()
}