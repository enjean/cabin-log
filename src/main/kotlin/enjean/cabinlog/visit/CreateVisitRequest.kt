package enjean.cabinlog.visit

import java.time.LocalDate

data class CreateVisitRequest(
    val name: String,
    val startDate: LocalDate,
    val endDate: LocalDate,
    val visitors: VisitorsInfo,
)

data class VisitorsInfo(
    val fullTimeVisitorIds: List<Long> = emptyList(),
    val partialVisitors: List<VisitVisitorRequest> = emptyList(),
)

data class VisitVisitorRequest(
    val id: Long,
    val visitPeriods: List<VisitPeriodRequest>,
)

data class VisitPeriodRequest(
    val startDate: LocalDate,
    val endDate: LocalDate,
)
