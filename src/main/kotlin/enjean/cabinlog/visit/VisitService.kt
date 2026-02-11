package enjean.cabinlog.visit

import enjean.cabinlog.cabin.CabinRepository
import enjean.cabinlog.cabin.CabinResponse
import org.springframework.stereotype.Service

@Service
class VisitService(
    private val visitRepository: VisitRepository,
    private val cabinRepository: CabinRepository,
) {
    fun createVisit(request: CreateVisitRequest): VisitResponse {
        val cabin = cabinRepository.getReferenceById(request.cabinId)
        val visit = visitRepository.save(
            VisitEntity(
                name = request.name,
                startDate = request.startDate,
                endDate = request.endDate,
                cabin = cabin,
            )
        )

        return VisitResponse(
            id = visit.id,
            cabin = CabinResponse(
                id = cabin.id,
                name = cabin.name,
            ),
            name = visit.name,
            startDate = visit.startDate,
            endDate = visit.endDate,
        )
    }
}
