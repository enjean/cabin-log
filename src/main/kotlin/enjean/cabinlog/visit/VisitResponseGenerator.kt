package enjean.cabinlog.visit

import enjean.cabinlog.cabin.CabinEntity
import enjean.cabinlog.cabin.CabinResponse
import org.springframework.stereotype.Service

@Service
class VisitResponseGenerator(
    private val visitVisitorsResponseGenerator: VisitVisitorsResponseGenerator,
) {
    fun generateVisitResponse(visit: VisitEntity): VisitResponse {

        return VisitResponse(
            id = visit.id,
            cabin = visit.cabin.toCabinResponse(),
            name = visit.name,
            startDate = visit.startDate,
            endDate = visit.endDate,
            visitors = visitVisitorsResponseGenerator.generateVisitVisitorsResponse(visit),
        )
    }

    private fun CabinEntity.toCabinResponse() =
        CabinResponse(
            id = id,
            name = name,
        )

}
