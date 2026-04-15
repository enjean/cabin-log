package enjean.cabinlog.cabin

import enjean.cabinlog.testutil.BaseIntegrationTest
import enjean.cabinlog.visitor.VisitorResponse
import enjean.cabinlog.visitor.VisitorsResponse
import org.assertj.core.api.Assertions.assertThat
import org.springframework.boot.test.web.client.getForEntity
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import kotlin.test.Test

class GetAllVisitorsIntegrationTest : BaseIntegrationTest() {

    @Test
    fun `get all visitors for a cabin`() {
        val cabin1 = createTestCabin("Test Cabin 1")

        val visitor1 = createTestVisitor(cabinId = cabin1.id, name = "Moiraine Damodred")
        val visitor2 = createTestVisitor(cabinId = cabin1.id, name = "Elayne Trakand")
        val visitor3 = createTestVisitor(cabinId = cabin1.id, name = "Thom Merrillin")

        val cabin2 = createTestCabin("Test Cabin 2")
        val visitor4 = createTestVisitor(cabinId = cabin2.id, name = "Padan Fain")

        val cabin3 = createTestCabin("Test Cabin 3")

        getVisitorsForCabin(cabinId = cabin1.id).assertVisitors(listOf(visitor1, visitor2, visitor3))
        getVisitorsForCabin(cabinId = cabin2.id).assertVisitors(listOf(visitor4))
        getVisitorsForCabin(cabinId = cabin3.id).assertVisitors(emptyList())
    }

    private fun getVisitorsForCabin(cabinId: Long) =
        testRestTemplate.getForEntity<VisitorsResponse>("/cabins/$cabinId/visitors")

    private fun ResponseEntity<VisitorsResponse>.assertVisitors(expectedVisitors: Collection<VisitorResponse>) {
        assertThat(statusCode).isEqualTo(HttpStatus.OK)
        val visitorsResponse = checkNotNull(body)
        assertThat(visitorsResponse.visitors).containsExactlyInAnyOrderElementsOf(expectedVisitors)
    }
}
