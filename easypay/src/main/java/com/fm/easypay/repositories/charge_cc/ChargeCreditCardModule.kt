package com.fm.easypay.repositories.charge_cc

import org.koin.dsl.module

internal object ChargeCreditCardModule {

    val chargeCreditCardModules = module {
        single<ChargeCreditCardRepository> { ChargeCreditCardRepositoryImpl(get()) }
    }
}
