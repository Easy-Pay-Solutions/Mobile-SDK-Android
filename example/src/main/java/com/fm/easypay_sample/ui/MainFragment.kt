package com.fm.easypay_sample.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.fm.easypay_sample.R
import com.fm.easypay_sample.databinding.FragmentMainBinding
import com.fm.easypay_sample.utils.viewBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainFragment : Fragment() {

    private val binding by viewBinding(FragmentMainBinding::bind)

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        return inflater.inflate(R.layout.fragment_main, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initComponents()
    }

    private fun initComponents() {
        binding.apply {
            btnChargeCard.setOnClickListener {
                navigateToChargeCard()
            }
            btnConsentAnnual.setOnClickListener {
                navigateToConsentAnnual()
            }
        }
    }

    private fun navigateToChargeCard() {
        val action = MainFragmentDirections.actionMainFragmentToChargeCardFragment()
        findNavController().navigate(action)
    }

    private fun navigateToConsentAnnual() {
        val action = MainFragmentDirections.actionMainFragmentToConsentAnnualFragment()
        findNavController().navigate(action)
    }
}