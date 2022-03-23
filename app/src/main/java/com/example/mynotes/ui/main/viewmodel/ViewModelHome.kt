package com.example.mynotes.ui.main.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mynotes.room.entity.CommonEntity
import com.example.mynotes.room.entity.PersonEntity
import com.example.mynotes.room.entity.PostEntity
import com.example.mynotes.utils.DbHelper
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import timber.log.Timber

class ViewModelHome : ViewModel() {
    var observableList: LiveData<List<PersonEntity>>? = MutableLiveData()

    init {
        Timber.d("isLuanched?")
        viewModelScope.launch(Dispatchers.IO) {
            observableList = DbHelper.getAllPersons()
        }
    }


    private var dataList: MutableList<CommonEntity> = mutableListOf()
    var obvData: MutableLiveData<List<CommonEntity>>? = MutableLiveData()

    fun getCommonData() {
        viewModelScope.launch {
            dataList.clear()
            dataList.addAll(DbHelper.getListCommon()!!)
            obvData?.postValue(dataList)
        }

    }

    private var postList: MutableList<PostEntity> = mutableListOf()
    var obvDataPosts: LiveData<List<PostEntity?>> = MutableLiveData()
    fun getNoOfPosts() {
        viewModelScope.launch {
            postList.clear()
            //postList.addAll(DbHelper.getAllPosts())
            //obvDataPosts?.postValue(postList)
            obvDataPosts = DbHelper.getAllPosts()
        }
    }


}