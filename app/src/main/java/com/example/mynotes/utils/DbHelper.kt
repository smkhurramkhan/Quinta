package com.example.mynotes.utils

import android.content.Context
import androidx.lifecycle.LiveData
import com.example.mynotes.room.database.AppDatabase
import com.example.mynotes.room.entity.CommonEntity
import com.example.mynotes.room.entity.PersonEntity
import com.example.mynotes.room.entity.PostEntity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import timber.log.Timber

object DbHelper {
    private var appDatabase: AppDatabase? = null

    fun getInstance(context: Context) {
        appDatabase = AppDatabase.getDatabase(context)
    }

    /*PERSON DAO METHODS*/
    suspend fun insertPerson(personEntity: PersonEntity): Long? {
        return withContext(Dispatchers.IO) {
            appDatabase?.personDao()?.insertPerson(personEntity)
        }
    }

    fun getAllPersons(): LiveData<List<PersonEntity>>? {
        return appDatabase?.personDao()?.getAllPersons()
    }

    suspend fun deletePerson(person: PersonEntity) {
        return withContext(Dispatchers.IO) {
            appDatabase?.personDao()?.deletePerson(person)
        }
    }

    suspend fun deletePersonByID(id: Int) {
        return withContext(Dispatchers.IO) {
            appDatabase?.personDao()?.deletePersonByID(id)
        }
    }

    suspend fun updatePerson(person: PersonEntity): Int? {
        Timber.d("person id is ${person.personid}")
        return withContext(Dispatchers.IO) {
            appDatabase?.personDao()?.updatePerson(person)
        }
    }

    suspend fun updatePersonNewId(newId: Long) {
        return withContext(Dispatchers.IO) {
            appDatabase?.personDao()?.updatePersonID(newId)
        }
    }

    suspend fun updatePersonbyData(
        id: Long,
        firstName: String, lastname: String, relationShip: String, company: String,
        interests: String, dates: String, loveLanguages: String, notes: String
    ) {
        return withContext(Dispatchers.IO) {
            appDatabase?.personDao()?.updatePersonbyData(
                id,
                firstName, lastname, relationShip, company,
                interests, dates, loveLanguages, notes
            )
        }
    }


/*POSTS DAO*/

    suspend fun insertPost(postEntity: PostEntity): Long? {
        return withContext(Dispatchers.IO) {
            appDatabase?.postDao()?.insertPost(postEntity)
        }
    }

    fun getAllPosts(): LiveData<List<PostEntity?>> {
        return appDatabase?.postDao()?.getAllPosts()!!

    }

    suspend fun getPersonSpecificPost(personId: Long): MutableList<PostEntity>? {
        return withContext(Dispatchers.IO) {
            appDatabase?.postDao()?.getPersonsPost(personId)
        }
    }

    suspend fun deletePostId(id: Int) {
        return withContext(Dispatchers.IO) {
            appDatabase?.postDao()?.deletePostId(id)
        }
    }

    suspend fun deletePost(postEntity: PostEntity) {
        return withContext(Dispatchers.IO) {
            appDatabase?.postDao()?.deletePost(postEntity)
        }
    }

    suspend fun updatePost(
        id: Int,
        postName: String, dueDate: String, dueTime: String, frequency: Int,
        notes: String
    ) {
        return withContext(Dispatchers.IO) {
            appDatabase?.postDao()?.updatePost(id, postName, dueDate, dueTime, frequency, notes)
        }
    }


    suspend fun updatePost(postEntity: PostEntity): Int? {
        Timber.d("post id is ${postEntity.postid}")
        return withContext(Dispatchers.IO) {
            appDatabase?.postDao()?.updatePost(postEntity)
        }
    }

    suspend fun updatePostTask(postId: Long, completion: Boolean) {
        return withContext(Dispatchers.IO) {
            appDatabase?.postDao()?.updatePostTask(postId, completion)
        }
    }


/*COMMON DAO*/

    suspend fun getListCommon(): MutableList<CommonEntity>? {
        return withContext(Dispatchers.IO) {
            Timber.d("getListCOmmon ${appDatabase?.commonDao()?.getList()}")
            appDatabase?.commonDao()?.getList()
        }
    }
}