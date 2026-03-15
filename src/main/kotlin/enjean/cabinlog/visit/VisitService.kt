package enjean.cabinlog.visit

import enjean.cabinlog.cabin.CabinRepository
import io.github.oshai.kotlinlogging.KotlinLogging
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service

private val logger = KotlinLogging.logger {}

@Service
class VisitService(
    private val visitRepository: VisitRepository,
    private val cabinRepository: CabinRepository,
    private val visitVisitorGenerator: VisitVisitorGenerator,
    private val visitResponseGenerator: VisitResponseGenerator,
) {
    fun createVisit(cabinId: Long, request: CreateVisitRequest): VisitResponse {
        val cabin = cabinRepository.getReferenceById(cabinId)
        val visit = VisitEntity(
            cabin = cabin,
            name = request.name,
            startDate = request.startDate,
            endDate = request.endDate,
        )

        val visitVisitors = visitVisitorGenerator.generateVisitVisitorEntities(
            visitorsInfo = request.visitors,
            visit = visit,
        )
        visitVisitors.forEach { visit.addVisitor(it) }

        val savedVisit = visitRepository.save(visit)

        logger.debug { "Saved visit $savedVisit"}
        return visitResponseGenerator.generateVisitResponse(savedVisit)
    }

    fun getVisits(cabinId: Long, year: Int?): VisitSummariesResponse {
        val visits = visitRepository.findByCabinIdAndYear(cabinId = cabinId, year = year)
        val visitSummaries = visits.map { visitEntity ->
            VisitSummaryResponse(
                id = visitEntity.id,
                name = visitEntity.name,
                startDate = visitEntity.startDate,
                endDate = visitEntity.endDate,
                visitors = visitEntity.visitVisitors.map { visitVisitorEntity ->
                    val visitor = visitVisitorEntity.visitor
                    VisitSummaryVisitorResponse(id = visitor.id, name = visitor.name)
                }.sortedBy { it.name }
            )
        }
        return VisitSummariesResponse(visitSummaries)
    }

    fun getVisit(id: Long): VisitResponse {
        val visit = visitRepository.findByIdOrNull(id) ?:
            throw IllegalStateException("Visit with id $id not found") // Later could add more sophisticated error handling to make sure this returns 404

        return visitResponseGenerator.generateVisitResponse(visit)
    }
}
