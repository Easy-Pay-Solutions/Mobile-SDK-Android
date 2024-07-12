package com.easypaysolutions.repositories.annual_consent

import com.easypaysolutions.repositories.Validator
import com.easypaysolutions.repositories.annual_consent.cancel.CancelAnnualConsentRepository
import com.easypaysolutions.repositories.annual_consent.cancel.CancelAnnualConsentRepositoryImpl
import com.easypaysolutions.repositories.annual_consent.create.CreateAnnualConsentBodyParams
import com.easypaysolutions.repositories.annual_consent.create.CreateAnnualConsentRepository
import com.easypaysolutions.repositories.annual_consent.create.CreateAnnualConsentRepositoryImpl
import com.easypaysolutions.repositories.annual_consent.create.CreateAnnualConsentValidator
import com.easypaysolutions.repositories.annual_consent.list.ListAnnualConsentsBodyParams
import com.easypaysolutions.repositories.annual_consent.list.ListAnnualConsentsRepository
import com.easypaysolutions.repositories.annual_consent.list.ListAnnualConsentsRepositoryImpl
import com.easypaysolutions.repositories.annual_consent.list.ListAnnualConsentsValidator
import com.easypaysolutions.repositories.annual_consent.process_payment.ProcessPaymentAnnualRepository
import com.easypaysolutions.repositories.annual_consent.process_payment.ProcessPaymentAnnualRepositoryImpl
import org.koin.dsl.module

internal object ConsentAnnualModule {

    val consentAnnualModules = module {
        single<Validator<CreateAnnualConsentBodyParams>> { CreateAnnualConsentValidator() }
        single<Validator<ListAnnualConsentsBodyParams>> { ListAnnualConsentsValidator() }
        single<ListAnnualConsentsRepository> { ListAnnualConsentsRepositoryImpl(get(), get()) }
        single<CreateAnnualConsentRepository> { CreateAnnualConsentRepositoryImpl(get(), get()) }
        single<CancelAnnualConsentRepository> { CancelAnnualConsentRepositoryImpl(get()) }
        single<ProcessPaymentAnnualRepository> { ProcessPaymentAnnualRepositoryImpl(get()) }
    }
}
