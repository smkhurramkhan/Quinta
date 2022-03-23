package com.example.mynotes.ui.suggestions

import android.content.ActivityNotFoundException
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.text.SpannableStringBuilder
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.text.bold
import com.example.mynotes.databinding.ActivitySuggestionsBinding
import com.example.mynotes.room.entity.PersonEntity
import com.example.mynotes.ui.addperson.ActivityAddPerson
import timber.log.Timber

class ActivitySuggestions : AppCompatActivity() {
    private lateinit var binding: ActivitySuggestionsBinding
    private lateinit var personModel: PersonEntity
    private var loveLanguages: MutableList<String> = mutableListOf()
    private var personID: Long? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySuggestionsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        handleExtrasFromIntent()

        intiViews()

        clickListeners()
    }

    private fun clickListeners() {
        binding.phonecall.setOnClickListener {
            goToNext("Phone Call")
        }

        binding.tvVisitthem.setOnClickListener {
            goToNext("Visit them")
        }

        binding.dinner.setOnClickListener {
            goToNext("Go for dinner")
        }

        binding.baketogether.setOnClickListener {
            goToNext("Bake Together")
        }

        binding.thankthem.setOnClickListener {
            goToNext("Thank them")
        }

        binding.complimentthem.setOnClickListener {
            goToNext("Give Compliment")
        }

        binding.sendcard.setOnClickListener {
            goToNext("Send a card")
        }
        binding.sendsong.setOnClickListener {
            goToNext("Send a song")
        }
        binding.sendflowers.setOnClickListener {
            goToNext("Send flowers")
        }
        binding.buythem.setOnClickListener {
            goToNext("Buy them something")
        }
        binding.giftvoucher.setOnClickListener {
            goToNext("Send them gift voucher")
        }
        binding.priortize.setOnClickListener {
            goToNext("Buy them something")
        }
        binding.physicalaffection.setOnClickListener {
            goToNext("Buy them something")
        }
        binding.specialoccsasion.setOnClickListener {
            goToNext("Special Occasion")
        }
        binding.help.setOnClickListener {
            goToNext("Ask them for help")
        }
        binding.specialeffor.setOnClickListener {
            goToNext("Make special effort for them")
        }

    }

    private fun goToNext(type: String) {
        val intent = Intent(this, ActivityAddSuggestion::class.java)
        intent.putExtra("suggestion", type)
        intent.putExtra("personid", personID)
        startActivity(intent)
    }

    private fun intiViews() {
        binding.apply {
            personName.text = personModel.firstname
                .plus(" ")
                .plus(personModel.lastname)

            etRelationshipCompany.text = personModel.relationShip
                .plus(" ")
                .plus(personModel.company)


            personModel.loveLanguages?.split(",")?.let { loveLanguages.addAll(it) }

            val s = SpannableStringBuilder()
                .append("You have noted ")
                .bold { append(personModel.firstname) }
                .append("'s top languages as ")
                .bold { append(loveLanguages[0]) }
                .append(", ")
                .bold { append(loveLanguages[1]) }
                .append(" and ")
                .bold { append(loveLanguages[2]) }
                .append(" more suggestions in these categories")
            notes.text = s



            btnPersonEdit.setOnClickListener {
                val intent = Intent(this@ActivitySuggestions, ActivityAddPerson::class.java)
                intent.putExtra("person", personModel)
                startActivity(intent)
            }

            btnFiveLanguages.setOnClickListener {
                searchLanguages()
            }
        }
    }

    private fun handleExtrasFromIntent() {
        personModel = intent.extras?.getSerializable("person") as PersonEntity

        personID = personModel.personid
        Timber.d("handleExtrasFromIntent person id is ${personModel.personid}")
        Timber.d("handleExtrasFromIntent person id is ${personModel.firstname}")

    }

    private fun searchLanguages() {
        try {
            val url = "https://www.google.com/search?q=the 5 love languages"
            val i = Intent(Intent.ACTION_VIEW)
            i.data = Uri.parse(url)
            startActivity(i)
        } catch (exception: ActivityNotFoundException) {
            Toast.makeText(this, "Browser not found", Toast.LENGTH_LONG).show()
        }

    }
}