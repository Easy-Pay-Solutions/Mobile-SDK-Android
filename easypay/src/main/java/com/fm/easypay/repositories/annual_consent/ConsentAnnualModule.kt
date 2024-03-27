package com.fm.easypay.repositories.annual_consent

import com.fm.easypay.repositories.annual_consent.create.CreateAnnualConsentRepository
import com.fm.easypay.repositories.annual_consent.create.CreateAnnualConsentRepositoryImpl
import com.fm.easypay.repositories.annual_consent.list.ListAnnualConsentsRepository
import com.fm.easypay.repositories.annual_consent.list.ListAnnualConsentsRepositoryImpl
import org.koin.dsl.module

internal object ConsentAnnualModule {

    val consentAnnualModules = module {
        single<ListAnnualConsentsRepository> { ListAnnualConsentsRepositoryImpl(get()) }
        single<CreateAnnualConsentRepository> { CreateAnnualConsentRepositoryImpl(get()) }
    }
}
