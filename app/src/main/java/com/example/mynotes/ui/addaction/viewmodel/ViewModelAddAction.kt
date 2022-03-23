package com.example.mynotes.ui.addaction.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mynotes.room.entity.PostEntity
import com.example.mynotes.utils.DbHelper
import kotlinx.coroutines.launch
import timber.log.Timber

class ViewModelAddAction : ViewModel() {

    fun insertPost(post: PostEntity): Long? {
        var id: Long? = null
        viewModelScope.launch {
            id = DbHelper.insertPost(post)
        }
        return id
    }


    fun updatePost(post: PostEntity): Int? {
        var id: Int? = null

        viewModelScope.launch {
            id = DbHelper.updatePost(post)

            Timber.d("updated or not $id")
        }
        return id
    }
}