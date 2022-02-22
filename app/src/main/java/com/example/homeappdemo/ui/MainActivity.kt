package com.example.homeappdemo.ui

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.navigation.ui.setupWithNavController
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import com.example.homeappdemo.R
import com.example.homeappdemo.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint
import java.util.*

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private var _binding: ActivityMainBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar?.hide()

        initializeBindingView()
        initializeNavGraph()
        initBottomNav()
    }

    private fun initializeBindingView() {
        _binding = ActivityMainBinding.inflate(layoutInflater)
        val view: View = binding.root
        setContentView(view)
    }

    private fun initializeNavGraph() {
        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.navFragment) as NavHostFragment
        val navController = navHostFragment.navController
        val appBarConfiguration = AppBarConfiguration(
            topLevelDestinationIds = setOf(
                R.id.deviceFragment,
                R.id.userProfileFragment,
                R.id.settingsFragment
            ),
            fallbackOnNavigateUpListener = ::onSupportNavigateUp
        )

        setupActionBarWithNavController(navController, appBarConfiguration)
    }

    private fun initBottomNav() {
        val navController = findNavController(R.id.navFragment)
        binding.bottomNavigationView.setupWithNavController(navController)

        navController.addOnDestinationChangedListener { _, _, arguments ->
            val toShowNav = arguments?.getBoolean("showNav", false) == true
            val toShowBar = arguments?.getBoolean("showBar", false) == true
            if (toShowNav) {
                supportActionBar?.hide()
                binding.bottomNavigationView.visibility = View.VISIBLE
            } else {
                if (toShowBar) supportActionBar?.show() else supportActionBar?.hide()
                supportActionBar?.setDisplayHomeAsUpEnabled(true)
                supportActionBar?.setHomeButtonEnabled(true)
                binding.bottomNavigationView.visibility = View.GONE
            }
        }

    }
    fun changeLanguage(locale: Language, context: Context) {
        when (locale) {
            Language.EN -> setAppLocale(context, "en")
            Language.FR -> setAppLocale(context, "fr")
        }

        this.recreate()
    }

    private fun setAppLocale(context: Context, language: String) {
        val locale = Locale(language)
        Locale.setDefault(locale)
        val config = context.resources.configuration
        config.setLocale(locale)
        context.createConfigurationContext(config)
        context.resources.updateConfiguration(config, context.resources.displayMetrics)
    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.navFragment)
        return navController.navigateUp() || super.onSupportNavigateUp()
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    enum class Language {
        EN, FR
    }

}