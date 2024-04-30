package com.easypaysolutions.repositories.annual_consent.create

import com.easypaysolutions.networking.NetworkResource
import com.easypaysolutions.utils.AnnualConsentHelper.prepareParams
import com.easypaysolutions.utils.TestApiHelper
import com.easypaysolutions.utils.secured.SecureData
import junit.framework.TestCase
import kotlinx.coroutines.runBlocking
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
internal class CreateAnnualConsentRepositoryTest {

    private val apiHelper = TestApiHelper()
    private val repository: CreateAnnualConsentRepository =
        CreateAnnualConsentRepositoryImpl(apiHelper)
    private val testSecureData = SecureData("encryptedData")

    //region Tests

    @Test
    fun `createAnnualConsent() with valid params returns Success`() = runBlocking {
        val params = prepareParams(testSecureData)
        val result = repository.createAnnualConsent(params)
        TestCase.assertEquals(result.status, NetworkResource.Status.SUCCESS)
    }

    @Test
    fun `createAnnualConsent() with too long ZIP parameter returns Error for MaxLength`() =
        runBlocking {
            val tooLongZip = "99999999999999999999999"    //should be limited to 20 digits
            val params = prepareParams(testSecureData, zip = tooLongZip)
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
            val params = prepareParams(testSecureData, zip = invalidZip)
            val result = repository.createAnnualConsent(params)
            TestCase.assertEquals(result.status, NetworkResource.Status.ERROR)
            TestCase.assertEquals(result.error?.message, "zip contains invalid characters")
        }

    @Test
    fun `createAnnualConsent() with negative LimitPerCharge returns Error for DoubleNotGreaterThanZero`() =
        runBlocking {
            val invalidLimitPerCharge: Double = -1.0    //double cannot be negative
            val params = prepareParams(testSecureData, limitPerCharge = invalidLimitPerCharge)
            val result = repository.createAnnualConsent(params)
            TestCase.assertEquals(result.status, NetworkResource.Status.ERROR)
            TestCase.assertEquals(result.error?.message, "limitPerCharge must be greater than 0.0")
        }

    @Test
    fun `createAnnualConsent() with blank CSV returns Error for NotBlank`() =
        runBlocking {
            val invalidCsv = ""     //CSV cannot be blank
            val params = prepareParams(testSecureData, csv = invalidCsv)
            val result = repository.createAnnualConsent(params)
            TestCase.assertEquals(result.status, NetworkResource.Status.ERROR)
            TestCase.assertEquals(result.error?.message, "csv cannot be blank")
        }

    //endregion

}