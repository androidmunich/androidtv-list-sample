package de.androidteam.mediathek.tv.sample.ui.video.adapter

import android.view.View
import android.widget.TextView
import androidx.leanback.widget.BaseGridView
import androidx.recyclerview.widget.RecyclerView
import de.androidteam.mediathek.tv.sample.R
import de.androidteam.mediathek.tv.sample.data.VideoList
import de.androidteam.mediathek.tv.sample.ui.base.recyclerview.TvHorizontalRecyclerView
import de.androidteam.mediathek.tv.sample.ui.base.recyclerview.TvListVH
import de.androidteam.mediathek.tv.sample.ui.base.recyclerview.TvViewHolderScrollState
import de.androidteam.mediathek.tv.sample.ui.base.recyclerview.decorators.LinearEdgeDecoration

class VideoListVH(view: View, scrollState: TvViewHolderScrollState) : TvListVH(view, scrollState) {

    private val recyclerView: TvHorizontalRecyclerView = view.findViewById(
        R.id.videoListRecyclerView
    )
    private val titleTextView: TextView = view.findViewById(R.id.titleTextView)
    private var videoList: VideoList? = null
    private val adapter = VideoAdapter()

    init {
        recyclerView.addItemDecoration(
            LinearEdgeDecoration(
                startPadding = view.resources.getDimensionPixelSize(R.dimen.safe_padding_horizontal),
                orientation = RecyclerView.HORIZONTAL
            )
        )
        recyclerView.setItemSpacing(view.resources.getDimensionPixelSize(R.dimen.video_item_spacing))
        recyclerView.adapter = adapter
    }

    override fun getRecyclerView(): BaseGridView = recyclerView

    override fun getScrollKey(): String? = videoList?.title

    override fun onFocused() {
        super.onFocused()
        titleTextView.alpha = 1.0f
    }

    override fun onUnfocused() {
        super.onUnfocused()
        titleTextView.alpha = 0.3f
    }

    fun bind(videoList: VideoList) {
        this.videoList = videoList
        adapter.setItems(videoList.videos)
        titleTextView.text = videoList.title
    }

}
