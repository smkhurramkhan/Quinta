package com.example.mynotes.ui.lovelanguages.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.mynotes.databinding.ItemLoveLanguagesBinding
import com.example.mynotes.ui.lovelanguages.model.ModelLoveLanguages
import com.example.mynotes.ui.lovelanguages.viewholders.VHLoveLanguages

class AdapterLoveLanguages(
    val dataList: MutableList<ModelLoveLanguages>,
    val context: Context
) : RecyclerView.Adapter<VHLoveLanguages>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VHLoveLanguages {
        return VHLoveLanguages(
            ItemLoveLanguagesBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: VHLoveLanguages, position: Int) {
        val loveLang = dataList[position]

        holder.binding.tvLoveLanguages.text = "+ ${loveLang.name}"

        if (loveLang.check) {
            holder.binding.check.isChecked = true
            loveLang.check = true
        } else {
            holder.binding.check.isChecked = false
            loveLang.check = false
        }


        holder.binding.check.setOnCheckedChangeListener { _, isChecked ->
            if (getListCount() < 3) {
                loveLang.check = isChecked
            } else {
                holder.binding.check.isChecked = false
                loveLang.check = false
                Toast.makeText(context, "Sorry! Maximum 3 languages allowed", Toast.LENGTH_SHORT)
                    .show()
            }
        }



    }

    private fun getListCount(): Int {
        var result = 0
        for (z in dataList.indices) {
            if (dataList[z].check) {
                result++
            }
        }
        return result
    }

    override fun getItemCount(): Int {
        return dataList.size
    }
}