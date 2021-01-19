package com.example.revolutlistener.screens.budget

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import com.example.revolutlistener.database.AppDatabase
import com.example.revolutlistener.databinding.BudgetFragmentBinding
import com.example.revolutlistener.notifications.isNotificationServiceEnabled

class BudgetFragment : Fragment() {

    private lateinit var viewModel: BudgetViewModel
    private lateinit var binding: BudgetFragmentBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate view and obtain an instance of the binding class

        binding = BudgetFragmentBinding.inflate(inflater)

        val application = requireNotNull(this.activity).application
        val viewModelFactory = BudgetViewModelFactory(application)
        viewModel = ViewModelProvider(this, viewModelFactory).get(BudgetViewModel::class.java)

        binding.viewmodel = viewModel
        binding.lifecycleOwner = this

        binding.goToListenerSettings.setOnClickListener {
            Intent("android.settings.ACTION_NOTIFICATION_LISTENER_SETTINGS").also {
                it.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                startActivity(it)
            }
        }

        return binding.root
    }

    override fun onResume() {
        super.onResume()
        binding.listenerNotSetView.visibility =
            if (context?.let { isNotificationServiceEnabled(it) } == true) View.GONE
            else View.VISIBLE
    }
}