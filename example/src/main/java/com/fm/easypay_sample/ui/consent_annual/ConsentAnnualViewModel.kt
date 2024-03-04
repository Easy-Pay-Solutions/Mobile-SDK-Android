package com.fm.easypay_sample.ui.consent_annual

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.fm.easypay.api.requests.base.EasyPayQuery
import com.fm.easypay.api.responses.ConsentAnnualQueryResult
import com.fm.easypay.networking.NetworkResource
import com.fm.easypay.repositories.consent_annual.ConsentAnnual
import com.fm.easypay_sample.utils.ViewState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

typealias ConsentAnnualState = ViewState<ConsentAnnualQueryResult>

@HiltViewModel
class ConsentAnnualViewModel @Inject constructor(
    private val consentAnnual: ConsentAnnual,
) : ViewModel() {

    private val _consentAnnuals = MutableStateFlow<ConsentAnnualState>(ViewState.Loading())
    val consentAnnuals: StateFlow<ConsentAnnualState> = _consentAnnuals

    fun getConsentAnnuals() {
        viewModelScope.launch {
            val query = EasyPayQuery("(A=-1)&&(G=1)&&(H='True')")
            val response = consentAnnual.getConsentAnnuals(query)
            when (response.status) {
                NetworkResource.Status.SUCCESS -> {
                    response.data?.let {
                        _consentAnnuals.emit(ViewState.Success(data = it))
                    } ?: _consentAnnuals.emit(ViewState.Error())
                }

                NetworkResource.Status.ERROR -> {
                    _consentAnnuals.emit(ViewState.Error(message = response.error?.message))
                }

                else -> {}
            }
        }
    }
}