package com.example.revolutlistener

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.navigation.findNavController
import androidx.navigation.ui.NavigationUI
import com.example.revolutlistener.databinding.ActivityMainBinding
import com.example.revolutlistener.notifications.NotificationService



private const val TAG = "mainActivity"
private const val CHANNEL_ID = "52"

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Start listening to notifications.
        Intent(this, NotificationService::class.java).also { intent ->
            startService(intent)
        }
        createNotificationChannel()

        val navController = findNavController(R.id.nav_host_fragment)


        // Switch from burger to back arrow
        binding.toolbar.setNavigationOnClickListener {
            NavigationUI.navigateUp(navController, binding.drawerLayout)
            binding.toolbar.setNavigationIcon(R.drawable.burger)
            binding.toolbar.menu.findItem(R.id.settings).isVisible = true
        }
        // Navigation on topBar
        binding.toolbar.setOnMenuItemClickListener { menuItem ->
            when (menuItem.itemId) {
                R.id.settings -> {
                    navController.navigate(R.id.settings_fragment)
                    menuItem.isVisible = false
                    true
                }
                else -> false
            }
        }



        NavigationUI.setupWithNavController(binding.navView, navController)
//        binding.bottomNav.setupWithNavController(navController)
//
        // Hide settings icon when in settings/about, change burger to back icon
        navController.addOnDestinationChangedListener { _, destination, _ ->
            when (destination.id) {
                R.id.about_fragment, R.id.settings_fragment, R.id.privacy_policy_fragment -> {
//                    binding.bottomNav.isVisible = false
                    binding.toolbar.setNavigationIcon(R.drawable.back)
                    binding.toolbar.menu.findItem(R.id.settings).isVisible = false
                }
                else -> false
//                else -> binding.bottomNav.isVisible = true
            }
        }
    }


    // Open drawer when clicking burger
    override fun onSupportNavigateUp(): Boolean {
        val navController = this.findNavController(R.id.nav_host_fragment)
        return NavigationUI.navigateUp(navController, binding.drawerLayout)
    }

    private fun createNotificationChannel() {
        // Create the NotificationChannel, but only on API 26+ because
        // the NotificationChannel class is new and not in the support library
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val name = getString(R.string.channel_name)
            val descriptionText = getString(R.string.channel_description)
            val importance = NotificationManager.IMPORTANCE_DEFAULT
            val channel = NotificationChannel(CHANNEL_ID, name, importance).apply {
                description = descriptionText
            }
            // Register the channel with the system
            val notificationManager: NotificationManager =
                getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel)
        }
    }
}
