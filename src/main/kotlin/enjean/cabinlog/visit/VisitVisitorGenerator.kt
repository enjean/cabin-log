package enjean.cabinlog.visit

import enjean.cabinlog.visitor.VisitorRepository
import org.springframework.stereotype.Component

@Component
class VisitVisitorGenerator(
    private val visitorRepository: VisitorRepository,
) {

    fun generateVisitVisitorEntities(visitorsInfo: VisitorsInfo, visit: VisitEntity): List<VisitVisitorEntity> {
        val fullVisitVisitors = visitorsInfo.fullTimeVisitorIds.map { id ->
            generateVisitVisitorEntity(
                visitorId = id,
                visit = visit,
                visitPeriods = listOf(VisitPeriodRequest(startDate = visit.startDate, endDate = visit.endDate)),
            )
        }

        val partialVisitVisitors = visitorsInfo.partialVisitors.map { visitVisitorRequest ->
            generateVisitVisitorEntity(
                visitorId = visitVisitorRequest.id,
                visit = visit,
                visitPeriods = visitVisitorRequest.visitPeriods,
            )
        }

        return fullVisitVisitors + partialVisitVisitors
    }

    private fun generateVisitVisitorEntity(visitorId: Long, visit: VisitEntity, visitPeriods: List<VisitPeriodRequest>) : VisitVisitorEntity {
        val visitor = visitorRepository.getReferenceById(visitorId)
        val visitVisitorEntity = VisitVisitorEntity(
            visitor = visitor,
            visit = visit,
        )
        visitPeriods.forEach { visitPeriod ->
            visitVisitorEntity.addVisitPeriod(
                VisitVisitorPeriodEntity(
                    startDate = visitPeriod.startDate,
                    endDate = visitPeriod.endDate,
                )
            )
        }
        return visitVisitorEntity
    }
}
