package com.example.tabletennis.fragments.main

import androidx.lifecycle.ViewModel
import com.example.tabletennis.Screens
import com.github.terrakok.cicerone.Router

class MainVM : ViewModel() {

    private var routerNull: Router? = null
    private val router get() = requireNotNull(routerNull)


    fun setRouter(router: Router) {
        routerNull = router
    }

    fun startGame() = router.navigateTo(Screens.SetPlayers(router = router))

    fun score() = router.navigateTo(Screens.Score(router = router))

    fun exit() = router.exit()
}