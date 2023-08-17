package com.example.a7minuteworkout

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import android.widget.Toast
import com.example.a7minuteworkout.databinding.ActivityExerciseBinding

class ExerciseActivity : AppCompatActivity() {
    private lateinit var binding: ActivityExerciseBinding

    private var countDownTimer: CountDownTimer? = null
    private val restTime: Long = 2000
    private val exerciseTime: Long = 3000
    private var restProgress = 0
    private var exerciseProgress = 0

    private lateinit var exerciseList: ArrayList<ExerciseModel>
    private var currentExerciseId = -1
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityExerciseBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
//        setContentView(R.layout.activity_exercise)

        setSupportActionBar(binding.toolbarExercise)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        exerciseList = Constants.getDefaultExerciseList()

        binding.toolbarExercise.setNavigationOnClickListener {
            onBackPressedDispatcher.onBackPressed()
        }

        setupRestView()
    }

    private fun setupRestView() {
        binding.progressBarTimer.max = 2
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
            else -> "WELL DONE!"
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
        binding.progressBarTimer.max = 3
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
    }
}