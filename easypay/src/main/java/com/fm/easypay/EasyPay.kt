@file:Suppress("TYPEALIAS_EXPANSION_DEPRECATION")

package com.fm.easypay

import android.content.Context
import com.fm.easypay.api.ApiModule
import com.fm.easypay.networking.NetworkingModule
import com.fm.easypay.repositories.annual_consent.ConsentAnnualModule
import com.fm.easypay.repositories.charge_cc.ChargeCreditCardModule
import io.sentry.Sentry
import io.sentry.android.core.SentryAndroid
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin
import org.koin.core.error.ApplicationAlreadyStartedException

/**
 * Entry-point to the EasyPay SDK.
 */
class EasyPay private constructor() {

    companion object {

        //region Public

        fun init(
            context: Context,
            sessionKey: String,
            hmacSecret: String,
            sentryKey: String? = null,
        ) {
            initModules(context)
            initSentry(context, sentryKey)
            EasyPayConfiguration.init(sessionKey, hmacSecret)
            // TODO: Validator blocks using Emulator. Need to find a way to bypass it.
//            RootedDeviceValidator.verifyDevice(context)
        }

        //endregion

        //region Private

        private fun initSentry(context: Context, sentryKey: String?) {
            if (sentryKey.isNullOrBlank()) return

            SentryAndroid.init(context) { options ->
                options.apply {
                    dsn = sentryKey
                    environment = "production"
                }
            }
        }

        private fun initModules(context: Context) {
            try {
                startKoin {
                    androidContext(context)
                    modules(
                        NetworkingModule.networkingModules,
                        ApiModule.apiModules,
                        ConsentAnnualModule.consentAnnualModules,
                        ChargeCreditCardModule.chargeCreditCardModules
                    )
                }
            } catch (e: ApplicationAlreadyStartedException) {
                Sentry.captureException(e)
                e.printStackTrace()
            }
        }

        //endregion
    }
}