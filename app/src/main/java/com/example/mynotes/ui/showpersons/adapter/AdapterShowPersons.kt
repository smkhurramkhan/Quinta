package com.example.mynotes.ui.showpersons.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.mynotes.databinding.ItemAllPersonsBinding
import com.example.mynotes.room.entity.PersonEntity
import com.example.mynotes.ui.showpersons.viewholder.VHShowPersons

class AdapterShowPersons(
    private var dataList: MutableList<PersonEntity>,
    val context: Context,
    val onClick: (position: Int) -> Unit
) : RecyclerView.Adapter<VHShowPersons>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VHShowPersons {
        return VHShowPersons(
            ItemAllPersonsBinding.inflate(
                LayoutInflater.from(
                    parent.context
                ),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: VHShowPersons, position: Int) {
        val person = dataList[position]

        holder.binding.apply {
            firstname.text = person.firstname
            lastname.text = person.lastname
            etRelationshipCompany.text = person.relationShip.plus(" ").plus(person.company)
            notes.text = person.personnotes

            parent.setOnClickListener {
                onClick(position)
            }

            btnAddAction.setOnClickListener {
                onClick(position)
            }

        }

    }

    override fun getItemCount(): Int {
        return dataList.size
    }

    fun setList(newList: MutableList<PersonEntity>) {
        dataList = newList
        notifyDataSetChanged()
    }

}