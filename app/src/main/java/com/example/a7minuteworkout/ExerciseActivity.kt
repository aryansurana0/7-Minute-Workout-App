package com.example.a7minuteworkout

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import com.example.a7minuteworkout.databinding.ActivityExerciseBinding
import com.example.a7minuteworkout.helpers.MediaPlayerHelper
import com.example.a7minuteworkout.helpers.TextToSpeechHelper

class ExerciseActivity : AppCompatActivity() {
    private lateinit var binding: ActivityExerciseBinding

    private var countDownTimer: CountDownTimer? = null
    private val restTime: Long = 10000
    private val exerciseTime: Long = 30000
    private var restProgress = 0
    private var exerciseProgress = 0

    private lateinit var exerciseList: ArrayList<ExerciseModel>
    private var currentExerciseId = -1

    private lateinit var ttsHelper: TextToSpeechHelper
    private lateinit var mediaPlayerHelper: MediaPlayerHelper
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityExerciseBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
//        setContentView(R.layout.activity_exercise)

        setSupportActionBar(binding.toolbarExercise)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        binding.toolbarExercise.setNavigationOnClickListener {
            onBackPressedDispatcher.onBackPressed()
        }

        exerciseList = Constants.getDefaultExerciseList()

        ttsHelper = TextToSpeechHelper(this)
        mediaPlayerHelper = MediaPlayerHelper(applicationContext)

        setupRestView()
    }

    private fun setupRestView() {
        binding.progressBarTimer.max = (restTime / 1000).toInt()
        if (countDownTimer != null) {
            countDownTimer?.cancel()
            restProgress = 0
        }
        setRestProgressBar()
    }

    private fun setRestProgressBar() {
        binding.ivExercise.setImageResource(
            resources.getIdentifier(
                "img_main_page",
                "drawable",
                packageName
            )
        )
        binding.tvExercise.text = when (currentExerciseId) {
            -1 -> "GET READY"
            exerciseList.size - 1 -> "WELL DONE!"
            else -> "Just ${exerciseList.size - currentExerciseId - 1} More"
        }

        val upNextText = if (currentExerciseId < exerciseList.size - 1) {
            "Up Next: ${exerciseList[currentExerciseId + 1].getName()}"
        } else {
            "Grab Some Water"
        }
        binding.tvUpNext.text = upNextText

        binding.progressBarTimer.progress = restProgress
        countDownTimer = object : CountDownTimer(restTime, 1000) {
            override fun onTick(millisUntilFinished: Long) {
                restProgress++
                binding.progressBarTimer.progress = (restTime / 1000 - restProgress).toInt()
                binding.tvTimer.text = (restTime / 1000 - restProgress).toString()
                when (restProgress) {
                    2 -> ttsHelper.speak(upNextText)
                    7 -> mediaPlayerHelper.startMediaPlayer()
                }
            }

            override fun onFinish() {
                currentExerciseId++
//                Toast.makeText(
//                    this@ExerciseActivity,
//                    "rest done",
//                    Toast.LENGTH_SHORT
//                ).show()
                setupExerciseView()
            }
        }.start()

    }

    private fun setupExerciseView() {
        binding.progressBarTimer.max = (exerciseTime / 1000).toInt()
        if (countDownTimer != null) {
            countDownTimer?.cancel()
            restProgress = 0
            exerciseProgress = 0
        }
        setExerciseProgressBar()
    }

    private fun setExerciseProgressBar() {
        if (currentExerciseId < exerciseList.size) {
            val currentExercise = exerciseList[currentExerciseId]
            val exerciseName = currentExercise.getName()
            binding.tvExercise.text = exerciseName

            val exerciseImageStr = "ic_${exerciseName.replace(' ', '_').lowercase()}"
            val exerciseImageId = resources.getIdentifier(exerciseImageStr, "drawable", packageName)
            binding.ivExercise.setImageResource(exerciseImageId)

            val upNextText = if (currentExerciseId < exerciseList.size - 1) {
                "Up Next: Rest"
            } else {
                "LAST ONE"
            }
            binding.tvUpNext.text = upNextText

            binding.progressBarTimer.progress = exerciseProgress
            countDownTimer = object : CountDownTimer(exerciseTime, 1000) {
                override fun onTick(millisUntilFinished: Long) {
                    exerciseProgress++
                    binding.progressBarTimer.progress =
                        (exerciseTime / 1000 - exerciseProgress).toInt()
                    binding.tvTimer.text = (exerciseTime / 1000 - exerciseProgress).toString()
                    when (exerciseProgress) {
                        24 -> ttsHelper.speak(upNextText)
                        27 -> mediaPlayerHelper.startMediaPlayer()
                    }
                }

                override fun onFinish() {
//                    Toast.makeText(
//                        this@ExerciseActivity,
//                        "ex done",
//                        Toast.LENGTH_SHORT
//                    ).show()
                    setupRestView()
                }
            }.start()
        }

    }

    override fun onDestroy() {
        super.onDestroy()
        if (countDownTimer != null) {
            countDownTimer?.cancel()
            restProgress = 0
            exerciseProgress = 0
        }
        ttsHelper.stopThenShutdown()
        mediaPlayerHelper.stopMediaPlayer()
    }
}