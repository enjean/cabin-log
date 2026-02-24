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
        assertEquals(HttpStatus.CREATED, response.statusCode)
        val visitResponse = response.body!!
        assertEquals(visitName, visitResponse.name)
        assertEquals(cabin, visitResponse.cabin)
        assertEquals(startDate, visitResponse.startDate)
        assertEquals(endDate, visitResponse.endDate)

        assertEquals(3, visitResponse.visitVisitors.size)

        val visitVisitorResponseForVisitor1 = visitResponse.visitVisitors.first { it.visitor.id == visitor1.id }
        assertEquals("Rand al'Thor", visitVisitorResponseForVisitor1.visitor.name)
        assertEquals(1, visitVisitorResponseForVisitor1.visitPeriods.size)
        assertEquals(VisitPeriodResponse(startDate, endDate), visitVisitorResponseForVisitor1.visitPeriods[0])

        val visitVisitorResponseForVisitor2 = visitResponse.visitVisitors.first { it.visitor.id == visitor2.id }
        assertEquals("Perrin Aybara", visitVisitorResponseForVisitor2.visitor.name)
        assertEquals(1, visitVisitorResponseForVisitor2.visitPeriods.size)
        assertEquals(VisitPeriodResponse(middleDate, middleDate), visitVisitorResponseForVisitor2.visitPeriods[0])

        val visitVisitorResponseForVisitor3 = visitResponse.visitVisitors.first { it.visitor.id == visitor3.id }
        assertEquals("Mat Cauthon", visitVisitorResponseForVisitor3.visitor.name)
        assertEquals(2, visitVisitorResponseForVisitor3.visitPeriods.size)
        assertEquals(VisitPeriodResponse(startDate, startDate), visitVisitorResponseForVisitor3.visitPeriods[0])
        assertEquals(VisitPeriodResponse(endDate, endDate), visitVisitorResponseForVisitor3.visitPeriods[1])
    }
}