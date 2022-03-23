package com.example.mynotes.room.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.mynotes.room.dao.CommonDao
import com.example.mynotes.room.dao.PersonDao
import com.example.mynotes.room.dao.PostDao
import com.example.mynotes.room.entity.PersonEntity
import com.example.mynotes.room.entity.PostEntity

@Database(
    entities = [PersonEntity::class,
        PostEntity::class],
    version = 4
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun personDao(): PersonDao
    abstract fun postDao(): PostDao
    abstract  fun commonDao(): CommonDao

    companion object {
        fun getDatabase(context: Context): AppDatabase {
            return Room.databaseBuilder(
                context,
                AppDatabase::class.java,
                "notesDB"
            ).fallbackToDestructiveMigration()
                .build()
        }
    }

}