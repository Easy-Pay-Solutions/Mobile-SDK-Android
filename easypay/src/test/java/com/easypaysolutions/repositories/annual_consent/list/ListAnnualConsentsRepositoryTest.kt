package com.easypaysolutions.repositories.annual_consent.list

import com.easypaysolutions.networking.NetworkResource
import com.easypaysolutions.utils.TestApiHelper
import junit.framework.TestCase
import kotlinx.coroutines.runBlocking
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
internal class ListAnnualConsentsRepositoryTest {

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

    //endregion

}