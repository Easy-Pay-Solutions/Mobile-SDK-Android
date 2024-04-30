package com.easypaysolutions.repositories.annual_consent

import com.easypaysolutions.repositories.annual_consent.cancel.CancelAnnualConsentRepository
import com.easypaysolutions.repositories.annual_consent.cancel.CancelAnnualConsentRepositoryImpl
import com.easypaysolutions.repositories.annual_consent.create.CreateAnnualConsentRepository
import com.easypaysolutions.repositories.annual_consent.create.CreateAnnualConsentRepositoryImpl
import com.easypaysolutions.repositories.annual_consent.list.ListAnnualConsentsRepository
import com.easypaysolutions.repositories.annual_consent.list.ListAnnualConsentsRepositoryImpl
import com.easypaysolutions.repositories.annual_consent.process_payment.ProcessPaymentAnnualRepository
import com.easypaysolutions.repositories.annual_consent.process_payment.ProcessPaymentAnnualRepositoryImpl
import org.koin.dsl.module

internal object ConsentAnnualModule {

    val consentAnnualModules = module {
        single<ListAnnualConsentsRepository> { ListAnnualConsentsRepositoryImpl(get()) }
        single<CreateAnnualConsentRepository> { CreateAnnualConsentRepositoryImpl(get()) }
        single<CancelAnnualConsentRepository> { CancelAnnualConsentRepositoryImpl(get()) }
        single<ProcessPaymentAnnualRepository> { ProcessPaymentAnnualRepositoryImpl(get()) }
    }
}
