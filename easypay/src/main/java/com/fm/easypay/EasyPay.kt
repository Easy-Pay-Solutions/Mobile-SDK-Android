package com.fm.easypay

import android.content.Context
import com.fm.easypay.api.ApiModule
import com.fm.easypay.networking.NetworkingModule
import com.fm.easypay.repositories.charge_cc.ChargeCreditCardModule
import com.fm.easypay.repositories.consent_annual.ConsentAnnualModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

/**
 * Entry-point to the EasyPay SDK.
 */
class EasyPay private constructor() {

    companion object {
        fun init(
            context: Context,
            sessionKey: String,
            hmacSecret: String,
        ) {
            setup(context)
            EasyPayConfiguration.init(sessionKey, hmacSecret)
            // TODO: Validator blocks using Emulator. Need to find a way to bypass it.
//            RootedDeviceValidator.verifyDevice(context)
        }

        private fun setup(context: Context) {
            initModules(context)
        }

        private fun initModules(context: Context) {
            startKoin {
                androidContext(context)
                modules(
                    NetworkingModule.networkingModules,
                    ApiModule.apiModules,
                    ConsentAnnualModule.consentAnnualModules,
                    ChargeCreditCardModule.chargeCreditCardModules
                )
            }
        }
    }
}