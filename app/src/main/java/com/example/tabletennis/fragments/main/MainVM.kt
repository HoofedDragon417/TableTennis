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

    /**
     * Функции навигации между экранами.
     *
     * @param router принимается всеми для навигации в последующих экранах.
     * */
    fun startOvOGame() = router.navigateTo(Screens.SetOvOPlayers(router = router))

    fun startTvTGame()=router.navigateTo(Screens.SetTvTPlayers(router = router))

    fun score() = router.navigateTo(Screens.Score(router = router))

    fun exit() = router.exit()
}