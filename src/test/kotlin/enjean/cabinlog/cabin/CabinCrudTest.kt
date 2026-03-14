package enjean.cabinlog.cabin

import enjean.cabinlog.testutil.BaseIntegrationTest
import org.apache.commons.lang3.RandomStringUtils
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.springframework.boot.test.web.client.postForEntity
import org.springframework.http.HttpStatus


class CabinCrudTest : BaseIntegrationTest() {

    @Test
    fun `create a cabin`() {
        val cabinName = RandomStringUtils.secure().nextAlphanumeric(10)
        val request = CreateCabinRequest(
            name = cabinName,
        )

        val response = testRestTemplate.postForEntity<CabinResponse>(
            "/cabins",
            request,
        )
        assertThat(response.statusCode).isEqualTo(HttpStatus.CREATED)
        val cabinResponse = response.body!!
        assertThat(cabinResponse.name).isEqualTo(cabinName)
    }
}
