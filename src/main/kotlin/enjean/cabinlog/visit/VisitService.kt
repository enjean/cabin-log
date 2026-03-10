package enjean.cabinlog.visit

import enjean.cabinlog.cabin.CabinRepository
import io.github.oshai.kotlinlogging.KotlinLogging
import org.springframework.stereotype.Service

private val logger = KotlinLogging.logger {}

@Service
class VisitService(
    private val visitRepository: VisitRepository,
    private val cabinRepository: CabinRepository,
    private val visitVisitorGenerator: VisitVisitorGenerator,
    private val visitResponseGenerator: VisitResponseGenerator,
) {
    fun createVisit(request: CreateVisitRequest): VisitResponse {
        val cabin = cabinRepository.getReferenceById(request.cabinId)
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

        logger.info { "Saving visit $visit"}
        val savedVisit = visitRepository.save(visit)

        logger.info { "Saving visits ${savedVisit.visitVisitors}"}
        return visitResponseGenerator.generateVisitResponse(savedVisit)
    }
}
