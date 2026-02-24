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
    var cabin: CabinEntity,
) {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0

    @OneToMany(mappedBy = "visit", cascade = [CascadeType.ALL], orphanRemoval = true)
    private val _visitVisitors: MutableList<VisitVisitorEntity> = mutableListOf()

    val visitVisitors: List<VisitVisitorEntity>
        get() = _visitVisitors

    fun addVisitor(visitor: VisitVisitorEntity) {
        _visitVisitors.add(visitor)
    }
}
