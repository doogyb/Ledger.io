package com.example.revolutlistener.screens.settings

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.text.InputType
import android.text.TextUtils
import androidx.lifecycle.ViewModelProvider
import androidx.preference.EditTextPreference
import androidx.preference.Preference
import androidx.preference.PreferenceFragmentCompat
import com.example.revolutlistener.R

const val TAG = "SettingsFragment"

class SettingsFragment : PreferenceFragmentCompat() {


    private lateinit var viewModel: SettingsViewModel

    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {

        setPreferencesFromResource(R.xml.preferences, rootKey)

        val application = requireNotNull(this.activity).application
        val viewModelFactory = SettingsViewModelFactory(application)
        viewModel = ViewModelProvider(this, viewModelFactory).get(SettingsViewModel::class.java)

        findPreference<EditTextPreference>("budget_preference")?.apply {

            summaryProvider = Preference.SummaryProvider<EditTextPreference> {
                val text = it.text
                if (TextUtils.isEmpty(text)) "Set your budget amount here" else "€$text"
            }

            setOnBindEditTextListener {
                it.inputType = InputType.TYPE_CLASS_NUMBER
            }
            setOnPreferenceChangeListener { preference, newValue ->
                viewModel.onSaveBudget(Integer.parseInt(newValue as String))
                true
            }
        }

        findPreference<EditTextPreference>("interval_preference")?.apply {

            summaryProvider = Preference.SummaryProvider<EditTextPreference> {
                val text = it.text
                if (TextUtils.isEmpty(text)) "How many days your Budget lasts" else "$text Days"
            }

            setOnBindEditTextListener {
                it.inputType = InputType.TYPE_CLASS_NUMBER
            }
            setOnPreferenceChangeListener { preference, newValue ->
                viewModel.onSaveInterval(Integer.parseInt(newValue as String))
                true
            }
        }

        findPreference<Preference>("feedback")?.apply {
            setOnPreferenceClickListener {
                val mailto = "mailto:Ledger.io@gmail.com?subject=" + Uri.encode("Ledger.io feedback")
                val email = Intent(Intent.ACTION_SENDTO)
                email.data = Uri.parse(mailto)
                startActivity(email)
                true
            }
        }
    }
}