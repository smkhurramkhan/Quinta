package com.example.mynotes.ui.main.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.mynotes.R
import com.example.mynotes.databinding.ItemCardBgBinding
import com.example.mynotes.room.entity.CommonEntity
import com.example.mynotes.ui.main.viewholder.VHHome

class AdapterHome(
    private var dataList: MutableList<CommonEntity>,
    private var context: Context,
    private var onClick: (position: Int) -> Unit
) : RecyclerView.Adapter<VHHome>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VHHome {
        return VHHome(
            ItemCardBgBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: VHHome, position: Int) {
        val person = dataList[position]

        holder.binding.apply {
            personName.text = person.firstname.plus(" ").plus(person.lastname)
            description.text = person.relationShip.plus(" ").plus(person.company)

            if (person.postName == null && person.dueDate == null) {
                upnext.text = context.getString(R.string.upnext)
                due.text = context.getString(R.string.duedate)
            } else {
                upnext.text = context.getString(R.string.upnext).plus(person.postName)
                due.text = context.getString(R.string.duedate).plus(person.dueDate)
            }
        }
        holder.binding.parent.setOnClickListener {
            onClick(position)
        }
    }

    override fun getItemCount(): Int {
        return dataList.size
    }

    fun setList(newList: MutableList<CommonEntity>) {
        dataList = newList
        notifyDataSetChanged()
    }
}