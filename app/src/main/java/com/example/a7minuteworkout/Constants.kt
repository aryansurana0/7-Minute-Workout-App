package com.example.a7minuteworkout

object Constants {

    fun getDefaultExerciseList(): ArrayList<ExerciseModel> {
        val jumpingJacks = ExerciseModel(
            0,
            "Jumping Jacks",
            R.drawable.ic_jumping_jacks
        )

        val wallChair = ExerciseModel(
            1,
            "Wall Chair",
            R.drawable.ic_wall_chair
        )

        val pushUps = ExerciseModel(
            2,
            "Push Ups",
            R.drawable.ic_push_ups
        )

        val abdominalCrunches =
            ExerciseModel(
                3,
                "Abdominal Crunches",
                R.drawable.ic_abdominal_crunches
            )

        val stepUpOntoChair =
            ExerciseModel(
                4,
                "Step Up onto Chair",
                R.drawable.ic_step_up_onto_chair
            )

        val squats = ExerciseModel(
            5,
            "Squats",
            R.drawable.ic_squats
        )

        val tricepDipsOnChair =
            ExerciseModel(
                6,
                "Tricep Dips On Chair",
                R.drawable.ic_tricep_dips_on_chair
            )

        val plank = ExerciseModel(
            7,
            "Plank",
            R.drawable.ic_plank
        )

        val highKneesRunningInPlace =
            ExerciseModel(
                8, "High Knees Running In Place",
                R.drawable.ic_high_knees_running_in_place
            )

        val lunges = ExerciseModel(
            9,
            "Lunges",
            R.drawable.ic_lunges
        )

        val pushUpAndRotation =
            ExerciseModel(
                10,
                "Push Up and Rotation",
                R.drawable.ic_push_up_and_rotation
            )

        val sidePlank = ExerciseModel(
            11,
            "Side Plank",
            R.drawable.ic_side_plank
        )

        return arrayListOf(
            jumpingJacks,
            wallChair,
            pushUps,
            abdominalCrunches,
            stepUpOntoChair,
            squats,
            tricepDipsOnChair,
            plank,
            highKneesRunningInPlace,
            lunges,
            pushUpAndRotation,
            sidePlank
        )
    }

}