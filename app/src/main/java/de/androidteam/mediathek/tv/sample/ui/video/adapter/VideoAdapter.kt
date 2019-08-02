package de.androidteam.mediathek.tv.sample.ui.video.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import de.androidteam.mediathek.tv.sample.R
import de.androidteam.mediathek.tv.sample.data.Movie

class VideoAdapter : RecyclerView.Adapter<VideoVH>() {

    private var items: List<Movie> = arrayListOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VideoVH {
        val vh = VideoVH(
            LayoutInflater.from(parent.context).inflate(
                R.layout.video_item_adapter,
                parent,
                false
            )
        )
        vh.onCreated()
        return vh
    }

    override fun getItemCount(): Int = items.size

    fun setItems(items: List<Movie>) {
        this.items = items
        notifyDataSetChanged()
    }

    override fun onBindViewHolder(holder: VideoVH, position: Int) {
        holder.bind(items[position])
        holder.onBound()
    }

}