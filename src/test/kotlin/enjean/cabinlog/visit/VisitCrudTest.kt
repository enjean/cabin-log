package enjean.cabinlog.visit

import enjean.cabinlog.testutil.BaseIntegrationTest
import org.apache.commons.lang3.RandomStringUtils
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.springframework.http.HttpStatus
import java.time.LocalDate

class VisitCrudTest : BaseIntegrationTest() {

    @Test
    fun `can create a visit`() {
        val cabin = createTestCabin()

        val visitName = RandomStringUtils.secure().nextAlphanumeric(10)
        val startDate = LocalDate.of(2025, 6, 15)
        val endDate = LocalDate.of(2025, 6, 17)
        val middleDate = LocalDate.of(2025, 6, 16)

        val visitor1 = createTestVisitor(cabinId = cabin.id, name = "Rand al'Thor")
        val visitor2 = createTestVisitor(cabinId = cabin.id, name = "Perrin Aybara")
        val visitor3 = createTestVisitor(cabinId = cabin.id, name = "Mat Cauthon")

        val request = CreateVisitRequest(
            name = visitName,
            cabinId = cabin.id,
            startDate = startDate,
            endDate = endDate,
            visitors = listOf(
                VisitVisitorRequest(
                    visitorId = visitor1.id,
                    visitPeriods = listOf(VisitPeriodRequest(startDate = startDate, endDate = endDate)),
                ),
                VisitVisitorRequest(
                    visitorId = visitor2.id,
                    visitPeriods = listOf(VisitPeriodRequest(startDate = middleDate, endDate = middleDate)),
                ),
                VisitVisitorRequest(
                    visitorId = visitor3.id,
                    visitPeriods =
                        listOf(
                            VisitPeriodRequest(startDate = startDate, endDate = startDate),
                            VisitPeriodRequest(startDate = endDate, endDate = endDate),
                        ),
                ),
            )
        )

        val response = testRestTemplate.postForEntity(
            "/visits",
            request,
            VisitResponse::class.java,
        )
        assertThat(response.statusCode).isEqualTo(HttpStatus.CREATED)
        val visitResponse = response.body!!
        assertThat(visitResponse.name).isEqualTo(visitName)
        assertThat(visitResponse.cabin).isEqualTo(cabin)
        assertThat(visitResponse.startDate).isEqualTo(startDate)
        assertThat(visitResponse.endDate).isEqualTo(endDate)

        assertThat(visitResponse.visitVisitors).hasSize(3)

        val visitVisitorResponseForVisitor1 = visitResponse.visitVisitors.first { it.visitor.id == visitor1.id }
        assertThat(visitVisitorResponseForVisitor1.visitor.name).isEqualTo("Rand al'Thor")
        assertThat(visitVisitorResponseForVisitor1.visitPeriods).containsExactly(
            VisitPeriodResponse(startDate, endDate),
        )

        val visitVisitorResponseForVisitor2 = visitResponse.visitVisitors.first { it.visitor.id == visitor2.id }
        assertThat(visitVisitorResponseForVisitor2.visitor.name).isEqualTo("Perrin Aybara")
        assertThat(visitVisitorResponseForVisitor2.visitPeriods).containsExactly(
            VisitPeriodResponse(middleDate, middleDate),
        )

        val visitVisitorResponseForVisitor3 = visitResponse.visitVisitors.first { it.visitor.id == visitor3.id }
        assertThat(visitVisitorResponseForVisitor3.visitor.name).isEqualTo("Mat Cauthon")
        assertThat(visitVisitorResponseForVisitor3.visitPeriods).containsExactly(
            VisitPeriodResponse(startDate, startDate),
            VisitPeriodResponse(endDate, endDate),
        )
    }
}