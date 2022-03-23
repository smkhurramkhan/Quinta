package com.example.mynotes.ui.addperson.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.mynotes.room.entity.PersonEntity
import com.example.mynotes.room.entity.PostEntity
import com.example.mynotes.utils.DbHelper
import kotlinx.coroutines.launch
import timber.log.Timber

class ViewModelAddPerson(application: Application) : AndroidViewModel(application) {

    val personId: MutableLiveData<Long> = MutableLiveData()


    fun insertPerson(person: PersonEntity) {
        viewModelScope.launch {
            val id = DbHelper.insertPerson(person)
            personId.postValue(id)
        }
    }

    fun updatePerson(person: PersonEntity): Int? {
        var id: Int? = null

        viewModelScope.launch {
            id = DbHelper.updatePerson(person)

            Timber.d("updated or not $id")
        }
        return id
    }


    fun updatePersonID(newId: Long) {
        viewModelScope.launch {
            DbHelper.updatePersonNewId(newId)
        }
    }

    fun insertPost(post: PostEntity): Long? {
        var id: Long? = null
        viewModelScope.launch {
            id = DbHelper.insertPost(post)
        }
        return id
    }

}