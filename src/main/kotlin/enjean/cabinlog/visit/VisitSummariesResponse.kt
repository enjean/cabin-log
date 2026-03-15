package enjean.cabinlog.visit

import java.time.LocalDate

data class VisitSummariesResponse(val visitSummaries: List<VisitSummaryResponse>)

data class VisitSummaryResponse(
    val id: Long,
    val name: String,
    val startDate: LocalDate,
    val endDate: LocalDate,
    val visitors: List<VisitSummaryVisitorResponse>,
)

data class VisitSummaryVisitorResponse(
    val id: Long,
    val name: String,
)
