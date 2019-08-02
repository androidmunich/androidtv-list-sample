package de.androidteam.mediathek.tv.sample.ui.video.adapter

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.ColorRes
import androidx.core.content.ContextCompat
import com.bumptech.glide.Glide
import de.androidteam.mediathek.tv.sample.R
import de.androidteam.mediathek.tv.sample.data.Movie
import de.androidteam.mediathek.tv.sample.ui.base.recyclerview.TvItemVH

class VideoVH(view: View) : TvItemVH(view) {

    private val titleTextView: TextView = view.findViewById(R.id.videoTitleTextView)
    private val subtitleTextView: TextView = view.findViewById(R.id.videoSubtitleTextView)
    private val imageView: ImageView = view.findViewById(R.id.videoImageView)

    fun bind(video: Movie) {
        Glide.with(itemView).load(video.backgroundImageUrl).into(imageView)
        titleTextView.text = video.title
        subtitleTextView.text = video.studio
    }

    override fun onFocused() {
        super.onFocused()
        titleTextView.setTextColor(getColor(R.color.color_item_selected_text))
        subtitleTextView.setTextColor(getColor(R.color.color_item_selected_text))
        itemView.setBackgroundColor(getColor(R.color.color_item_selected_background))
    }

    override fun onUnfocused() {
        super.onUnfocused()
        titleTextView.setTextColor(getColor(R.color.color_item_unselected_text))
        subtitleTextView.setTextColor(getColor(R.color.color_item_unselected_text))
        itemView.setBackgroundColor(getColor(R.color.color_item_unselected_background))
    }

    private fun getColor(@ColorRes colorRes: Int): Int {
        return ContextCompat.getColor(itemView.context, colorRes)
    }
}
