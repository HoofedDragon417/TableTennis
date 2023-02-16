package com.example.tabletennis.storage

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.example.tabletennis.models.Gender
import com.example.tabletennis.models.Player

class DataBase(context: Context) :
    SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    companion object {
        private const val DATABASE_NAME = "PlayersDB"
        private const val DATABASE_VERSION = 1

        private const val KEY_ID = "id"
        private const val KEY_PLAYER_NAME = "player_name"
        private const val KEY_PLAYER_WINS = "player_wins"
        private const val KEY_PLAYER_LOSES = "player_loses"
        private const val KEY_PLAYER_WIN_RATE = "player_win_rate"
        private const val KEY_PLAYER_GENDER = "player_gender"

        private const val GENDER_REC_MALE = "male"
        private const val GENDER_REC_FEMALE = "female"
        private const val GENDER_REC_ALIEN = "alien"
    }

    private val createTable = "create table $DATABASE_NAME(" +
            "$KEY_ID integer primary key autoincrement," +
            "$KEY_PLAYER_NAME text," +
            "$KEY_PLAYER_WINS integer," +
            "$KEY_PLAYER_LOSES integer," +
            "$KEY_PLAYER_WIN_RATE real," +
            "$KEY_PLAYER_GENDER text" +
            ")"

    override fun onCreate(database: SQLiteDatabase?) {
        database?.execSQL(createTable)
    }

    override fun onUpgrade(database: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        database?.execSQL("drop table if exists $DATABASE_NAME")
        onCreate(database)
    }

    /**Сохраняет данные нового игрока в БД.
     *
     * @param player новый игрок.*/
    fun savePlayer(player: Player) {
        val db = this.writableDatabase

        val values = ContentValues()

        values.put(KEY_PLAYER_NAME, player.name)
        values.put(KEY_PLAYER_WINS, player.wins)
        values.put(KEY_PLAYER_LOSES, player.loses)
        values.put(KEY_PLAYER_WIN_RATE, player.winRate)

        when (player.gender) {
            Gender.Male -> values.put(KEY_PLAYER_GENDER, GENDER_REC_MALE)
            Gender.Female -> values.put(KEY_PLAYER_GENDER, GENDER_REC_FEMALE)
            Gender.Alien -> values.put(KEY_PLAYER_GENDER, GENDER_REC_ALIEN)
        }

        db.insert(DATABASE_NAME, null, values)
        db.close()
    }

    /**Обновляет данные существующего игрока по окончанию игры.
     *
     * @param player игрок, данные которого надо обновить.*/
    fun updatePlayers(player: Player) {
        val db = this.writableDatabase

        val values = ContentValues()

        values.put(KEY_PLAYER_WINS, player.wins)
        values.put(KEY_PLAYER_LOSES, player.loses)
        values.put(KEY_PLAYER_WIN_RATE, player.winRate)

        db.update(DATABASE_NAME, values, "$KEY_ID = ${player.id}", null)
        db.close()
    }

    /**
     * Собирает всех игроков, которые есть в БД, в единый список.
     *
     * @return [ArrayList] игроков.
     * */
    fun viewPlayers(): ArrayList<Player> {

        val list = ArrayList<Player>()

        val db = this.readableDatabase
        val selectQuery = "select * from $DATABASE_NAME"

        val cursor: Cursor = db.rawQuery(selectQuery, null)

        if (cursor.moveToFirst()) {
            do {
                val id = cursor.getInt(0)
                val playerName = cursor.getString(1)
                val wins = cursor.getInt(2)
                val loses = cursor.getInt(3)
                val winRate = cursor.getDouble(4)
                var gender = Gender.Male
                when (cursor.getString(5)) {
                    GENDER_REC_MALE -> gender = Gender.Male
                    GENDER_REC_FEMALE -> gender = Gender.Female
                    GENDER_REC_ALIEN -> gender = Gender.Alien
                }

                list.add(
                    Player(
                        id = id,
                        name = playerName,
                        wins = wins,
                        loses = loses,
                        winRate = winRate,
                        gender = gender
                    )
                )
            } while (cursor.moveToNext())
        }

        db.close()
        cursor.close()
        return list
    }

}