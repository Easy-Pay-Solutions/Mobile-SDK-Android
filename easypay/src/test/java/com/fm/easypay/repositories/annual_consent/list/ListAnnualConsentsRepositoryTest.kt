package com.fm.easypay.repositories.annual_consent.list

import com.fm.easypay.api.EasyPayApiHelper
import com.fm.easypay.api.requests.annual_consent.CreateAnnualConsentRequest
import com.fm.easypay.api.requests.annual_consent.ListAnnualConsentsRequest
import com.fm.easypay.api.requests.charge_cc.ChargeCreditCardRequest
import com.fm.easypay.api.responses.annual_consent.CreateAnnualConsentResult
import com.fm.easypay.api.responses.annual_consent.ListAnnualConsentsResult
import com.fm.easypay.api.responses.charge_cc.ChargeCreditCardResult
import com.fm.easypay.networking.NetworkResource
import junit.framework.TestCase
import kotlinx.coroutines.runBlocking
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mockito
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class ListAnnualConsentsRepositoryTest {

    private val apiHelper = TestApiHelper()
    private val repository: ListAnnualConsentsRepository =
        ListAnnualConsentsRepositoryImpl(apiHelper)

    //region Tests

    @Test
    fun `listAnnualConsents() with valid params returns Success`() = runBlocking {
        val params = prepareParams()
        val result = repository.listAnnualConsents(params)
        TestCase.assertEquals(result.status, NetworkResource.Status.SUCCESS)
    }

    //endregion

    //region Private

    private fun prepareParams(): ListAnnualConsentsBodyParams = ListAnnualConsentsBodyParams(
        merchantId = 1,
        customerReferenceId = "123456"
    )

    private class TestApiHelper : EasyPayApiHelper {
        override suspend fun listAnnualConsents(request: ListAnnualConsentsRequest): NetworkResource<ListAnnualConsentsResult> {
            return NetworkResource.success(Mockito.mock())
        }

        override suspend fun chargeCreditCard(request: ChargeCreditCardRequest): NetworkResource<ChargeCreditCardResult> {
            return NetworkResource.success(Mockito.mock())
        }

        override suspend fun createAnnualConsent(request: CreateAnnualConsentRequest): NetworkResource<CreateAnnualConsentResult> {
            return NetworkResource.success(Mockito.mock())
        }
    }

    //endregion

}