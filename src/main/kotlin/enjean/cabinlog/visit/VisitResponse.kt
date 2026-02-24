package enjean.cabinlog.visit

import enjean.cabinlog.cabin.CabinResponse
import enjean.cabinlog.visitor.VisitorResponse
import java.time.LocalDate

data class VisitResponse(
    val id: Long,
    val cabin: CabinResponse,
    val name: String,
    val startDate: LocalDate,
    val endDate: LocalDate,
    val visitVisitors: List<VisitVisitorResponse>,
)

data class VisitVisitorResponse(
    val visitor: VisitorResponse,
    val visitPeriods: List<VisitPeriodResponse>,
)

data class VisitPeriodResponse(
    val startDate: LocalDate,
    val endDate: LocalDate,
)
