package com.example.tabletennis.models

data class Player(
    val id: Int = 0,
    val name: String,
    var wins: Int = 0,
    var loses: Int = 0,
    var winRate: Double = 0.0,
    val gender: Gender
)

enum class Gender {
    Male, Female, Alien
}
