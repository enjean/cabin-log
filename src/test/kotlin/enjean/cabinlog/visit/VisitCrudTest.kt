package enjean.cabinlog.visit

import enjean.cabinlog.testutil.BaseIntegrationTest
import org.apache.commons.lang3.RandomStringUtils
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.springframework.boot.test.web.client.getForEntity
import org.springframework.boot.test.web.client.postForEntity
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
            startDate = startDate,
            endDate = endDate,
            visitors = VisitorsInfo(
                fullTimeVisitorIds = listOf(visitor1.id),
                partialVisitors = listOf(
                    VisitVisitorRequest(
                        id = visitor2.id,
                        visitPeriods = listOf(VisitPeriodRequest(startDate = middleDate, endDate = middleDate)),
                    ),
                    VisitVisitorRequest(
                        id = visitor3.id,
                        visitPeriods = listOf(
                            VisitPeriodRequest(startDate = startDate, endDate = startDate),
                            VisitPeriodRequest(startDate = endDate, endDate = endDate)
                        ),
                    )
                )
            )
        )

        val response = testRestTemplate.postForEntity<VisitResponse>(
            "/cabins/${cabin.id}/visits",
            request,
        )
        assertThat(response.statusCode).isEqualTo(HttpStatus.CREATED)
        val visitResponse = response.body!!
        assertThat(visitResponse.name).isEqualTo(visitName)
        assertThat(visitResponse.cabin).isEqualTo(cabin)
        assertThat(visitResponse.startDate).isEqualTo(startDate)
        assertThat(visitResponse.endDate).isEqualTo(endDate)

        assertThat(visitResponse.visitors.fullTimeVisitors).hasSize(1)
        val fullTimeVisitor = visitResponse.visitors.fullTimeVisitors[0]
        assertThat(fullTimeVisitor.name).isEqualTo("Rand al'Thor")

        assertThat(visitResponse.visitors.partTimeVisitors).hasSize(2)

        val visitVisitorResponseForVisitor2 = visitResponse.visitors.partTimeVisitors.first { it.visitor.id == visitor2.id }
        assertThat(visitVisitorResponseForVisitor2.visitor.name).isEqualTo("Perrin Aybara")
        assertThat(visitVisitorResponseForVisitor2.visitPeriods).containsExactly(
            VisitPeriodResponse(middleDate, middleDate),
        )

        val visitVisitorResponseForVisitor3 = visitResponse.visitors.partTimeVisitors.first { it.visitor.id == visitor3.id }
        assertThat(visitVisitorResponseForVisitor3.visitor.name).isEqualTo("Mat Cauthon")
        assertThat(visitVisitorResponseForVisitor3.visitPeriods).containsExactly(
            VisitPeriodResponse(startDate, startDate),
            VisitPeriodResponse(endDate, endDate),
        )

        val getVisitResponseEntity = testRestTemplate.getForEntity<VisitResponse>("/visits/${visitResponse.id}")
        assertThat(getVisitResponseEntity.statusCode).isEqualTo(HttpStatus.OK)
        val getVisitResponse = getVisitResponseEntity.body!!
        assertThat(getVisitResponse.name).isEqualTo(visitName)
        assertThat(getVisitResponse.visitors.fullTimeVisitors).hasSize(1)
        assertThat(getVisitResponse.visitors.partTimeVisitors).hasSize(2)
    }
}
