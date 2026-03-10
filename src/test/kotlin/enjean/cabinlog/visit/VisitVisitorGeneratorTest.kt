package enjean.cabinlog.visit

import enjean.cabinlog.visitor.VisitorEntity
import enjean.cabinlog.visitor.VisitorRepository
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.mockito.kotlin.mock
import org.mockito.kotlin.whenever
import java.time.LocalDate
import kotlin.random.Random

class VisitVisitorGeneratorTest {

    private val mockVisitorRepository = mock<VisitorRepository>()

    private val visitVisitorGenerator = VisitVisitorGenerator(mockVisitorRepository)

    @Test
    fun `for a full visitor, add a VisitVisitorEntity with one visit period equal to the full visit length`() {
        val visitorId = Random.nextLong()
        val visitorsInfo = VisitorsInfo(
            fullTimeVisitorIds = listOf(visitorId),
        )
        val visit = VisitEntity(
            name = "Visit Name",
            startDate = LocalDate.of(2026, 2, 1),
            endDate = LocalDate.of(2026, 2, 6),
            cabin = mock(),
        )

        val visitor = mock<VisitorEntity>()
        whenever(mockVisitorRepository.getReferenceById(visitorId)).thenReturn(visitor)

        val result = visitVisitorGenerator.generateVisitVisitorEntities(visitorsInfo, visit)

        assertThat(result).hasSize(1)
        val resultVisitVisitorEntity = result[0]

        assertThat(resultVisitVisitorEntity.visit).isEqualTo(visit)
        assertThat(resultVisitVisitorEntity.visitor).isEqualTo(visitor)
        assertThat(resultVisitVisitorEntity.visitPeriods).hasSize(1)
        val visitPeriod = resultVisitVisitorEntity.visitPeriods[0]
        assertThat(visitPeriod.startDate).isEqualTo(visit.startDate)
        assertThat(visitPeriod.endDate).isEqualTo(visit.endDate)
    }

    @Test
    fun `for a partial visitor, add all of the requested visit periods`() {
        val visitorId = Random.nextLong()
        val visitorsInfo = VisitorsInfo(
            partialVisitors = listOf(
                VisitVisitorRequest(
                    id = visitorId,
                    visitPeriods = listOf(
                        VisitPeriodRequest(
                            startDate = LocalDate.of(2026, 2, 1),
                            endDate = LocalDate.of(2026, 2, 2),
                        ),
                        VisitPeriodRequest(
                            startDate = LocalDate.of(2026, 2, 4),
                            endDate = LocalDate.of(2026, 2, 5),
                        ),
                    ),
                ),
            ),
        )
        val visit = VisitEntity(
            name = "Visit Name",
            startDate = LocalDate.of(2026, 2, 1),
            endDate = LocalDate.of(2026, 2, 6),
            cabin = mock(),
        )

        val visitor = mock<VisitorEntity>()
        whenever(mockVisitorRepository.getReferenceById(visitorId)).thenReturn(visitor)

        val result = visitVisitorGenerator.generateVisitVisitorEntities(visitorsInfo, visit)

        assertThat(result).hasSize(1)
        val resultVisitVisitorEntity = result[0]

        assertThat(resultVisitVisitorEntity.visit).isEqualTo(visit)
        assertThat(resultVisitVisitorEntity.visitor).isEqualTo(visitor)
        assertThat(resultVisitVisitorEntity.visitPeriods).hasSize(2)
        assertThat(resultVisitVisitorEntity.visitPeriods).anySatisfy {
            assertThat(it.startDate).isEqualTo(LocalDate.of(2026, 2, 1))
            assertThat(it.endDate).isEqualTo(LocalDate.of(2026, 2, 2))
        }
        assertThat(resultVisitVisitorEntity.visitPeriods).anySatisfy {
            assertThat(it.startDate).isEqualTo(LocalDate.of(2026, 2, 4))
            assertThat(it.endDate).isEqualTo(LocalDate.of(2026, 2, 5))
        }
    }
}
