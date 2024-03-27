package com.fm.easypay_sample.ui.consent_annual

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.fm.easypay.api.responses.annual_consent.ListAnnualConsentsResult
import com.fm.easypay.networking.NetworkResource
import com.fm.easypay.repositories.annual_consent.list.ListAnnualConsents
import com.fm.easypay.repositories.annual_consent.list.ListAnnualConsentsBodyParams
import com.fm.easypay_sample.utils.ViewState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

typealias ConsentAnnualState = ViewState<ListAnnualConsentsResult>

@HiltViewModel
class ConsentAnnualViewModel @Inject constructor(
    private val listAnnualConsents: ListAnnualConsents,
) : ViewModel() {

    private val _consentAnnuals = MutableStateFlow<ConsentAnnualState>(ViewState.Loading())
    val consentAnnuals: StateFlow<ConsentAnnualState> = _consentAnnuals

    //region Public

    fun getConsentAnnuals() {
        viewModelScope.launch {
            val params = prepareParams()
            val response = listAnnualConsents.listAnnualConsents(params)
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

    //endregion

    //region Private

    private fun prepareParams(): ListAnnualConsentsBodyParams = ListAnnualConsentsBodyParams()

    //endregion

}