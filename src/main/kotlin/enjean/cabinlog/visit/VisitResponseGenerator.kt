package enjean.cabinlog.visit

import enjean.cabinlog.cabin.CabinEntity
import enjean.cabinlog.cabin.CabinResponse
import org.springframework.stereotype.Service

@Service
class VisitResponseGenerator(
    private val visitRepository: VisitRepository,
) {
    fun generateVisitResponse(visitId: Long): VisitResponse {
        val visit = visitRepository.getReferenceById(visitId)

        return VisitResponse(
            id = visit.id,
            cabin = visit.cabin.toCabinResponse(),
            name = visit.name,
            startDate = visit.startDate,
            endDate = visit.endDate,
            visitVisitors = emptyList(), // TODO
        )
    }

    private fun CabinEntity.toCabinResponse() =
        CabinResponse(
            id = id,
            name = name,
        )
}
