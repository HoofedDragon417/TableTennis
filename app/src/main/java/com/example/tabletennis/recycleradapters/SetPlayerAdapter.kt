package com.example.tabletennis.recycleradapters

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.tabletennis.R
import com.example.tabletennis.databinding.RecyclerviewItemSetPlayerBinding
import com.example.tabletennis.models.Gender
import com.example.tabletennis.models.Player

class SetPlayerAdapter(
    val context: Context,
    private val items: ArrayList<Player>
) :
    RecyclerView.Adapter<SetPlayerAdapter.SetPlayerViewHolder>() {

    private lateinit var listener: onItemClickListener

    interface onItemClickListener {
        fun onItemClick(position: Int)
    }

    fun setOnItemClickListener(listener: onItemClickListener) {
        this.listener = listener
    }

    inner class SetPlayerViewHolder(
        private val binding: RecyclerviewItemSetPlayerBinding, listener: onItemClickListener
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(position: Int) {
            val item = items[position]

            when (item.gender) {
                Gender.Male -> setImage(R.drawable.avatar_male)
                Gender.Female -> setImage(R.drawable.avatar_female)
                Gender.Alien -> setImage(R.drawable.avatar_alien)
            }

            binding.playerName.text = item.name
        }

        init {
            itemView.setOnClickListener {
                listener.onItemClick(adapterPosition)
            }
        }

        private fun setImage(id: Int) {
            binding.playerAvatar.setImageResource(id)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SetPlayerViewHolder {
        val itemView =
            RecyclerviewItemSetPlayerBinding.inflate(LayoutInflater.from(context), parent, false)

        return SetPlayerViewHolder(itemView, listener)
    }

    override fun onBindViewHolder(holder: SetPlayerViewHolder, position: Int) {
        holder.bind(position)
    }

    override fun getItemCount(): Int {
        return items.size
    }
}