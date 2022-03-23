package com.example.mynotes.ui.upcomingevents

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.example.mynotes.databinding.ActivityUpComingEventsBinding
import com.example.mynotes.room.entity.PostEntity
import com.example.mynotes.ui.addaction.ActivityAddAction
import com.example.mynotes.ui.showposts.adapters.AdapterPosts
import com.example.mynotes.ui.showposts.viewmodel.ViewModelPosts
import timber.log.Timber


class ActivityUpComingEvents : AppCompatActivity() {

    private lateinit var binding: ActivityUpComingEventsBinding
    private val viewModel: ViewModelPosts by viewModels()
    private lateinit var adapterPosts: AdapterPosts
    private var dataList: MutableList<PostEntity?> = mutableListOf()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityUpComingEventsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupAdapter()

    }

    private fun setupAdapter() {
        adapterPosts = AdapterPosts(
            dataList,
            this,
            onClick = {
                val intent = Intent(this, ActivityAddAction::class.java)
                intent.putExtra("personid", dataList[it]?.personid)
                intent.putExtra("postEntity", dataList[it])
                startActivity(intent)

            }


        )
        binding.rvAllPosts.adapter = adapterPosts
    }


    override fun onResume() {
        super.onResume()
        getData()
    }

    private fun getData() {
        viewModel.getPost()
        viewModel.obsPostList.observe(this, {
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

}