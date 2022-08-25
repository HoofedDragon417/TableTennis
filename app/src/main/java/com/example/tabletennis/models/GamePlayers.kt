package com.example.tabletennis.models

class GamePlayers(val playerOne: Player, val playerTwo: Player, val pitcher: Pitcher)

enum class Pitcher {
    PlayerOne, PlayerTwo
}

enum class Players {
    First, Second
}

enum class Winner {
    PlayerOne, PlayerTwo
}
