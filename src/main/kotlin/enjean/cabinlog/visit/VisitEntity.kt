package enjean.cabinlog.visit

import enjean.cabinlog.cabin.CabinEntity
import jakarta.persistence.*
import java.time.LocalDate

@Entity
@Table(name = "visits")
class VisitEntity(
    var name: String,
    var startDate: LocalDate,
    var endDate: LocalDate,
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "cabin_id")
    var cabin: CabinEntity
) {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0
}
