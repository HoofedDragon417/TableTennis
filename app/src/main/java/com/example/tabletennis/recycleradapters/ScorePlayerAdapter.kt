package com.example.tabletennis.recycleradapters

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.tabletennis.R
import com.example.tabletennis.databinding.RecyclerviewItemScoreBinding
import com.example.tabletennis.models.Gender
import com.example.tabletennis.models.Player

class ScorePlayerAdapter(private val context: Context, private val items: ArrayList<Player>) :
    RecyclerView.Adapter<ScorePlayerAdapter.ScoreViewHolder>() {
    inner class ScoreViewHolder(private val binding: RecyclerviewItemScoreBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(position: Int) {
            val item = items[position]

            when (item.gender) {
                Gender.Male -> setImage(R.drawable.avatar_male)
                Gender.Female -> setImage(R.drawable.avatar_female)
                Gender.Alien -> setImage(R.drawable.avatar_alien)
            }

            binding.tvPlayerName.text = context.getString(R.string.player_name, item.name)
            binding.tvPlayerWins.text = context.getString(R.string.player_wins, item.wins)
            binding.tvPlayerLoses.text = context.getString(R.string.player_loses, item.loses)
            binding.tvPlayerWinRate.text = context.getString(R.string.player_win_rate, item.winRate)
        }

        private fun setImage(id: Int) {
            binding.ivPlayerAvatar.setImageResource(id)
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ScoreViewHolder {
        val itemView =
            RecyclerviewItemScoreBinding.inflate(LayoutInflater.from(context), parent, false)
        return ScoreViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: ScoreViewHolder, position: Int) {
        holder.bind(position)
    }

    override fun getItemCount(): Int {
        return items.size
    }
}