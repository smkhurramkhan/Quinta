package com.example.mynotes.ui.addperson

import android.app.DatePickerDialog
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts.StartActivityForResult
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.example.mynotes.databinding.ActivityAddPersonBinding
import com.example.mynotes.room.entity.PersonEntity
import com.example.mynotes.room.entity.PostEntity
import com.example.mynotes.ui.addperson.viewmodel.ViewModelAddPerson
import com.example.mynotes.ui.lovelanguages.ActivityLoveLanguages
import com.example.mynotes.ui.lovelanguages.model.ModelLoveLanguages
import com.google.gson.Gson
import kotlinx.coroutines.launch
import timber.log.Timber
import java.util.*


class ActivityAddPerson : AppCompatActivity() {
    private lateinit var binding: ActivityAddPersonBinding
    private val viewModelAddPerson: ViewModelAddPerson by viewModels()
    private var personModel: PersonEntity? = null
    private var interests: MutableList<String>? = mutableListOf()
    private var love: MutableList<String>? = mutableListOf()

    private val someActivityResultLauncher = registerForActivityResult(
        StartActivityForResult()
    ) { result ->
        if (result.resultCode == RESULT_OK) {
            // There are no request codes
            val intent = result.data

            val loveList: List<ModelLoveLanguages> =
                intent?.getSerializableExtra("loveList") as List<ModelLoveLanguages>
            Timber.d("received list size id ${loveList.size}")

            binding.etlove1.setText(loveList[0].name)
            binding.etlove2.setText(loveList[1].name)
            binding.etlove3.setText(loveList[2].name)

        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddPersonBinding.inflate(layoutInflater)
        setContentView(binding.root)

        handleExtrasFromIntent()

        if (personModel != null) {
            setDataInViews()
        }


        binding.btnSavePerson.setOnClickListener {
            lifecycleScope.launch {
                if (personModel == null)
                    insertPerson()
                else updatePerson()
            }

        }
        binding.btnDiscard.setOnClickListener {
            finish()
        }

        binding.etlove1.setOnClickListener {
            goToChooseLanguage()
        }
        binding.etlove2.setOnClickListener {
            goToChooseLanguage()
        }
        binding.etlove3.setOnClickListener {
            goToChooseLanguage()
        }

        binding.etBirthday.setOnClickListener {
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
                binding.etBirthday.setText(
                    StringBuilder().append(dayOfMonth).append("/").append(monthOfYear + 1)
                        .append("/").append(year)

                )
            }, mYear, mMonth, mDay
        )
        dpd.show()
    }


    private fun goToChooseLanguage() {
        val i = Intent(this, ActivityLoveLanguages::class.java)
        someActivityResultLauncher.launch(i)


    }

    private fun updatePerson() {
        val person = PersonEntity().apply {
            personid = personModel!!.personid
            firstname = binding.etFirstName.text.toString()
            lastname = binding.etLastName.text.toString()
            relationShip = binding.etRelationship.text.toString()
            company = binding.etCompany.text.toString()
            interests =
                binding.etInterests1.text.toString()
                    .plus(",")
                    .plus(binding.etInterests2.text.toString())
                    .plus(",")
                    .plus(binding.etInterests3.text.toString())
            dates = binding.etBirthday.text.toString()
            loveLanguages =
                binding.etlove1.text.toString()
                    .plus(",")
                    .plus(binding.etlove2.text.toString())
                    .plus(",")
                    .plus(binding.etlove3.text.toString())
            personnotes = binding.etNotes.text.toString()
            newId = personModel!!.newId

        }
        Timber.d("---------------------------------------------------------------")
        Timber.d("update person is ${person.personid}")
        Timber.d("updated person is ${person.firstname}")
        Timber.d("updated person is ${person.lastname}")
        Timber.d("updated person is ${person.company}")
        Timber.d("updated person is ${person.interests}")
        Timber.d("updated person is ${person.dates}")
        Timber.d("updated person is ${person.loveLanguages}")
        Timber.d("updated person is ${person.personnotes}")

        val updatePerson = viewModelAddPerson.updatePerson(person)

        Timber.d("updated from activity $updatePerson")
        finish()

        Toast.makeText(this, "Person updated", Toast.LENGTH_LONG).show()
    }

    private fun setDataInViews() {
        binding.etFirstName.setText(personModel?.firstname)
        binding.etLastName.setText(personModel?.lastname)
        binding.etCompany.setText(personModel?.company)
        binding.etRelationship.setText(personModel?.relationShip)

        binding.etInterests1.setText(interests?.get(0))
        binding.etInterests2.setText(interests?.get(1))
        binding.etInterests3.setText(interests?.get(2))

        binding.etlove1.setText(love?.get(0))
        binding.etlove2.setText(love?.get(1))
        binding.etlove3.setText(love?.get(2))


        binding.etNotes.setText(personModel?.personnotes)


    }

    private fun handleExtrasFromIntent() {
        personModel = intent?.extras?.getSerializable("person") as PersonEntity?
        Timber.d("person id is ${personModel?.personid}")
        personModel?.interests?.split(",")?.let { interests?.addAll(it) }

        personModel?.loveLanguages?.split(",")?.let { love?.addAll(it) }

        Timber.d("Interest list is ${Gson().toJson(interests)}")
        Timber.d("Interest list is ${Gson().toJson(love)}")
    }

    private fun insertPerson() {
        val person = PersonEntity().apply {
            firstname = binding.etFirstName.text.toString()
            lastname = binding.etLastName.text.toString()
            relationShip = binding.etRelationship.text.toString()
            company = binding.etCompany.text.toString()
            interests =
                binding.etInterests1.text.toString()
                    .plus(",")
                    .plus(binding.etInterests2.text.toString())
                    .plus(",")
                    .plus(binding.etInterests3.text.toString())
            dates = binding.etBirthday.text.toString()
            loveLanguages =
                binding.etlove1.text.toString()
                    .plus(",")
                    .plus(binding.etlove2.text.toString())
                    .plus(",")
                    .plus(binding.etlove3.text.toString())

            personnotes = binding.etNotes.text.toString()

        }
        viewModelAddPerson.insertPerson(person)

        Timber.d("person is $person")
        Timber.d("person is ${person.firstname}")


        viewModelAddPerson.personId.observe(this, {
            Timber.d("person added $it")

            viewModelAddPerson.updatePersonID(it)

            Toast.makeText(this, "Person added", Toast.LENGTH_LONG).show()

            insertPost(personID = it)

            finish()

        })
    }

    private fun insertPost(personID: Long?) {
        lifecycleScope.launch {
            val post = PostEntity().apply {
                postName = "Birthday"
                dueDate = binding.etBirthday.text.toString()
                dueTime = "12:00 AM"
                frequency = "1"
                postnotes = binding.etFirstName.text.toString().plus(" 's Birthday")
                isCompleted = false
                personid = personID

            }

            Timber.d("post is $post")
            Timber.d("post name is ${post.postName}")

            val id = viewModelAddPerson.insertPost(post)
            Timber.d("new person added iD is $id")

        }


    }
}