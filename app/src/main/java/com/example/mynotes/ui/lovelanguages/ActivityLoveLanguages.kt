package com.example.mynotes.ui.lovelanguages

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.mynotes.R
import com.example.mynotes.databinding.ActivityLoveLanguagesBinding
import com.example.mynotes.ui.lovelanguages.adapters.AdapterLoveLanguages
import com.example.mynotes.ui.lovelanguages.model.ModelLoveLanguages
import timber.log.Timber
import java.io.Serializable


class ActivityLoveLanguages : AppCompatActivity() {
    private var loveLanguageList: MutableList<ModelLoveLanguages> = mutableListOf()
    private var newList: MutableList<ModelLoveLanguages> = mutableListOf()
    private lateinit var adapterLoveLanguages: AdapterLoveLanguages
    private lateinit var binding: ActivityLoveLanguagesBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoveLanguagesBinding.inflate(layoutInflater)
        setContentView(binding.root)

        insertData()

        setAdapter()

        binding.btnDone.setOnClickListener {
            for (i in 0..loveLanguageList.lastIndex) {
                if (loveLanguageList[i].check) {
                    newList.add(loveLanguageList[i])
                }
            }
            val resultIntent = Intent()
            resultIntent.putExtra("loveList", newList as Serializable?)
            Timber.d("new list size is ${newList.size}")
            setResult(RESULT_OK, resultIntent)
            finish()
        }
    }


    private fun insertData() {
        loveLanguageList.add(ModelLoveLanguages(getString(R.string.words_affirmation), false))
        loveLanguageList.add(ModelLoveLanguages(getString(R.string.acts_of_service), false))
        loveLanguageList.add(ModelLoveLanguages(getString(R.string.receiving_gifts), false))
        loveLanguageList.add(ModelLoveLanguages(getString(R.string.quality_time), false))
        loveLanguageList.add(ModelLoveLanguages(getString(R.string.physical_touch), false))
    }

    private fun setAdapter() {
        adapterLoveLanguages = AdapterLoveLanguages(
            dataList = loveLanguageList,
            context = this,
        )
        binding.rvLoveLanguages.adapter = adapterLoveLanguages
    }
}