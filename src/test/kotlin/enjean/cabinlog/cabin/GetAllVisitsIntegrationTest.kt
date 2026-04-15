package enjean.cabinlog.cabin

import enjean.cabinlog.testutil.BaseIntegrationTest
import enjean.cabinlog.visit.VisitSummariesResponse
import enjean.cabinlog.visit.VisitSummaryVisitorResponse
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.springframework.boot.test.web.client.getForEntity
import org.springframework.http.HttpStatus
import java.time.LocalDate

class GetAllVisitsIntegrationTest : BaseIntegrationTest() {

    @Test
    fun `get all visits`() {
        val cabin = createTestCabin()
        val visitor1 = createTestVisitor(cabinId = cabin.id, name = "Moiraine Damodred")
        val visitor2 = createTestVisitor(cabinId = cabin.id, name = "Elayne Trakand")
        val visitor3 = createTestVisitor(cabinId = cabin.id, name = "Thom Merrillin")

        createTestVisit(
            cabinId = cabin.id,
            name = "Spring 2025",
            startDate = LocalDate.of(2025, 3, 1),
            endDate = LocalDate.of(2025, 3, 1),
            fullTimeVisitorIds = listOf(visitor1.id),
        )
        createTestVisit(
            cabinId = cabin.id,
            name = "Jan 2026",
            startDate = LocalDate.of(2026, 1, 15),
            endDate = LocalDate.of(2026, 1, 16),
            fullTimeVisitorIds = listOf(visitor3.id, visitor1.id, visitor2.id),
        )
        createTestVisit(
            cabinId = cabin.id,
            name = "NYE 2025",
            startDate = LocalDate.of(2025, 12, 30),
            endDate = LocalDate.of(2026, 1, 3),
            fullTimeVisitorIds = listOf(visitor3.id),
        )
        createTestVisit(
            cabinId = cabin.id,
            name = "Summer 2025",
            startDate = LocalDate.of(2025, 6, 1),
            endDate = LocalDate.of(2025, 6, 3),
            fullTimeVisitorIds = listOf(visitor2.id),
        )

        val response = testRestTemplate.getForEntity<VisitSummariesResponse>("/cabins/${cabin.id}/visits")
        assertThat(response.statusCode).isEqualTo(HttpStatus.OK)
        val visitSummariesResponse = checkNotNull(response.body)
        assertThat(visitSummariesResponse.visitSummaries).hasSize(4)

        assertThat(visitSummariesResponse.visitSummaries.map { it.name })
            .containsExactly("Jan 2026", "NYE 2025", "Summer 2025", "Spring 2025") // reverse chronological order

        val jan26Summary = visitSummariesResponse.visitSummaries[0]
        assertThat(jan26Summary.visitors).containsExactly(
            VisitSummaryVisitorResponse(id = visitor2.id, "Elayne Trakand"),
            VisitSummaryVisitorResponse(id = visitor1.id, "Moiraine Damodred"),
            VisitSummaryVisitorResponse(id = visitor3.id, "Thom Merrillin"),
        ) // alphabetized

        val responseFor2025 = getVisitsForYear(cabinId = cabin.id, year = 2025)
        assertThat(responseFor2025.body!!.visitSummaries).hasSize(3)

        val responseFor2026 = getVisitsForYear(cabinId = cabin.id, year = 2026)
        assertThat(responseFor2026.body!!.visitSummaries).hasSize(1)

        val responseFor2024 = getVisitsForYear(cabinId = cabin.id, year = 2024)
        assertThat(responseFor2024.body!!.visitSummaries).isEmpty()
    }

    private fun getVisitsForYear(cabinId: Long, year: Int) =
        testRestTemplate.getForEntity<VisitSummariesResponse>("/cabins/$cabinId/visits?year=$year")
}
