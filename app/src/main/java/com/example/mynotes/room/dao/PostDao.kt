package com.example.mynotes.room.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.mynotes.room.entity.PostEntity

@Dao
interface PostDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertPost(post: PostEntity): Long

    @Query("SELECT * FROM PostEntity")
    fun getAllPosts(): LiveData<List<PostEntity?>>

    @Query("SELECT * FROM PostEntity WHERE personid=:personId")
    fun getPersonsPost(personId: Long): MutableList<PostEntity>

    @Query("delete from PostEntity where postId=:id")
    fun deletePostId(id: Int)

    @Delete
    fun deletePost(postEntity: PostEntity)


    @Query("UPDATE PostEntity SET postName = :postName, dueDate = :dueDate, dueTime = :dueTime,frequency = :frequency,postnotes = :notes  WHERE postId =:id")
    fun updatePost(
        id: Int,
        postName: String, dueDate: String, dueTime: String, frequency: Int,
        notes: String
    )

    @Query("UPDATE PostEntity SET isCompleted= :completion WHERE postid = :postId")
    fun updatePostTask(postId: Long, completion: Boolean)

    @Update
    fun updatePost(postEntity: PostEntity): Int
}