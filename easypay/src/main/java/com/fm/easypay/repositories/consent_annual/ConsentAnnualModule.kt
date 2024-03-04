package com.fm.easypay.repositories.consent_annual

import org.koin.dsl.module

internal object ConsentAnnualModule {

    val consentAnnualModules = module {
        single<ConsentAnnualRepository> { ConsentAnnualRepositoryImpl(get()) }
    }
}
