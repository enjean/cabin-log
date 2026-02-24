package enjean.cabinlog.visit

import enjean.cabinlog.cabin.CabinRepository
import enjean.cabinlog.visitor.VisitorRepository
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service

@Service
class VisitService(
    private val visitRepository: VisitRepository,
    private val cabinRepository: CabinRepository,
    private val visitorRepository: VisitorRepository,
    private val visitResponseGenerator: VisitResponseGenerator,
) {
    fun createVisit(request: CreateVisitRequest): VisitResponse {
        val cabin = cabinRepository.findByIdOrNull(request.cabinId)!!
        val visit = VisitEntity(
            cabin = cabin,
            name = request.name,
            startDate = request.startDate,
            endDate = request.endDate,
        )

        request.visitors.forEach { visitVisitorRequest ->
            val visitor = visitorRepository.getReferenceById(visitVisitorRequest.visitorId)
            val visitVisitorEntity = VisitVisitorEntity(
                visitor = visitor,
                visit = visit,
            )

            visitVisitorRequest.visitPeriods.forEach { visitPeriod ->
                visitVisitorEntity.addVisitPeriod(
                    VisitVisitorPeriodEntity(
                        startDate = visitPeriod.startDate,
                        endDate = visitPeriod.endDate,
                    )
                )
            }

            visit.addVisitor(visitVisitorEntity)
        }

        val savedVisit = visitRepository.save(visit)

        return visitResponseGenerator.generateVisitResponse(savedVisit)
    }
}
