package com.example.mynotes.room.entity

import androidx.room.Entity
import androidx.room.PrimaryKey



class CommonEntity {

    var postid = 0
    var postName: String? = null
    var dueDate: String? = null
    var dueTime: String? = null
    var frequency: Int = 0
    var postnotes: String? = null
    var personid: Long? = null
    var newId: Long? = null

    var firstname: String? = null
    var lastname: String? = null
    var relationShip: String? = null
    var company: String? = null
    var interests: String? = null
    var dates: String? = null
    var loveLanguages: String? = null
    var personnotes: String? = null

}