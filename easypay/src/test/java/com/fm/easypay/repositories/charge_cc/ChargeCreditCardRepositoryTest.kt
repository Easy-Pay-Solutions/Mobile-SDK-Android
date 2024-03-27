package com.fm.easypay.repositories.charge_cc

import com.fm.easypay.api.EasyPayApiHelper
import com.fm.easypay.api.requests.charge_cc.ChargeCreditCardRequest
import com.fm.easypay.api.requests.annual_consent.ListAnnualConsentsRequest
import com.fm.easypay.api.requests.annual_consent.CreateAnnualConsentRequest
import com.fm.easypay.api.responses.charge_cc.ChargeCreditCardResult
import com.fm.easypay.api.responses.annual_consent.ListAnnualConsentsResult
import com.fm.easypay.api.responses.annual_consent.CreateAnnualConsentResult
import com.fm.easypay.networking.NetworkResource
import com.fm.easypay.utils.secured.SecureData
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.runBlocking
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mockito.mock
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
internal class ChargeCreditCardRepositoryTest {

    private val apiHelper = TestApiHelper()
    private val repository: ChargeCreditCardRepository = ChargeCreditCardRepositoryImpl(apiHelper)
    private val testSecureData = SecureData("encryptedData")

    //region Tests

    @Test
    fun `chargeCreditCard() with valid params returns Success`() = runBlocking {
        val params = prepareParams()
        val result = repository.chargeCreditCard(params)
        assertEquals(result.status, NetworkResource.Status.SUCCESS)
    }

    @Test
    fun `chargeCreditCard() with too long ZIP parameter returns Error for MaxLength`() =
        runBlocking {
            val tooLongZip = "99999999999999999999999"    //should be limited to 20 digits
            val params = prepareParams(tooLongZip)
            val result = repository.chargeCreditCard(params)
            assertEquals(result.status, NetworkResource.Status.ERROR)
            assertEquals(result.error?.message, "zip exceeds maximum length of 20 characters")
        }

    @Test
    fun `chargeCreditCard() with invalid characters in ZIP returns Error for InvalidCharacters`() =
        runBlocking {
            val invalidZip = "999?"    //cannot contain question mark
            val params = prepareParams(invalidZip)
            val result = repository.chargeCreditCard(params)
            assertEquals(result.status, NetworkResource.Status.ERROR)
            assertEquals(result.error?.message, "zip contains invalid characters")
        }

    @Test
    fun `chargeCreditCard() with negative TotalAmount returns Error for DoubleNotGreaterThanZero`() =
        runBlocking {
            val invalidTotalAmount: Double = -1.0    //double cannot be negative
            val params = prepareParams(totalAmount = invalidTotalAmount)
            val result = repository.chargeCreditCard(params)
            assertEquals(result.status, NetworkResource.Status.ERROR)
            assertEquals(result.error?.message, "totalAmount must be greater than 0.0")
        }

    //endregion

    //region Private

    private fun prepareParams(
        zip: String = "04005",
        totalAmount: Double = 10.0,
    ): ChargeCreditCardBodyParams =
        ChargeCreditCardBodyParams(
            encryptedCardNumber = testSecureData,
            creditCardInfo = prepareCreditCardInfo(),
            accountHolder = prepareAccountHolder(zip),
            endCustomer = prepareEndCustomer(),
            amounts = prepareAmounts(totalAmount),
            purchaseItems = preparePurchaseItems(),
            merchantId = 1
        )

    private fun prepareCreditCardInfo(): CreditCardInfoParam = CreditCardInfoParam(
        expMonth = 10,
        expYear = 2026,
        csv = "999"
    )

    private fun preparePurchaseItems(): PurchaseItemsParam = PurchaseItemsParam(
        serviceDescription = "FROM API TESTER",
        clientRefId = "12456",
        rpguid = "3d3424a6-c5f3-4c28"
    )

    private fun prepareAmounts(totalAmount: Double): AmountsParam = AmountsParam(
        totalAmount = totalAmount,
        salesTax = 0.0,
        surcharge = 0.0,
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

    private class TestApiHelper : EasyPayApiHelper {
        override suspend fun listAnnualConsents(request: ListAnnualConsentsRequest): NetworkResource<ListAnnualConsentsResult> {
            // not needed for this test
            return NetworkResource.success(mock())
        }

        override suspend fun chargeCreditCard(request: ChargeCreditCardRequest): NetworkResource<ChargeCreditCardResult> {
            return NetworkResource.success(mock())
        }

        override suspend fun createAnnualConsent(request: CreateAnnualConsentRequest): NetworkResource<CreateAnnualConsentResult> {
            return NetworkResource.success(mock())
        }
    }

    //endregion

}