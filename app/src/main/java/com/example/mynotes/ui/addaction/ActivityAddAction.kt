package com.example.mynotes.ui.addaction

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.example.mynotes.databinding.ActivityAddActionBinding
import com.example.mynotes.room.entity.PostEntity
import com.example.mynotes.ui.addaction.viewmodel.ViewModelAddAction
import kotlinx.android.synthetic.main.item_card_bg.*
import kotlinx.coroutines.launch
import timber.log.Timber
import java.util.*


class ActivityAddAction : AppCompatActivity() {

    private val viewModelAddAction: ViewModelAddAction by viewModels()
    private lateinit var binding: ActivityAddActionBinding
    private var postModel: PostEntity? = null
    private var personID: Long = 0

    override

    fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddActionBinding.inflate(layoutInflater)
        setContentView(binding.root)

        handleExtras()

        if (postModel != null) {
            setDataInViews()
        }


        binding.btnSavePost.setOnClickListener {
            if (postModel == null)
                insertPost() else updatePost()
        }

        binding.btnDiscard.setOnClickListener {
            finish()
        }


        binding.etDueTime.setOnClickListener {
            timePickerDialog()
        }

        binding.etDueDate.setOnClickListener {
            showDatePickerDialog()
        }

    }

    private fun updatePost() {
        val postEntity = PostEntity().apply {
            postid = postModel?.postid!!
            postName = binding.etActionName.text.toString()
            dueDate = binding.etDueDate.text.toString()
            dueTime = binding.etDueTime.text.toString()
            frequency = binding.etFrequency.text.toString()
            postnotes = binding.etNotes.text.toString()
            isCompleted = false
            personid = postModel?.personid
        }

        Timber.d("---------------------------------------------------------------")
        Timber.d("update personid is ${postEntity.personid}")
        Timber.d("updated postname is ${postEntity.postName}")
        Timber.d("updated postdate is ${postEntity.dueDate}")
        Timber.d("updated posttime is ${postEntity.dueTime}")
        Timber.d("updated postid is ${postEntity.postid}")
        Timber.d("updated postnotes is ${postEntity.postnotes}")
        Timber.d("updated frequency is ${postEntity.frequency}")

        val updatePerson = viewModelAddAction.updatePost(postEntity)

        Timber.d("updated from activity $updatePerson")

        finish()

        Toast.makeText(this, "Action updated", Toast.LENGTH_LONG).show()


    }

    private fun setDataInViews() {
        binding.etDueDate.setText(postModel?.dueDate)
        binding.etActionName.setText(postModel?.postName)
        binding.etDueTime.setText(postModel?.dueTime)
        binding.etFrequency.setText(postModel?.frequency)
        binding.etNotes.setText(postModel?.postnotes)

    }


    private fun showDatePickerDialog() {
        val c = Calendar.getInstance()
        val mYear = c[Calendar.YEAR]
        val mMonth = c[Calendar.MONTH]
        val mDay = c[Calendar.DAY_OF_MONTH]

        // Launch Date Picker Dialog

        // Launch Date Picker Dialog
        val dpd = DatePickerDialog(
            this,
            { _, year, monthOfYear, dayOfMonth -> // Display Selected date in textbox
                binding.etDueDate.setText(
                    StringBuilder().append(dayOfMonth).append("/").append(monthOfYear + 1)
                        .append("/").append(year)

                )
            }, mYear, mMonth, mDay
        )
        dpd.show()
    }

    private fun timePickerDialog() {
        val mcurrentTime: Calendar = Calendar.getInstance()
        val hour: Int = mcurrentTime.get(Calendar.HOUR_OF_DAY)
        val minute: Int = mcurrentTime.get(Calendar.MINUTE)
        val mTimePicker = TimePickerDialog(
            this,
            { _, selectedHour, selectedMinute ->
                showTime(selectedHour, selectedMinute)
            },
            hour,
            minute,
            false
        )

        mTimePicker.setTitle("Select Time")
        mTimePicker.show()
    }

    private fun showTime(hour: Int, min: Int) {
        val format: String?
        var mHour = hour
        when {
            mHour == 0 -> {
                mHour += 12
                format = "AM"
            }
            mHour == 12 -> {
                format = "PM"
            }
            mHour > 12 -> {
                mHour -= 12
                format = "PM"
            }
            else -> {
                format = "AM"
            }
        }
        binding.etDueTime.setText(
            StringBuilder().append(mHour).append(" : ").append(min)
                .append(" ").append(format)
        )
    }

    private fun handleExtras() {
        personID = intent.extras!!.getLong("personid")

        postModel = intent?.extras?.getSerializable("postEntity") as PostEntity?

        Timber.d("post id name ${postModel?.postName}")
        Timber.d("person id is $personID")
    }

    private fun insertPost() {
        lifecycleScope.launch {
            val post = PostEntity().apply {
                postName = binding.etActionName.text.toString()
                dueDate = binding.etDueDate.text.toString()
                dueTime = binding.etDueTime.text.toString()
                frequency = binding.etFrequency.text.toString()
                postnotes = binding.etNotes.text.toString()
                isCompleted = false
                personid = personID

            }

            Timber.d("post is $post")
            Timber.d("post name is ${post.postName}")

            val id = viewModelAddAction.insertPost(post)
            Timber.d("new person added iD is $id")
            Toast.makeText(this@ActivityAddAction, "Post Added", Toast.LENGTH_LONG).show()

            finish()
        }


    }

}