package com.example.mynotes.ui.showpersons.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mynotes.room.entity.PersonEntity
import com.example.mynotes.utils.DbHelper
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ViewModelAllPerson : ViewModel() {

    var observableList: LiveData<List<PersonEntity>>? = MutableLiveData()

    init {
        viewModelScope.launch(Dispatchers.IO) {
            observableList = DbHelper.getAllPersons()
        }
    }
}