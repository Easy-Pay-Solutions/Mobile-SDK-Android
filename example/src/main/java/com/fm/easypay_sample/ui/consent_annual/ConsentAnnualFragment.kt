package com.fm.easypay_sample.ui.consent_annual

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.fm.easypay_sample.R
import com.fm.easypay_sample.databinding.FragmentConsentAnnualBinding
import com.fm.easypay_sample.utils.AlertUtils
import com.fm.easypay_sample.utils.ViewState
import com.fm.easypay_sample.utils.viewBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class ConsentAnnualFragment : Fragment() {

    private val binding by viewBinding(FragmentConsentAnnualBinding::bind)
    private val viewModel: ConsentAnnualViewModel by viewModels { defaultViewModelProviderFactory }
    private val consentsAdapter = ConsentAnnualAdapter(
        onDeleteClickedCallback = { viewModel.deleteConsent(it) },
        onChargeClickedCallback = { viewModel.chargeConsent(it) }
    )

    //region Lifecycle methods

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initFlows()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        return inflater.inflate(R.layout.fragment_consent_annual, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initComponents()
        viewModel.fetchAnnualConsents()
    }

    //endregion

    //region Setup methods

    private fun initFlows() {
        lifecycleScope.launchWhenResumed {
            launch {
                viewModel.annualConsents.collect {
                    when (it) {
                        is ViewState.Loading -> binding.progressView.show()
                        is ViewState.Success -> {
                            it.value?.let { result ->
                                consentsAdapter.submitList(result.consents)
                            }
                            binding.progressView.hide()
                        }

                        is ViewState.Error -> {
                            binding.progressView.hide()
                            AlertUtils.showAlert(requireContext(), it.message)
                        }
                    }
                }
            }
            launch {
                viewModel.processPaymentAnnualResult.collect {
                    when (it) {
                        is ViewState.Loading -> binding.progressView.show()
                        is ViewState.Success -> {
                            binding.progressView.hide()
                            AlertUtils.showAlert(requireContext(), "Payment successful")
                        }

                        is ViewState.Error -> {
                            binding.progressView.hide()
                            AlertUtils.showAlert(requireContext(), it.message)
                        }
                    }
                }

            }
            launch {
                viewModel.deleteAnnualConsentResult.collect {
                    when (it) {
                        is ViewState.Loading -> binding.progressView.show()
                        is ViewState.Success -> {
                            binding.progressView.hide()
                            AlertUtils.showAlert(requireContext(), "Consent deleted")
                        }

                        is ViewState.Error -> {
                            binding.progressView.hide()
                            AlertUtils.showAlert(requireContext(), it.message)
                        }
                    }
                }
            }
        }
    }

    private fun initComponents() {
        binding.apply {
            btnAddCard.setOnClickListener { navigateToAddCard() }
            rvAnnualConsents.adapter = consentsAdapter
        }
    }

    //endregion

    //region Navigation

    private fun navigateToAddCard() {
        val action =
            ConsentAnnualFragmentDirections.actionConsentAnnualFragmentToAddAnnualConsentFragment()
        findNavController().navigate(action)
    }

    //endregion

}