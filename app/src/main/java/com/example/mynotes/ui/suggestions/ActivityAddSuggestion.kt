package com.example.mynotes.ui.suggestions

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.example.mynotes.databinding.ActivityAddSuggestionBinding
import com.example.mynotes.room.entity.PostEntity
import com.example.mynotes.ui.addaction.viewmodel.ViewModelAddAction
import kotlinx.coroutines.launch
import timber.log.Timber
import java.util.*

class ActivityAddSuggestion : AppCompatActivity() {
    private lateinit var binding: ActivityAddSuggestionBinding
    private var recSuggestion: String? = null
    private val viewModelAddAction: ViewModelAddAction by viewModels()
    private var personID: Long? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddSuggestionBinding.inflate(layoutInflater)
        setContentView(binding.root)

        handleExtrasFromIntent()

        if (recSuggestion != null) {
            binding.etActionName.setText(recSuggestion)
        }


        binding.btnSavePost.setOnClickListener {
                insertPost()
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

    private fun handleExtrasFromIntent() {
        recSuggestion = intent.extras?.getString("suggestion")
        personID = intent.extras?.getLong("personid")

        Timber.d("person id rec is $personID")
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
            Toast.makeText(this@ActivityAddSuggestion, "Post Added", Toast.LENGTH_LONG).show()

            finish()
        }


    }
}