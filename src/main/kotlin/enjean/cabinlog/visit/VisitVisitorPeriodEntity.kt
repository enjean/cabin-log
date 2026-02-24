package enjean.cabinlog.visit

import jakarta.persistence.*
import java.time.LocalDate

@Entity
@Table(name = "visit_visitor_periods")
class VisitVisitorPeriodEntity(
    var startDate: LocalDate,
    var endDate: LocalDate,
) {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0
}
