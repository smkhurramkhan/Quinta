package com.example.mynotes.ui.main

import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.mynotes.R
import com.example.mynotes.databinding.ActivityMainBinding
import com.example.mynotes.room.entity.CommonEntity
import com.example.mynotes.room.entity.PersonEntity
import com.example.mynotes.ui.addperson.ActivityAddPerson
import com.example.mynotes.ui.main.adapter.AdapterHome
import com.example.mynotes.ui.main.viewmodel.ViewModelHome
import com.example.mynotes.ui.showpersons.ActivityShowPersons
import com.example.mynotes.ui.showposts.ActivityAllPosts
import com.example.mynotes.ui.upcomingevents.ActivityUpComingEvents
import com.example.mynotes.utils.SharedPref
import com.google.gson.Gson
import kotlinx.android.synthetic.main.activity_main.*
import timber.log.Timber


class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var viewModel: ViewModelHome
    private lateinit var adapterHome: AdapterHome
    private var dataList: MutableList<CommonEntity> = mutableListOf()
    private lateinit var sharedPref: SharedPref

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        Timber.d("onCreate")

        sharedPref = SharedPref(this)

        viewModel = ViewModelProvider(this)[ViewModelHome::class.java]

        setupAdapter()


        binding.apply {

            personName.text = sharedPref.personName

            btnAddPerson.setOnClickListener {
                val intent = Intent(this@MainActivity, ActivityAddPerson::class.java)
                startActivity(intent)
            }
            btnViewAll.setOnClickListener {
                val intent = Intent(this@MainActivity, ActivityShowPersons::class.java)
                startActivity(intent)
            }

            personName.setOnClickListener {
                showEditNameDialog()
            }

            events.setOnClickListener {
                val intent = Intent(this@MainActivity, ActivityUpComingEvents::class.java)
                startActivity(intent)
            }

        }


    }

    private fun showEditNameDialog() {
        val alert: AlertDialog.Builder = AlertDialog.Builder(this)
        val edittext = EditText(this)
        alert.setMessage("Edit Name")

        alert.setView(edittext)

        alert.setPositiveButton("Update") { _, _ ->
            val personUpdatedName = edittext.text.toString()
            sharedPref.personName = personUpdatedName

            binding.personName.text = personUpdatedName
        }

        alert.setNegativeButton("Cancel") { _, _ ->
        }

        alert.show()
    }

    override fun onResume() {
        super.onResume()
        getData()
    }

    private fun getData() {
        viewModel.getCommonData()

        viewModel.obvData?.observe(this, {
            Timber.d("common list size is ${it.size}")
            Timber.d("person specs is ${Gson().toJson(it)}")
            dataList.clear()

            dataList.addAll(it)

            dataList.reverse()

            if (dataList.isEmpty()) {
                noData.show()
            } else {
                noData.hide()
            }
            adapterHome.setList(dataList)


        })

        viewModel.observableList?.observe(this, {
            Timber.d("size of list is ${it.size}")


        })

        viewModel.getNoOfPosts()
        viewModel.obvDataPosts?.observe(this, {
            if (it.isNotEmpty()) {
                binding.events.text =
                    it.size.toString().plus(" ").plus(getString(R.string.upcoming_events))
            } else {
                binding.events.text = getString(R.string.noevents)
            }
        })
    }

    private fun View.show() {
        this.visibility = View.VISIBLE
    }

    private fun View.hide() {
        this.visibility = View.GONE
    }

    private fun setupAdapter() {
        adapterHome = AdapterHome(dataList = dataList,
            context = this,
            onClick = {

                val person = PersonEntity().apply {
                    Timber.d("----------------------------------------------------")
                    Timber.d("person id inside apply is ${dataList[it].personid}")

                    personid = dataList[it].newId!!
                    firstname = dataList[it].firstname
                    lastname = dataList[it].lastname
                    relationShip = dataList[it].relationShip
                    company = dataList[it].company
                    interests = dataList[it].interests
                    dates = dataList[it].dates
                    loveLanguages = dataList[it].loveLanguages
                    personnotes = dataList[it].personnotes
                    newId = dataList[it].newId

                }

                val intent = Intent(this, ActivityAllPosts::class.java)
                intent.putExtra("person", person)
                startActivity(intent)

            }
        )
        binding.rvPersons.layoutManager = LinearLayoutManager(this)
        binding.rvPersons.adapter = adapterHome
    }
}