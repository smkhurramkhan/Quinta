package com.example.mynotes.ui.showposts

import android.R
import android.app.AlertDialog
import android.content.ActivityNotFoundException
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.example.mynotes.databinding.ActivityAllPostsBinding
import com.example.mynotes.room.entity.PersonEntity
import com.example.mynotes.room.entity.PostEntity
import com.example.mynotes.ui.addaction.ActivityAddAction
import com.example.mynotes.ui.addperson.ActivityAddPerson
import com.example.mynotes.ui.showposts.adapters.AdapterPosts
import com.example.mynotes.ui.showposts.viewmodel.ViewModelPosts
import com.example.mynotes.ui.suggestions.ActivitySuggestions
import kotlinx.coroutines.launch
import timber.log.Timber


class ActivityAllPosts : AppCompatActivity() {
    private lateinit var binding: ActivityAllPostsBinding
    private lateinit var personModel: PersonEntity
    private val viewModel: ViewModelPosts by viewModels()
    private lateinit var adapterPosts: AdapterPosts
    private var dataList: MutableList<PostEntity?> = mutableListOf()
    private var interests: MutableList<String>? = mutableListOf()
    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        binding = ActivityAllPostsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        handleExtrasFromIntent()

        initViews()

        setupAdapter()

    }

    override fun onResume() {
        super.onResume()
        getData()
    }

    private fun getData() {
        viewModel.getAllPosts(personModel.personid)
        viewModel.observableList.observe(this, {
            Timber.d("posts list size is ${it.size}")

            dataList.clear()

            dataList.addAll(it)

            dataList.reverse()

            adapterPosts.setList(dataList)

            if (dataList.isNotEmpty()) {
                binding.noPosts.visibility = View.GONE
            } else {
                binding.noPosts.visibility = View.VISIBLE
            }
        })
    }

    private fun initViews() {
        binding.apply {
            personName.text = personModel.firstname


            etRelationshipCompany.text =
                personModel.relationShip
                    .plus(" ")
                    .plus(personModel.company)

            notes.text = personModel.personnotes

            btnAddPost.setOnClickListener {
                val intent = Intent(this@ActivityAllPosts, ActivityAddAction::class.java)
                intent.putExtra("personid", personModel.personid)
                startActivity(intent)
            }

            btnPersonDelete.setOnClickListener {
                showDeleteDialog()
            }

            binding.btnPersonEdit.setOnClickListener {
                val intent = Intent(this@ActivityAllPosts, ActivityAddPerson::class.java)
                intent.putExtra("person", personModel)
                startActivity(intent)
            }

            binding.btnArticles.setOnClickListener {
                interests?.let { it1 -> searchInterests(it1) }
            }

            binding.btnSuggestions.setOnClickListener {
                val intent = Intent(this@ActivityAllPosts, ActivitySuggestions::class.java)
                intent.putExtra("person", personModel)
                startActivity(intent)
            }
        }
    }

    private fun searchInterests(interest: MutableList<String>) {
        try {
            val url = "https://www.google.com/search?q=".plus(interest[0]).plus(" OR ")
                .plus(interest[1])
                .plus(" OR ")
                .plus(interest[2])
            val i = Intent(Intent.ACTION_VIEW)
            i.data = Uri.parse(url)
            startActivity(i)
        } catch (exception: ActivityNotFoundException) {
            Toast.makeText(this, "Browser not found", Toast.LENGTH_LONG).show()
        }

    }

/*    private fun getRandomChestItem(list: List<String>?): String {
        return list!!.random()
    }*/

    private fun showDeleteDialog() {
        AlertDialog.Builder(this)
            .setTitle("Delete Person")
            .setMessage("Are you sure you want to delete this person?")
            .setPositiveButton(
                R.string.ok
            ) { _, _ ->

                lifecycleScope.launch {
                    viewModel.deleteUser(personModel)
                    Toast.makeText(
                        this@ActivityAllPosts,
                        "Person deleted",
                        Toast.LENGTH_LONG
                    ).show()
                    finish()
                }
            }
            .setNegativeButton(R.string.cancel) { _, _ ->
            }
            .setIcon(R.drawable.ic_dialog_alert)
            .show()
    }

    private fun handleExtrasFromIntent() {
        personModel = intent.extras?.getSerializable("person") as PersonEntity

        Timber.d("handleExtrasFromIntent person id is ${personModel.personid}")
        Timber.d("handleExtrasFromIntent person id is ${personModel.firstname}")

        personModel.interests?.split(",")?.let { interests?.addAll(it) }
    }

    private fun setupAdapter() {
        adapterPosts = AdapterPosts(
            dataList,
            this,
            onClick = {
                val intent = Intent(this, ActivityAddAction::class.java)
                intent.putExtra("personid", personModel.personid)
                intent.putExtra("postEntity", dataList[it])
                startActivity(intent)
            }
        )
        binding.rvActionsAndEvents.adapter = adapterPosts
    }
}