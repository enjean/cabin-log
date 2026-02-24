package enjean.cabinlog.visit

import java.time.LocalDate

data class CreateVisitRequest(
    val cabinId: Long,
    val name: String,
    val startDate: LocalDate,
    val endDate: LocalDate,
    val visitors: List<VisitVisitorRequest>,
)

data class VisitVisitorRequest(
    val visitorId: Long,
    val visitPeriods: List<VisitPeriodRequest>,
)

data class VisitPeriodRequest(
    val startDate: LocalDate,
    val endDate: LocalDate,
)
