package com.example.mynotes.ui.showpersons

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.example.mynotes.databinding.ActivityShowPersonsBinding
import com.example.mynotes.room.entity.PersonEntity
import com.example.mynotes.ui.addperson.ActivityAddPerson
import com.example.mynotes.ui.showpersons.adapter.AdapterShowPersons
import com.example.mynotes.ui.showpersons.viewmodel.ViewModelAllPerson
import com.example.mynotes.ui.showposts.ActivityAllPosts
import timber.log.Timber

class ActivityShowPersons : AppCompatActivity() {

    private lateinit var viewModel: ViewModelAllPerson
    private lateinit var binding: ActivityShowPersonsBinding
    private lateinit var adapterShowPersons: AdapterShowPersons
    private var dataList: MutableList<PersonEntity> = mutableListOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityShowPersonsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel = ViewModelProvider(this)[ViewModelAllPerson::class.java]

        setupAdapter()


        binding.btnAddPerson.setOnClickListener {
            startActivity(Intent(this, ActivityAddPerson::class.java))
        }

    }

    override fun onResume() {
        super.onResume()
        getData()
    }

    private fun getData() {
        viewModel.observableList?.observe(this, {
            Timber.d("list size is ${it.size}")

            dataList.clear()

            dataList.addAll(it)

            dataList.reverse()


            if (dataList.isNotEmpty()) {
                binding.noData.hide()
                binding.btnAddPerson.hide()
            } else {
                binding.noData.show()
                binding.btnAddPerson.show()
            }

            adapterShowPersons.setList(dataList)


        })
    }

    private fun View.show() {
        this.visibility = View.VISIBLE
    }

    private fun View.hide() {
        this.visibility = View.GONE
    }

    private fun setupAdapter() {
        adapterShowPersons = AdapterShowPersons(
            dataList,
            this,
            onClick = {
                val intent = Intent(this, ActivityAllPosts::class.java)
                intent.putExtra("person", dataList[it])
                startActivity(intent)
            }
        )
        binding.rvAlLPersons.adapter = adapterShowPersons
    }

}