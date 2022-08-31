package com.example.tabletennis.fragments.game

import android.content.Context
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.tabletennis.models.*
import com.example.tabletennis.storage.DataBase
import com.github.terrakok.cicerone.Router

class GameVM : ViewModel() {
    private var routerNull: Router? = null
    val router get() = requireNotNull(routerNull)

    var pitcher = MutableLiveData<Pitcher>()

    var playerOneScore = MutableLiveData(0)
    var playerTwoScore = MutableLiveData(0)

    private var commonScore = 0
    private var playerOne = Player(name = "", gender = Gender.Alien)
    private var playerTwo = Player(name = "", gender = Gender.Alien)

    fun setValues(router: Router, gameModel: GamePlayers) {
        routerNull = router
        pitcher.value = gameModel.pitcher
        playerOne = gameModel.playerOne
        playerTwo = gameModel.playerTwo
    }

    fun upScore(player: Players) {
        when (player) {
            Players.First -> {
                playerOneScore.value = playerOneScore.value!! + 1
                commonScore += 1
                gameInstructions()
            }
            Players.Second -> {
                playerTwoScore.value = playerTwoScore.value!! + 1
                commonScore += 1
                gameInstructions()
            }
        }
    }

    fun downScore(player: Players) {
        when (player) {
            Players.First -> {
                playerOneScore.value = playerOneScore.value!! - 1
                commonScore -= 1
                gameInstructions()
            }
            Players.Second -> {
                playerTwoScore.value = playerTwoScore.value!! - 1
                commonScore -= 1
                gameInstructions()
            }
        }
    }

    private fun gameInstructions() {
        when (commonScore < 20) {
            true -> {
                if (commonScore % 2 == 0) {
                    when (pitcher.value) {
                        Pitcher.PlayerOne -> pitcher.value = Pitcher.PlayerTwo
                        Pitcher.PlayerTwo -> pitcher.value = Pitcher.PlayerOne
                        else -> return
                    }
                }
            }
            false -> {
                when (pitcher.value) {
                    Pitcher.PlayerOne -> pitcher.value = Pitcher.PlayerTwo
                    Pitcher.PlayerTwo -> pitcher.value = Pitcher.PlayerOne
                    else -> return
                }
            }
        }

    }

    fun stopGame(context: Context, winner: Winner) {
        when (winner) {
            Winner.PlayerOne -> {
                playerOne.wins += 1
                playerOne.winRate = setWinRates(playerOne)

                playerTwo.loses += 1
                playerTwo.winRate = setWinRates(playerTwo)
            }
            Winner.PlayerTwo -> {
                playerOne.loses += 1
                playerOne.winRate = setWinRates(playerOne)

                playerTwo.wins += 1
                playerTwo.winRate = setWinRates(playerTwo)
            }
        }

        DataBase(context).updatePlayers(playerOne)
        DataBase(context).updatePlayers(playerTwo)

        router.exit()
    }

    private fun setWinRates(player: Player): Double {
        if (player.loses == 0) {
            return player.wins.toDouble()
        }
        return player.wins.toDouble() / player.loses.toDouble()
    }
}