package com.example.mynotes.room.dao

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.room.*
import androidx.room.OnConflictStrategy.REPLACE
import com.example.mynotes.room.entity.PersonEntity

@Dao
interface PersonDao {
    @Insert(onConflict = REPLACE)
    fun insertPerson(person: PersonEntity): Long

    @Query("SELECT * FROM PersonEntity ")
    fun getAllPersons(): LiveData<List<PersonEntity>>


    @Query("delete from PersonEntity where personId=:id")
    fun deletePersonByID(id: Int)

    @Delete
    fun deletePerson(person: PersonEntity)


    @Update
    fun updatePerson(person: PersonEntity): Int


    @Query("UPDATE PersonEntity SET firstName = :firstName, lastname = :lastname, relationShip = :relationShip,company = :company,interests = :interests,dates = :dates,loveLanguages = :loveLanguages,personnotes = :notes WHERE personId =:id")
    fun updatePersonbyData(
        id: Long,
        firstName: String, lastname: String, relationShip: String, company: String,
        interests: String, dates: String, loveLanguages: String, notes: String
    )

    @Query("Update PersonEntity set newId = :newID WHERE personid =:newID")
    fun updatePersonID(newID: Long)
}