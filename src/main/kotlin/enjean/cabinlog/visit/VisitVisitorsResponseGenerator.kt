package enjean.cabinlog.visit

import enjean.cabinlog.visitor.VisitorEntity
import enjean.cabinlog.visitor.VisitorResponse
import org.springframework.stereotype.Service

@Service
class VisitVisitorsResponseGenerator {
     fun generateVisitVisitorsResponse(visit: VisitEntity): VisitVisitorsResponse {
        val fullVsPartial = visit.visitVisitors.partition { it.isFullVisitVisitor(visit)}
        val fullTimeVisitors = fullVsPartial.first.map { it.visitor.toVisitorResponse() }
        val partTimeVisitors = fullVsPartial.second.map { it.toVisitVisitorResponse() }
        return VisitVisitorsResponse(
            fullTimeVisitors = fullTimeVisitors,
            partTimeVisitors = partTimeVisitors,
        )
    }

    private fun VisitVisitorEntity.isFullVisitVisitor(visit: VisitEntity) : Boolean {
        if (visitPeriods.size != 1) {
            return false
        }
        val visitPeriod = visitPeriods.first()
        return visitPeriod.startDate == visit.startDate && visitPeriod.endDate == visit.endDate
    }

    private fun VisitVisitorEntity.toVisitVisitorResponse() =
        VisitVisitorResponse(
            visitor = visitor.toVisitorResponse(),
            visitPeriods = visitPeriods.map { it.toVisitPeriodResponse() }
        )

    private fun VisitorEntity.toVisitorResponse() =
        VisitorResponse(
            id = id,
            name = name,
        )

    private fun VisitVisitorPeriodEntity.toVisitPeriodResponse() =
        VisitPeriodResponse(
            startDate = startDate,
            endDate = endDate,
        )
}
