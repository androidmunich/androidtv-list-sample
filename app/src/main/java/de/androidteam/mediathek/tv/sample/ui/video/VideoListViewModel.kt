package de.androidteam.mediathek.tv.sample.ui.video

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import de.androidteam.mediathek.tv.sample.data.MovieList
import de.androidteam.mediathek.tv.sample.data.VideoList

class VideoListViewModel : ViewModel() {

    private val videos = MutableLiveData<List<VideoList>>()

    fun getVideos(): LiveData<List<VideoList>> = videos

    fun loadVideos() {
        val list = arrayListOf<VideoList>()
        MovieList.MOVIE_CATEGORY.forEach {
            val videoList = ArrayList(MovieList.list)
            repeat(4) {
                videoList.addAll(MovieList.list)
            }
            list.add(VideoList(it, videoList.shuffled()))
        }
        videos.postValue(list)
    }


}
