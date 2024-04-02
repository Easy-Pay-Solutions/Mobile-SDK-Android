package com.fm.easypay.repositories.annual_consent

import com.fm.easypay.repositories.annual_consent.cancel.CancelAnnualConsentRepository
import com.fm.easypay.repositories.annual_consent.cancel.CancelAnnualConsentRepositoryImpl
import com.fm.easypay.repositories.annual_consent.create.CreateAnnualConsentRepository
import com.fm.easypay.repositories.annual_consent.create.CreateAnnualConsentRepositoryImpl
import com.fm.easypay.repositories.annual_consent.list.ListAnnualConsentsRepository
import com.fm.easypay.repositories.annual_consent.list.ListAnnualConsentsRepositoryImpl
import com.fm.easypay.repositories.annual_consent.process_payment.ProcessPaymentAnnualRepository
import com.fm.easypay.repositories.annual_consent.process_payment.ProcessPaymentAnnualRepositoryImpl
import org.koin.dsl.module

internal object ConsentAnnualModule {

    val consentAnnualModules = module {
        single<ListAnnualConsentsRepository> { ListAnnualConsentsRepositoryImpl(get()) }
        single<CreateAnnualConsentRepository> { CreateAnnualConsentRepositoryImpl(get()) }
        single<CancelAnnualConsentRepository> { CancelAnnualConsentRepositoryImpl(get()) }
        single<ProcessPaymentAnnualRepository> { ProcessPaymentAnnualRepositoryImpl(get()) }
    }
}
