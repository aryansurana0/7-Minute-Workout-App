package com.example.a7minuteworkout.helpers

import android.content.Context
import android.media.MediaPlayer
import android.net.Uri
import com.example.a7minuteworkout.R

class MediaPlayerHelper(private val applicationContext: Context) {
    private lateinit var mediaPlayer: MediaPlayer
    private lateinit var soundUri: Uri

    init {
        try {
            soundUri = Uri.parse("android.resource://com.example.a7minuteworkout/${R.raw.sport_start_3bleeps}")
            mediaPlayer = MediaPlayer.create(applicationContext, soundUri)
            mediaPlayer.isLooping = false

        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    fun startMediaPlayer() {
        try {
            mediaPlayer.start()
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    fun stopMediaPlayer() {
        mediaPlayer.stop()
    }

}