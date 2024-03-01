package com.fm.easypay.repositories

import org.koin.dsl.module

internal object ConsentAnnualModule {

    val consentAnnualModules = module {
        single<ConsentAnnualRepository> { ConsentAnnualRepositoryImpl(get()) }
    }
}
