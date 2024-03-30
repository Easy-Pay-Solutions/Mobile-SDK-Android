package com.fm.easypay.repositories.annual_consent.create

import com.fm.easypay.networking.NetworkResource
import com.fm.easypay.repositories.charge_cc.BillingAddressParam
import com.fm.easypay.repositories.charge_cc.CreditCardInfoParam
import com.fm.easypay.repositories.charge_cc.PersonalDataParam
import com.fm.easypay.utils.TestApiHelper
import com.fm.easypay.utils.secured.SecureData
import junit.framework.TestCase
import kotlinx.coroutines.runBlocking
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.junit.MockitoJUnitRunner
import java.util.Date

@RunWith(MockitoJUnitRunner::class)
internal class CreateAnnualConsentRepositoryTest {

    private val apiHelper = TestApiHelper()
    private val repository: CreateAnnualConsentRepository =
        CreateAnnualConsentRepositoryImpl(apiHelper)
    private val testSecureData = SecureData("encryptedData")

    //region Tests

    @Test
    fun `createAnnualConsent() with valid params returns Success`() = runBlocking {
        val params = prepareParams()
        val result = repository.createAnnualConsent(params)
        TestCase.assertEquals(result.status, NetworkResource.Status.SUCCESS)
    }

    @Test
    fun `createAnnualConsent() with too long ZIP parameter returns Error for MaxLength`() =
        runBlocking {
            val tooLongZip = "99999999999999999999999"    //should be limited to 20 digits
            val params = prepareParams(tooLongZip)
            val result = repository.createAnnualConsent(params)
            TestCase.assertEquals(result.status, NetworkResource.Status.ERROR)
            TestCase.assertEquals(
                result.error?.message,
                "zip exceeds maximum length of 20 characters"
            )
        }

    @Test
    fun `createAnnualConsent() with invalid characters in ZIP returns Error for InvalidCharacters`() =
        runBlocking {
            val invalidZip = "999?"    //cannot contain question mark
            val params = prepareParams(invalidZip)
            val result = repository.createAnnualConsent(params)
            TestCase.assertEquals(result.status, NetworkResource.Status.ERROR)
            TestCase.assertEquals(result.error?.message, "zip contains invalid characters")
        }

    @Test
    fun `chargeCreditCard() with negative LimitPerCharge returns Error for DoubleNotGreaterThanZero`() =
        runBlocking {
            val invalidLimitPerCharge: Double = -1.0    //double cannot be negative
            val params = prepareParams(limitPerCharge = invalidLimitPerCharge)
            val result = repository.createAnnualConsent(params)
            TestCase.assertEquals(result.status, NetworkResource.Status.ERROR)
            TestCase.assertEquals(result.error?.message, "limitPerCharge must be greater than 0.0")
        }

    //endregion

    //region Private

    private fun prepareParams(
        zip: String = "04005",
        limitPerCharge: Double = 10.0,
    ): CreateAnnualConsentBodyParams =
        CreateAnnualConsentBodyParams(
            encryptedCardNumber = testSecureData,
            creditCardInfo = prepareCreditCardInfo(),
            accountHolder = prepareAccountHolder(zip),
            endCustomer = prepareEndCustomer(),
            consentCreator = prepareConsentCreator(limitPerCharge),
        )

    private fun prepareConsentCreator(limitPerCharge: Double): ConsentCreatorParam =
        ConsentCreatorParam(
            merchantId = 1,
            serviceDescription = "Service Description",
            clientRefId = "Client Ref Id",
            rpguid = "RPGUID",
            startDate = Date(),
            numDays = 365,
            limitPerCharge = limitPerCharge,
            limitLifeTime = 100.0
        )

    private fun prepareCreditCardInfo(): CreditCardInfoParam = CreditCardInfoParam(
        expMonth = 10,
        expYear = 2026,
        csv = "999"
    )

    private fun prepareAccountHolder(zip: String): PersonalDataParam =
        PersonalDataParam(
            firstName = "John",
            lastName = "Doe",
            company = "",
            billingAddress = prepareBillingAddress(zip),
            email = "robert@easypaysolutions.com",
            phone = "8775558472"
        )

    private fun prepareEndCustomer(): PersonalDataParam = PersonalDataParam(
        firstName = "John",
        lastName = "Doe",
        company = "",
        billingAddress = prepareBillingAddress(),
        email = "robert@easypaysolutions.com",
        phone = "8775558472"
    )

    private fun prepareBillingAddress(zip: String = "04005"): BillingAddressParam =
        BillingAddressParam(
            address1 = "123 Fake St.",
            address2 = "",
            city = "PORTLAND",
            state = "ME",
            zip = zip,
            country = "USA"
        )

    //endregion

}