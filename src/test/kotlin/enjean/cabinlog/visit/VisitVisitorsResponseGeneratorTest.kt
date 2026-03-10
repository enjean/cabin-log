package enjean.cabinlog.visit

import enjean.cabinlog.visitor.VisitorEntity
import enjean.cabinlog.visitor.VisitorResponse
import org.apache.commons.lang3.RandomStringUtils
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.mockito.kotlin.doReturn
import org.mockito.kotlin.mock
import java.time.LocalDate
import kotlin.random.Random

class VisitVisitorsResponseGeneratorTest {
    private val visitVisitorsResponseGenerator = VisitVisitorsResponseGenerator()

    @Test
    fun `if the visitor has one visit period that matches the visit start and end, it is a full visitor`() {
        val visitorId = Random.nextLong()
        val visitorName = RandomStringUtils.secure().nextAlphabetic(8)
        val visit = mockVisitEntity(
            startDate = LocalDate.of(2025, 11, 1),
            endDate = LocalDate.of(2025, 11, 10),
            visitVisitors = listOf(
                mockVisitVisitor(
                    visitorId = visitorId,
                    visitorName = visitorName,
                    visitPeriods = listOf(
                        VisitVisitorPeriodEntity(
                            startDate = LocalDate.of(2025, 11, 1),
                            endDate = LocalDate.of(2025, 11, 10),
                        )
                    )
                )
            )
        )

        val result = visitVisitorsResponseGenerator.generateVisitVisitorsResponse(visit)

        assertThat(result.partTimeVisitors).isEmpty()
        assertThat(result.fullTimeVisitors).containsExactly(
            VisitorResponse(
                id = visitorId,
                name = visitorName,
            )
        )
    }

    @Test
    fun `if the visitor has one visit period that does not match the visit start, it is a partial visitor`() {
        val visitorId = Random.nextLong()
        val visitorName = RandomStringUtils.secure().nextAlphabetic(8)
        val visit = mockVisitEntity(
            startDate = LocalDate.of(2025, 11, 1),
            endDate = LocalDate.of(2025, 11, 10),
            visitVisitors = listOf(
                mockVisitVisitor(
                    visitorId = visitorId,
                    visitorName = visitorName,
                    visitPeriods = listOf(
                        VisitVisitorPeriodEntity(
                            startDate = LocalDate.of(2025, 11, 2),
                            endDate = LocalDate.of(2025, 11, 10),
                        )
                    )
                )
            )
        )

        val result = visitVisitorsResponseGenerator.generateVisitVisitorsResponse(visit)

        assertThat(result.fullTimeVisitors).isEmpty()
        assertThat(result.partTimeVisitors).containsExactly(
            VisitVisitorResponse(
                visitor = VisitorResponse(
                    id = visitorId,
                    name = visitorName,
                ),
                visitPeriods = listOf(
                    VisitPeriodResponse(
                        startDate = LocalDate.of(2025, 11, 2),
                        endDate = LocalDate.of(2025, 11, 10),
                    )
                )
            )
        )
    }

    @Test
    fun `if the visitor has one visit period that does not match the visit end, it is a partial visitor`() {
        val visitorId = Random.nextLong()
        val visitorName = RandomStringUtils.secure().nextAlphabetic(8)
        val visit = mockVisitEntity(
            startDate = LocalDate.of(2025, 11, 1),
            endDate = LocalDate.of(2025, 11, 10),
            visitVisitors = listOf(
                mockVisitVisitor(
                    visitorId = visitorId,
                    visitorName = visitorName,
                    visitPeriods = listOf(
                        VisitVisitorPeriodEntity(
                            startDate = LocalDate.of(2025, 11, 1),
                            endDate = LocalDate.of(2025, 11, 9),
                        )
                    )
                )
            )
        )

        val result = visitVisitorsResponseGenerator.generateVisitVisitorsResponse(visit)

        assertThat(result.fullTimeVisitors).isEmpty()
        assertThat(result.partTimeVisitors).containsExactly(
            VisitVisitorResponse(
                visitor = VisitorResponse(
                    id = visitorId,
                    name = visitorName,
                ),
                visitPeriods = listOf(
                    VisitPeriodResponse(
                        startDate = LocalDate.of(2025, 11, 1),
                        endDate = LocalDate.of(2025, 11, 9),
                    )
                )
            )
        )
    }


    @Test
    fun `if the visitor has two visit periods, it is a partial visitor`() {
        val visitorId = Random.nextLong()
        val visitorName = RandomStringUtils.secure().nextAlphabetic(8)
        val visit = mockVisitEntity(
            startDate = LocalDate.of(2025, 11, 1),
            endDate = LocalDate.of(2025, 11, 10),
            visitVisitors = listOf(
                mockVisitVisitor(
                    visitorId = visitorId,
                    visitorName = visitorName,
                    visitPeriods = listOf(
                        VisitVisitorPeriodEntity(
                            startDate = LocalDate.of(2025, 11, 1),
                            endDate = LocalDate.of(2025, 11, 5),
                        ),
                        VisitVisitorPeriodEntity(
                            startDate = LocalDate.of(2025, 11, 6),
                            endDate = LocalDate.of(2025, 11, 10),
                        ),
                    )
                )
            )
        )

        val result = visitVisitorsResponseGenerator.generateVisitVisitorsResponse(visit)

        assertThat(result.fullTimeVisitors).isEmpty()
        assertThat(result.partTimeVisitors).containsExactly(
            VisitVisitorResponse(
                visitor = VisitorResponse(
                    id = visitorId,
                    name = visitorName,
                ),
                visitPeriods = listOf(
                    VisitPeriodResponse(
                        startDate = LocalDate.of(2025, 11, 1),
                        endDate = LocalDate.of(2025, 11, 5),
                    ),
                    VisitPeriodResponse(
                        startDate = LocalDate.of(2025, 11, 6),
                        endDate = LocalDate.of(2025, 11, 10),
                    ),
                )
            )
        )
    }

    private fun mockVisitEntity(
        startDate: LocalDate,
        endDate: LocalDate,
        visitVisitors: List<VisitVisitorEntity>,
    ): VisitEntity = mock<VisitEntity> {
        on { this.startDate } doReturn startDate
        on { this.endDate } doReturn endDate
        on { this.visitVisitors } doReturn visitVisitors
    }

    private fun mockVisitVisitor(
        visitorId: Long,
        visitorName: String,
        visitPeriods: List<VisitVisitorPeriodEntity>,
    ): VisitVisitorEntity {
        val visitor = mock<VisitorEntity> {
            on { this.id } doReturn visitorId
            on { this.name } doReturn visitorName
        }
        return mock {
            on { this.visitor } doReturn visitor
            on { this.visitPeriods } doReturn visitPeriods
        }
    }
}
