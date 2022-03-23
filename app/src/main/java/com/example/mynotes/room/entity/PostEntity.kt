package com.example.mynotes.room.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity
class PostEntity : Serializable {
    @PrimaryKey(autoGenerate = true)
    var postid: Long = 0
    var postName: String? = null
    var dueDate: String? = null
    var dueTime: String? = null
    var frequency: String? = null
    var postnotes: String? = null
    var isCompleted: Boolean = false
    var personid: Long? = null
}