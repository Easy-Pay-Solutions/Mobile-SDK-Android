package com.easypaysolutions.customer_sheet.utils

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

sealed interface CustomerSheetResult : Parcelable {

    @Parcelize
    data class Selected(val annualConsentId: Int?) : CustomerSheetResult

    @Parcelize
    data class Failed(
        val error: Throwable,
    ) : CustomerSheetResult
}
