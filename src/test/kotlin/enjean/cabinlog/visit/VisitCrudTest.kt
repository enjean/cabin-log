package enjean.cabinlog.visit

import enjean.cabinlog.testutil.BaseIntegrationTest
import org.apache.commons.lang3.RandomStringUtils
import org.junit.jupiter.api.Test
import org.springframework.http.HttpStatus
import java.time.LocalDate
import kotlin.test.assertEquals

class VisitCrudTest : BaseIntegrationTest() {

    @Test
    fun `can create a visit`() {
        val cabin = createTestCabin()
        val visitName = RandomStringUtils.secure().nextAlphanumeric(10)
        val startDate = LocalDate.of(2025, 6, 15)
        val endDate = startDate.plusDays(2)
        val request = CreateVisitRequest(
            name = visitName,
            cabinId = cabin.id,
            startDate = startDate,
            endDate = endDate,
        )

        val response = testRestTemplate.postForEntity(
            "/visits",
            request,
            VisitResponse::class.java,
        )
        assertEquals(HttpStatus.CREATED, response.statusCode)
        val visitResponse = response.body!!
        assertEquals(visitName, visitResponse.name)
        assertEquals(cabin, visitResponse.cabin)
        assertEquals(startDate, visitResponse.startDate)
        assertEquals(endDate, visitResponse.endDate)
    }
}