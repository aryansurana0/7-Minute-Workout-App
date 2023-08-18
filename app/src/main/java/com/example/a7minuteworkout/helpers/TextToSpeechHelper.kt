package com.example.a7minuteworkout.helpers

import android.content.Context
import android.speech.tts.TextToSpeech
import android.util.Log
import java.util.Locale

class TextToSpeechHelper(private val context: Context): TextToSpeech.OnInitListener {
    private val tts: TextToSpeech = TextToSpeech(context, this)

    override fun onInit(status: Int) {
        if (status == TextToSpeech.SUCCESS) {
            tts.language = Locale.US
        }
        else {
            Log.e("TTS", "Initialization failed")
        }
    }

    fun speak(text: String) {
        tts.speak(text, TextToSpeech.QUEUE_FLUSH, null, "")
    }

    fun stopThenShutdown() {
        tts.stop()
        tts.shutdown()
    }
}