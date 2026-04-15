package enjean.cabinlog.visitor

import enjean.cabinlog.cabin.CabinRepository
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Service

@Service
class VisitorService(
    private val visitorRepository: VisitorRepository,
    private val cabinRepository: CabinRepository,
) {
    fun createVisitor(cabinId: Long, createVisitorRequest: CreateVisitorRequest): VisitorResponse {
        val visitor = visitorRepository.save(
            VisitorEntity(
                name = createVisitorRequest.name,
                cabin = cabinRepository.getReferenceById(cabinId),
            )
        )
        return VisitorResponse(
            id = visitor.id,
            name = visitor.name,
        )
    }

    fun getVisitors(cabinId: Long): List<VisitorResponse> {
        val visitorEntities = visitorRepository.findAllByCabinId(cabinId)
        return visitorEntities.map { v ->
            VisitorResponse(
                id = v.id,
                name = v.name,
            )
        }
    }
}
