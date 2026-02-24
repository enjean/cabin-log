package enjean.cabinlog.visit

import enjean.cabinlog.cabin.CabinEntity
import enjean.cabinlog.cabin.CabinResponse
import enjean.cabinlog.visitor.VisitorEntity
import enjean.cabinlog.visitor.VisitorResponse
import org.springframework.stereotype.Service

@Service
class VisitResponseGenerator {
    fun generateVisitResponse(visit: VisitEntity): VisitResponse {

        return VisitResponse(
            id = visit.id,
            cabin = visit.cabin.toCabinResponse(),
            name = visit.name,
            startDate = visit.startDate,
            endDate = visit.endDate,
            visitVisitors = visit.visitVisitors.map { it.toVisitVisitorResponse() }
        )
    }

    private fun CabinEntity.toCabinResponse() =
        CabinResponse(
            id = id,
            name = name,
        )

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
