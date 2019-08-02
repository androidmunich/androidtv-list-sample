package de.androidteam.mediathek.tv.sample.ui.video

import android.os.Bundle
import android.view.KeyEvent
import android.view.View
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.RecyclerView
import de.androidteam.mediathek.tv.sample.R
import de.androidteam.mediathek.tv.sample.data.VideoList
import de.androidteam.mediathek.tv.sample.ui.base.fragment.TvBaseFragment
import de.androidteam.mediathek.tv.sample.ui.base.recyclerview.TvVerticalRecyclerView
import de.androidteam.mediathek.tv.sample.ui.base.recyclerview.decorators.LinearEdgeDecoration
import de.androidteam.mediathek.tv.sample.ui.video.adapter.VideoListAdapter

/**
 * A Basic example of a standard fragment that just uses [TvVerticalRecyclerView]
 * to display a list of video rows.
 */
class VideoListFragment : TvBaseFragment() {

    companion object {
        const val TAG = "VideoList"
    }

    private lateinit var adapter: VideoListAdapter
    private lateinit var recyclerView: TvVerticalRecyclerView
    private lateinit var viewModel: VideoListViewModel

    override fun getLayoutId(): Int = R.layout.fragment_video_list

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(VideoListViewModel::class.java)
        viewModel.loadVideos()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        recyclerView = view.findViewById(R.id.videoRecyclerView)
        // Add safe vertical padding to RecyclerView.
        // This will apply item decorations for the first and last item
        recyclerView.addItemDecoration(
            LinearEdgeDecoration(
                startPadding = view.resources.getDimensionPixelSize(R.dimen.safe_padding_vertical),
                endPadding = view.resources.getDimensionPixelSize(R.dimen.safe_padding_vertical),
                orientation = RecyclerView.VERTICAL
            )
        )
        adapter = VideoListAdapter()
        recyclerView.adapter = adapter
        viewModel.getVideos().observe(viewLifecycleOwner,
            Observer<List<VideoList>?> { t ->
                if (t != null) {
                    adapter.setItems(t)
                    recyclerView.selectCurrentPosition()
                }
            })
    }

    override fun onFocused() {
        super.onFocused()
        recyclerView.selectCurrentPosition()
    }

    override fun onUnfocused() {
        super.onUnfocused()
        recyclerView.unselectCurrentPosition()
    }

    override fun dispatchKeyEvent(event: KeyEvent): Boolean {
        // Consume all edges instead of the left one so we can open the drawer
        return recyclerView.dispatchKeyEventAndConsumeEdges(
            event,
            consumeEdgeLeft = false,
            consumeEdgeTop = true,
            consumeEdgeRight = true,
            consumeEdgeBottom = true
        )
    }

}
