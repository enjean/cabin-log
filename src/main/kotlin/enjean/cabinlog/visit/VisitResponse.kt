package enjean.cabinlog.visit

import enjean.cabinlog.cabin.CabinResponse
import java.time.LocalDate

data class VisitResponse(
    val id: Long,
    val cabin: CabinResponse,
    val name: String,
    val startDate: LocalDate,
    val endDate: LocalDate,
)
