package enjean.cabinlog.visit

import java.time.LocalDate

data class CreateVisitRequest(
    val cabinId: Long,
    val name: String,
    val startDate: LocalDate,
    val endDate: LocalDate,
)
