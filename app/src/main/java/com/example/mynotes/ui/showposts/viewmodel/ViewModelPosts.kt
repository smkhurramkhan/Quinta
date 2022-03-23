package com.example.mynotes.ui.showposts.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mynotes.room.entity.PersonEntity
import com.example.mynotes.room.entity.PostEntity
import com.example.mynotes.utils.DbHelper
import kotlinx.coroutines.launch
import timber.log.Timber

class ViewModelPosts : ViewModel() {

    private var dataList: MutableList<PostEntity>? = mutableListOf()
    var observableList: MutableLiveData<List<PostEntity>> = MutableLiveData()


    private var postLists: MutableList<PostEntity>? = mutableListOf()
    var obsPostList: LiveData<List<PostEntity?>> = MutableLiveData()


    fun getAllPosts(personId: Long) {
        viewModelScope.launch {

            dataList?.clear()

            dataList = DbHelper.getPersonSpecificPost(personId)

            observableList.postValue(dataList)
        }

    }

    fun getPost() {
        viewModelScope.launch {
            postLists?.clear()
           // postLists?.addAll(DbHelper.getAllPosts())

            //Timber.d("size of list is ${postLists?.size}")
            //obsPostList.postValue(postLists)

            obsPostList  =DbHelper.getAllPosts()
        }
    }

    fun deleteUser(personEntity: PersonEntity) {
        viewModelScope.launch {
            DbHelper.deletePerson(personEntity)
        }
    }

}