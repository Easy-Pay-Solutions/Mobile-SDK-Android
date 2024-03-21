package com.fm.easypay.api

import com.fm.easypay.api.requests.ChargeCreditCardBodyDto
import com.fm.easypay.api.requests.ChargeCreditCardRequest
import com.fm.easypay.api.requests.base.EasyPayQuery
import com.fm.easypay.api.responses.ChargeCreditCardResponse
import com.fm.easypay.api.responses.ChargeCreditCardResult
import com.fm.easypay.api.responses.ConsentAnnualQueryResponse
import com.fm.easypay.networking.DefaultNetworkDataSource
import com.fm.easypay.networking.NetworkResource
import com.fm.easypay.networking.authentication.AuthHelper
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.runBlocking
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.ResponseBody.Companion.toResponseBody
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mockito.mock
import org.mockito.junit.MockitoJUnitRunner
import retrofit2.Response

@RunWith(MockitoJUnitRunner::class)
internal class EasyPayApiHelperTest {

    private val authHelper = TestAuthHelper()
    private val networkDataSource = DefaultNetworkDataSource()
    private lateinit var easyPayApiHelper: EasyPayApiHelper

    //region Tests

    @Test
    fun `chargeCreditCard() returns Success`() = runBlocking {
        val result = initHelperWith(TestSuccessEasyPayService())
        assertEquals(result.status, NetworkResource.Status.SUCCESS)
    }

    @Test
    fun `chargeCreditCard() returns Declined`() = runBlocking {
        val result = initHelperWith(TestDeclinedEasyPayService())
        assertEquals(result.status, NetworkResource.Status.DECLINED)
    }

    @Test
    fun `chargeCreditCard() returns Error from API`() = runBlocking {
        val result = initHelperWith(TestApiErrorEasyPayService())
        assertEquals(result.status, NetworkResource.Status.ERROR)
    }

    @Test
    fun `chargeCreditCard() returns Error from SDK`() = runBlocking {
        val result = initHelperWith(TestSdkErrorEasyPayService())
        assertEquals(result.status, NetworkResource.Status.ERROR)
    }

    //endregion

    //region Helpers

    private suspend fun initHelperWith(easyPayService: EasyPayService): NetworkResource<ChargeCreditCardResult> {
        easyPayApiHelper = EasyPayApiHelperImpl(
            easyPayService,
            networkDataSource,
            authHelper
        )
        val request = ChargeCreditCardRequest(userDataPresent = true, mock())
        return easyPayApiHelper.chargeCreditCard(request)
    }

    private class TestSdkErrorEasyPayService : TestEasyPayService() {
        override suspend fun cardSaleManual(
            sessKey: String,
            body: ChargeCreditCardBodyDto,
        ): Response<ChargeCreditCardResponse> {
            return Response.error(
                400,
                "{\"key\":[\"test\"]}".toResponseBody("application/json".toMediaTypeOrNull())
            )
        }
    }

    private class TestApiErrorEasyPayService : TestEasyPayService() {
        override suspend fun cardSaleManual(
            sessKey: String,
            body: ChargeCreditCardBodyDto,
        ): Response<ChargeCreditCardResponse> {
            val result = TestHelper.prepareChargeCardResult(
                functionOk = false,
                txApproved = false
            )
            val response = ChargeCreditCardResponse(result)
            return Response.success(response)
        }
    }

    private class TestDeclinedEasyPayService : TestEasyPayService() {
        override suspend fun cardSaleManual(
            sessKey: String,
            body: ChargeCreditCardBodyDto,
        ): Response<ChargeCreditCardResponse> {
            val result = TestHelper.prepareChargeCardResult(
                functionOk = true,
                txApproved = false
            )
            val response = ChargeCreditCardResponse(result)
            return Response.success(response)
        }
    }

    private class TestSuccessEasyPayService : TestEasyPayService() {
        override suspend fun cardSaleManual(
            sessKey: String,
            body: ChargeCreditCardBodyDto,
        ): Response<ChargeCreditCardResponse> {
            val result = TestHelper.prepareChargeCardResult()
            val response = ChargeCreditCardResponse(result)
            return Response.success(response)
        }
    }

    private abstract class TestEasyPayService : EasyPayService {
        override suspend fun getConsentAnnuals(
            sessKey: String,
            query: EasyPayQuery,
        ): Response<ConsentAnnualQueryResponse> {
            return mock()
        }
    }

    private class TestAuthHelper : AuthHelper {
        override fun getSessKey(userDataPresent: Boolean): String = "test"

        override fun getHmacHash(
            sessionKey: String,
            epoch: String,
            deviceId: String,
            hmacSecret: String,
        ): String = "test"
    }

    private object TestHelper {
        fun prepareChargeCardResult(
            functionOk: Boolean = true,
            txApproved: Boolean = true,
        ): ChargeCreditCardResult {
            return ChargeCreditCardResult(
                functionOk = functionOk,
                errorCode = 0,
                errorMessage = "",
                responseMessage = "APPROVED OK4501",
                txApproved = txApproved,
                txId = 64,
                txCode = "OK4501",
                avsResult = "Y",
                acquirerResponseEmv = null,
                cvvResult = "",
                isPartialApproval = false,
                requiresVoiceAuth = false,
                responseApprovedAmount = 0,
                responseAuthorizedAmount = 0,
                responseBalanceAmount = 0
            )
        }
    }

    //endregion

}