package com.example.mynotes.room.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity
class PersonEntity : Serializable {
    @PrimaryKey(autoGenerate = true)
    var personid: Long = 0
    var firstname: String? = null
    var lastname: String? = null
    var relationShip: String? = null
    var company: String? = null
    var interests: String? = null
    var dates: String? = null
    var loveLanguages: String? = null
    var personnotes: String? = null
    var newId: Long? = null


}