package com.example.flashcardapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.Menu
import android.view.MenuItem
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import com.example.flashcardapp.databinding.ActivityMainBinding
import com.example.flashcardapp.ui.viewmodel.QuestionsViewModel
import com.example.flashcardapp.ui.viewmodel.QuestionsViewModelFactory


class MainActivity : AppCompatActivity() {

    private val viewModel: QuestionsViewModel by viewModels {
        QuestionsViewModelFactory(
            (application as FlashCardsApplication).repository,
            application as FlashCardsApplication
        )
    }

    private lateinit var binding: ActivityMainBinding

    private lateinit var navHostFragment: NavHostFragment
    private lateinit var navController: NavController
    private lateinit var appBarConfiguration: AppBarConfiguration

    private var keepOnScreen = true
    private val delay = 1500L

    override fun onCreate(savedInstanceState: Bundle?) {

        installSplashScreen().setKeepOnScreenCondition { keepOnScreen }
        Handler(Looper.getMainLooper()).postDelayed({ keepOnScreen = false }, delay)

        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel.themeMode.observe(this) { themeMode ->
            if (themeMode) {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
            } else {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
            }
        }

        setSupportActionBar(binding.toolbarView)

        navHostFragment =
            supportFragmentManager.findFragmentById(binding.fragmentContainerView.id)
                    as NavHostFragment
        navController = navHostFragment.navController

        appBarConfiguration = AppBarConfiguration(navController.graph)

        setupActionBarWithNavController(navController, appBarConfiguration)

    }

    override fun onSupportNavigateUp(): Boolean {
        return navController.navigateUp() || super.onSupportNavigateUp()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.toolbar_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.dark_theme_btn -> {
                changeAndSaveMode(true)
                true
            }

            R.id.light_theme_btn -> {
                changeAndSaveMode(false)
                true
            }

            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun changeAndSaveMode(value: Boolean) {
        viewModel.saveThemeToDataStore(value)
    }
}


