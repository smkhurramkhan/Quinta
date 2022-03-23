package com.example.mynotes.room.dao

import androidx.room.Dao
import androidx.room.Query
import com.example.mynotes.room.entity.CommonEntity

@Dao
interface CommonDao {

    @Query("SELECT * FROM personentity LEFT JOIN postentity ON personentity.personid = postentity.personid  ")
    fun getList(): MutableList<CommonEntity>

}