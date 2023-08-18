package com.example.a7minuteworkout

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.a7minuteworkout.adapters.ExerciseStatusAdapter
import com.example.a7minuteworkout.databinding.ActivityExerciseBinding
import com.example.a7minuteworkout.helpers.MediaPlayerHelper
import com.example.a7minuteworkout.helpers.TextToSpeechHelper

class ExerciseActivity : AppCompatActivity() {
    private lateinit var binding: ActivityExerciseBinding

    private var countDownTimer: CountDownTimer? = null
    private val restTime: Int = 10
    private val exerciseTime: Int = 30
    private var restProgress = 0
    private var exerciseProgress = 0

    private lateinit var exerciseList: ArrayList<ExerciseModel>
    private var currentExerciseId = -1

    private lateinit var ttsHelper: TextToSpeechHelper
    private lateinit var mediaPlayerHelper: MediaPlayerHelper

    private lateinit var exerciseStatusRVAdapter: ExerciseStatusAdapter

    private lateinit var intentToFinishActivity: Intent
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

        intentToFinishActivity = Intent(this, FinishActivity::class.java)

        setupRestView()
        setupExerciseStatusRV()
    }

    private fun setupExerciseStatusRV() {
        binding.rvExerciseStatus.layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        exerciseStatusRVAdapter = ExerciseStatusAdapter(exerciseList)
        binding.rvExerciseStatus.adapter = exerciseStatusRVAdapter
    }

    private fun setupRestView() {
        binding.progressBarTimer.max = restTime
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
            exerciseList.size - 1 -> "WELL DONE!"
            else -> "GET READY"
//            -1 -> "GET READY"
//            else -> "Just ${exerciseList.size - currentExerciseId - 1} More"
        }

        val upNextText = if (currentExerciseId < exerciseList.size - 1) {
            "Up Next: ${exerciseList[currentExerciseId + 1].getName()}"
        } else {
            "Grab Some Water"
        }
        binding.tvUpNext.text = upNextText

        binding.progressBarTimer.progress = restProgress
        countDownTimer = object : CountDownTimer((restTime * 1000).toLong(), 1000) {
            override fun onTick(millisUntilFinished: Long) {
                restProgress++
                binding.progressBarTimer.progress = (restTime - restProgress)
                binding.tvTimer.text = (restTime - restProgress).toString()
                when (restProgress) {
                    (restTime - 8) -> ttsHelper.speak(upNextText)
                    (restTime - 3) -> mediaPlayerHelper.startMediaPlayer()
                }
            }

            override fun onFinish() {
                currentExerciseId++
//                Toast.makeText(
//                    this@ExerciseActivity,
//                    "rest done",
//                    Toast.LENGTH_SHORT
//                ).show()
                if (currentExerciseId < exerciseList.size) {
                    exerciseList[currentExerciseId].setIsSelected(true)
                    exerciseStatusRVAdapter.notifyItemChanged(currentExerciseId)
                }
                setupExerciseView()
            }
        }.start()

    }

    private fun setupExerciseView() {
        binding.progressBarTimer.max = exerciseTime
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
            countDownTimer = object : CountDownTimer((exerciseTime * 1000).toLong(), 1000) {
                override fun onTick(millisUntilFinished: Long) {
                    exerciseProgress++
                    binding.progressBarTimer.progress =
                        (exerciseTime - exerciseProgress)
                    binding.tvTimer.text = (exerciseTime - exerciseProgress).toString()
                    when (exerciseProgress) {
                        (exerciseTime - 6) -> ttsHelper.speak(upNextText)
                        (exerciseTime - 3) -> mediaPlayerHelper.startMediaPlayer()
                    }
                }

                override fun onFinish() {
//                    Toast.makeText(
//                        this@ExerciseActivity,
//                        "ex done",
//                        Toast.LENGTH_SHORT
//                    ).show()
                    exerciseList[currentExerciseId].setIsSelected(false)
                    exerciseList[currentExerciseId].setIsCompleted(true)
                    exerciseStatusRVAdapter.notifyItemChanged(currentExerciseId)

                    if (currentExerciseId < exerciseList.size - 1) setupRestView()
                    else {
                        startActivity(intentToFinishActivity)
                        finish()
                    }
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