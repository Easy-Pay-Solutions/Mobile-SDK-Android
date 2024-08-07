package com.easypaysolutions.payment_sheet.utils

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

sealed interface PaymentSheetResult: Parcelable {

    @Parcelize
    data class Completed(val data: PaymentSheetCompletedData) : PaymentSheetResult

    @Parcelize
    data object Canceled : PaymentSheetResult

    @Parcelize
    data class Failed(val error: Throwable) : PaymentSheetResult
}
