package com.fm.easypay.exceptions

/**
 * [EasyPaySdkException] - a class for exceptions issued in the SDK.
 */
class EasyPaySdkException(
    val type: Type,
    cause: Throwable? = null,
) : EasyPayException(type.message, cause) {

    enum class Type(val message: String) {
        EASY_PAY_CONFIGURATION_NOT_INITIALIZED("EasyPayConfiguration not initialized."),
        MISSED_SESSION_KEY("Missed Session Key."),
        MISSED_HMAC_SECRET("Missed HMAC secret."),
        ROOTED_DEVICE_DETECTED("Rooted device detected. EasyPay SDK does not support rooted devices."),
        RSA_INPUT_DATA_EMPTY("RSA certificate's Public Key is empty."),
        RSA_CERTIFICATE_NOT_FETCHED("RSA certificate not fetched."),
    }
}