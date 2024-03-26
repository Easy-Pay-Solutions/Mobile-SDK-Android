package com.fm.easypay.repositories.consent_annual

import com.fm.easypay.repositories.consent_annual.create.CreateAnnualConsentRepository
import com.fm.easypay.repositories.consent_annual.create.CreateAnnualConsentRepositoryImpl
import org.koin.dsl.module

internal object ConsentAnnualModule {

    val consentAnnualModules = module {
        single<ConsentAnnualRepository> { ConsentAnnualRepositoryImpl(get()) }
        single<CreateAnnualConsentRepository> { CreateAnnualConsentRepositoryImpl(get()) }
    }
}
