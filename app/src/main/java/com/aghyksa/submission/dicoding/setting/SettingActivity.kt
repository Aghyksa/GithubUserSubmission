package com.aghyksa.submission.dicoding.setting

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.CompoundButton
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatDelegate
import com.aghyksa.submission.dicoding.R
import com.aghyksa.submission.dicoding.databinding.ActivitySettingBinding
import com.aghyksa.submission.dicoding.datastore.SettingPreferences
import com.aghyksa.submission.dicoding.datastore.dataStore
import com.aghyksa.submission.dicoding.main.MainViewModel
import com.aghyksa.submission.dicoding.utils.GenericViewModelFactory

class SettingActivity : AppCompatActivity() {
    private lateinit var bind : ActivitySettingBinding
    private lateinit var pref :SettingPreferences
    private val viewModel: SettingViewModel by viewModels{
        GenericViewModelFactory.create(
            SettingViewModel(pref)
        )
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bind= ActivitySettingBinding.inflate(layoutInflater)
        setContentView(bind.root)
        pref = SettingPreferences.getInstance(application.dataStore)
        setupViewModel()
        setupListener()
    }

    private fun setupListener() {
        bind.switchTheme.setOnCheckedChangeListener { _: CompoundButton?, isChecked: Boolean ->
            viewModel.saveThemeSetting(isChecked)
        }
    }

    private fun setupViewModel() {
        viewModel.getThemeSettings().observe(this@SettingActivity) { isDarkModeActive: Boolean ->
            if (isDarkModeActive) {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
                bind.switchTheme.isChecked = true
            } else {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
                bind.switchTheme.isChecked = false
            }
        }
    }
}