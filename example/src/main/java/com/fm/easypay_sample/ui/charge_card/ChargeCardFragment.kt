package com.fm.easypay_sample.ui.charge_card

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.fm.easypay_sample.R
import com.fm.easypay_sample.databinding.FragmentChargeCardBinding
import com.fm.easypay_sample.utils.viewBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ChargeCardFragment : Fragment() {

    private val binding by viewBinding(FragmentChargeCardBinding::bind)
    private val viewModel: ChargeCardViewModel by viewModels { defaultViewModelProviderFactory }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        return inflater.inflate(R.layout.fragment_charge_card, container, false)
    }
}