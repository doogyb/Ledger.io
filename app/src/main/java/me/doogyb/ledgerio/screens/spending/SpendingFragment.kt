package me.doogyb.ledgerio.screens.spending

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import me.doogyb.ledgerio.databinding.SpendingFragmentBinding

class SpendingFragment : Fragment() {

    private lateinit var viewModel: SpendingViewModel
    private lateinit var binding: SpendingFragmentBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = SpendingFragmentBinding.inflate(inflater)
        return binding.root
    }

}