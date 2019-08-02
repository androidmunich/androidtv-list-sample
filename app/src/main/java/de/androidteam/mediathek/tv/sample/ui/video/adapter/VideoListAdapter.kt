package de.androidteam.mediathek.tv.sample.ui.video.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import de.androidteam.mediathek.tv.sample.R
import de.androidteam.mediathek.tv.sample.data.VideoList
import de.androidteam.mediathek.tv.sample.ui.base.recyclerview.TvViewHolderScrollState

class VideoListAdapter : RecyclerView.Adapter<VideoListVH>() {

    private val tvScrollState = TvViewHolderScrollState()
    private var items: List<VideoList> = arrayListOf()

    fun clearScrollState() {
        tvScrollState.clear()
    }

    fun setItems(items: List<VideoList>) {
        this.items = items
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VideoListVH {
        val vh = VideoListVH(
            LayoutInflater.from(parent.context).inflate(
                R.layout.video_list_adapter,
                parent,
                false
            ), tvScrollState
        )
        vh.onCreated()
        return vh
    }

    override fun getItemCount(): Int = items.size

    override fun onBindViewHolder(holder: VideoListVH, position: Int) {
        holder.onPreBound()
        holder.bind(items[position])
        holder.onBound()
    }

    override fun onViewRecycled(holder: VideoListVH) {
        super.onViewRecycled(holder)
        holder.onRecycled()
    }

}
