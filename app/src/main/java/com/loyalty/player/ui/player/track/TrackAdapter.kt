package com.loyalty.player.ui.player.track

import android.view.LayoutInflater.from
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.loyalty.player.R
import com.loyalty.player.ui.player.supportClass.util.track.TrackEntity

class TrackAdapter(private val items: List<TrackEntity>) : RecyclerView.Adapter<TrackViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TrackViewHolder {
        return TrackViewHolder(
            from(parent.context).inflate(
                R.layout.item_track_selection,
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: TrackViewHolder, position: Int) {
        holder.bind(items[position], position)
    }

    override fun getItemCount() = items.size
}