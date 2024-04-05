package com.fm.easypay_sample.ui.consent_annual.add

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.fm.easypay.api.responses.annual_consent.CreateAnnualConsentResult
import com.fm.easypay.networking.NetworkResource
import com.fm.easypay.repositories.annual_consent.create.CreateAnnualConsent
import com.fm.easypay.utils.secured.SecureData
import com.fm.easypay_sample.utils.ViewState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

typealias CreateAnnualConsentState = ViewState<CreateAnnualConsentResult>

@HiltViewModel
class AddAnnualConsentViewModel @Inject constructor(
    private val createAnnualConsent: CreateAnnualConsent,
) : ViewModel() {

    var viewData: AddAnnualConsentViewData = AddAnnualConsentHelper.getPrefilledViewData()
        private set

    private val _createAnnualConsentResult = MutableSharedFlow<CreateAnnualConsentState>()
    val createAnnualConsentResult: SharedFlow<CreateAnnualConsentState> = _createAnnualConsentResult

    //region Public methods

    fun createAnnualConsent(secureData: SecureData<String>) {
        viewModelScope.launch {
            _createAnnualConsentResult.emit(ViewState.Loading())
            val params = viewData.toCreateAnnualConsentParams(secureData)
            val response = createAnnualConsent.createAnnualConsent(params)
            when (response.status) {
                NetworkResource.Status.SUCCESS -> {
                    response.data?.let {
                        _createAnnualConsentResult.emit(ViewState.Success(data = it))
                    } ?: _createAnnualConsentResult.emit(ViewState.Error())
                }

                NetworkResource.Status.ERROR -> emitError("ERROR: " + response.error?.message)
                NetworkResource.Status.DECLINED -> emitError("DECLINED: " + response.error?.message)
            }
        }
    }

    //endregion

    //region Private methods

    private suspend fun emitError(message: String?) {
        _createAnnualConsentResult.emit(ViewState.Error(message))
    }

    //endregion

}