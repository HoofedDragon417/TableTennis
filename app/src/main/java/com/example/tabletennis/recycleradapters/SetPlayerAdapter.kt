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

    private lateinit var listener: OnItemClickListener

    interface OnItemClickListener {
        fun onItemClick(position: Int)
    }

    fun setOnItemClickListener(listener: OnItemClickListener) {
        this.listener = listener
    }

    inner class SetPlayerViewHolder(
        private val binding: RecyclerviewItemSetPlayerBinding, listener: OnItemClickListener
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(position: Int) {
            val item = items[position]

            binding.playerName.setCompoundDrawablesWithIntrinsicBounds(
                when (item.gender) {
                    Gender.Male -> context.getDrawable(R.drawable.avatar_male_mini)
                    Gender.Female -> context.getDrawable(R.drawable.avatar_female_mini)
                    Gender.Alien -> context.getDrawable(R.drawable.avatar_alien_mini)
                }, null,
                null,
                null
            )

            binding.playerName.text = item.name

        }

        init {
            itemView.setOnClickListener {
                listener.onItemClick(adapterPosition)
            }
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