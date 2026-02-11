package enjean.cabinlog.cabin

import enjean.cabinlog.testutil.BaseIntegrationTest
import org.apache.commons.lang3.RandomStringUtils
import org.junit.jupiter.api.Test
import org.springframework.http.HttpStatus
import kotlin.test.assertEquals


class CabinCrudTest : BaseIntegrationTest() {

    @Test
    fun `create a cabin`() {
        val cabinName = RandomStringUtils.secure().nextAlphanumeric(10)
        val request = CreateCabinRequest(
            name = cabinName,
        )

        val response = testRestTemplate.postForEntity(
            "/cabins",
            request,
            CabinResponse::class.java,
        )
        assertEquals(HttpStatus.CREATED, response.statusCode)
        val cabinResponse = response.body!!
        assertEquals(cabinName, cabinResponse.name)
    }
}
