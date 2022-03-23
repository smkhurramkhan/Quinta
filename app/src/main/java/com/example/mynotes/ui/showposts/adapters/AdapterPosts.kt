package com.example.mynotes.ui.showposts.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.mynotes.databinding.ItemAllPostsBinding
import com.example.mynotes.room.entity.PostEntity
import com.example.mynotes.ui.showposts.viewholders.VHAllPosts
import com.example.mynotes.utils.DbHelper
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


class AdapterPosts(
    private var dataList: MutableList<PostEntity?>,
    private val context: Context,
    private val onClick: (position: Int) -> Unit
) : RecyclerView.Adapter<VHAllPosts>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VHAllPosts {
        return VHAllPosts(
            ItemAllPostsBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )

        )
    }

    override fun onBindViewHolder(holder: VHAllPosts, position: Int) {
        val posts = dataList[position]

        holder.binding.checkBox.isChecked = posts!!.isCompleted

        holder.binding.apply {
            postName.text = posts.postName
            dueDate.text = posts.dueDate

            parent.setOnClickListener {
                onClick(position)
            }
            checkBox.setOnClickListener {
                CoroutineScope(Dispatchers.Main).launch {
                    if (!posts.isCompleted) {
                        Toast.makeText(context, "Action Completed", Toast.LENGTH_LONG).show()
                        DbHelper.updatePostTask(posts.postid, true)
                        checkBox.isChecked = true
                    } else {
                        checkBox.isChecked = true
                        Toast.makeText(context, "Action already completed", Toast.LENGTH_LONG)
                            .show()
                    }
                }
            }
        }
    }

    fun toggleCheck() {
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int {
        return dataList.size
    }

    fun setList(list: MutableList<PostEntity?>) {
        dataList = list
        notifyDataSetChanged()
    }
}