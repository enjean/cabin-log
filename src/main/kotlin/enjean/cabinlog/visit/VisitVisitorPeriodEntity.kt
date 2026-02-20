package enjean.cabinlog.visit

import jakarta.persistence.*
import java.time.LocalDate

@Entity
@Table(name = "visit_visitor_periods")
class VisitVisitorPeriodEntity(
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "visit_visitor_id")
    var visitVisitorEntity: VisitVisitorEntity,
    var startDate: LocalDate,
    var endDate: LocalDate,
) {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0
}
