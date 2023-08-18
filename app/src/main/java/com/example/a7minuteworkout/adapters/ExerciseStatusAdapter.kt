package com.example.a7minuteworkout.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.a7minuteworkout.ExerciseModel
import com.example.a7minuteworkout.R
import com.example.a7minuteworkout.databinding.ItemExerciseStatusBinding

class ExerciseStatusAdapter(val exerciseList: ArrayList<ExerciseModel>) :
    RecyclerView.Adapter<ExerciseStatusAdapter.ViewHolder>() {
    class ViewHolder(binding: ItemExerciseStatusBinding) :
        RecyclerView.ViewHolder(binding.root) {
        val tvExerciseStatusItem = binding.tvExerciseStatusItem
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            ItemExerciseStatusBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun getItemCount(): Int {
        return exerciseList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val exerciseModel = exerciseList[position]
        holder.tvExerciseStatusItem.text = (exerciseModel.getId() + 1).toString()

        when {
            exerciseModel.getIsSelected() -> {
                holder.tvExerciseStatusItem.background = ContextCompat.getDrawable(
                    holder.itemView.context,
                    R.drawable.item_circular_color_white_bg
                )
            }
            exerciseModel.getIsCompleted() -> {
                holder.tvExerciseStatusItem.background = ContextCompat.getDrawable(
                    holder.itemView.context,
                    R.drawable.item_circular_color_accent_bg
                )
            }
            else -> {
                holder.tvExerciseStatusItem.background = ContextCompat.getDrawable(
                    holder.itemView.context,
                    R.drawable.item_circular_color_grey_bg
                )
            }
        }
    }
}