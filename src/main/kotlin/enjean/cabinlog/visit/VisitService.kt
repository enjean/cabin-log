package enjean.cabinlog.visit

import enjean.cabinlog.cabin.CabinRepository
import enjean.cabinlog.cabin.CabinResponse
import enjean.cabinlog.visitor.VisitorRepository
import enjean.cabinlog.visit.VisitVisitorEntity
import enjean.cabinlog.visit.VisitVisitorRepository
import org.springframework.stereotype.Service

@Service
class VisitService(
    private val visitRepository: VisitRepository,
    private val cabinRepository: CabinRepository,
    private val visitorRepository: VisitorRepository,
    private val visitVisitorRepository: VisitVisitorRepository,
    private val visitVisitorPeriodRepository: VisitVisitorPeriodRepository,
    private val visitResponseGenerator: VisitResponseGenerator,
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

        request.visitors.forEach { visitVisitorRequest ->
            val visitor = visitorRepository.getReferenceById(visitVisitorRequest.visitorId)
            val visitVisitorEntity = visitVisitorRepository.save(
                VisitVisitorEntity(
                    visitor = visitor,
                    visit = visit,
                )
            )
            visitVisitorRequest.visitPeriods.forEach { visitPeriod ->
                visitVisitorPeriodRepository.save(
                    VisitVisitorPeriodEntity(
                        visitVisitorEntity = visitVisitorEntity,
                        startDate = visitPeriod.startDate,
                        endDate = visitPeriod.endDate,
                    )
                )
            }
        }

       return visitResponseGenerator.generateVisitResponse(visit.id)
    }
}
