package com.easypaysolutions.common.presentation

import com.easypaysolutions.api.responses.annual_consent.AnnualConsent
import com.easypaysolutions.api.responses.annual_consent.CancelAnnualConsentResult
import com.easypaysolutions.api.responses.annual_consent.CreateAnnualConsentResult
import com.easypaysolutions.api.responses.annual_consent.ProcessPaymentAnnualResult
import com.easypaysolutions.api.responses.charge_cc.ChargeCreditCardResult

internal sealed class PayWithSavedCardUiState {
    data class Success(val result: ProcessPaymentAnnualResult) : PayWithSavedCardUiState()
    data class Error(val exception: Throwable) : PayWithSavedCardUiState()
    data class Declined(val result: ProcessPaymentAnnualResult) : PayWithSavedCardUiState()
    data object Loading : PayWithSavedCardUiState()
    data object Idle : PayWithSavedCardUiState()
}

internal sealed class PayWithNewCardUiState {
    data class Success(val result: ChargeCreditCardResult) : PayWithNewCardUiState()
    data class Error(val exception: Throwable) : PayWithNewCardUiState()
    data class Declined(val result: ChargeCreditCardResult) : PayWithNewCardUiState()
    data object Loading : PayWithNewCardUiState()
    data object Idle : PayWithNewCardUiState()
}

internal sealed class AddNewCardUiState {
    data class Success(val result: CreateAnnualConsentResult) : AddNewCardUiState()
    data class Error(val exception: Throwable) : AddNewCardUiState()
    data object Loading : AddNewCardUiState()
}

internal sealed class PaymentMethodsUiState {
    data class Success(val methods: List<AnnualConsent>) : PaymentMethodsUiState()
    data class Error(val exception: Throwable) : PaymentMethodsUiState()
    data object Loading : PaymentMethodsUiState()
}

internal sealed class DeleteCardUiState {
    data class Success(val result: CancelAnnualConsentResult) : DeleteCardUiState()
    data class Error(val exception: Throwable) : DeleteCardUiState()
    data object Loading : DeleteCardUiState()
}

internal sealed class OpenNewCardSheetUiState {
    data object Idle : OpenNewCardSheetUiState()
    data object Open : OpenNewCardSheetUiState()
    data object Close : OpenNewCardSheetUiState()
}