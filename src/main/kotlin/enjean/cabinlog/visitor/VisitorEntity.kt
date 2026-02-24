package enjean.cabinlog.visitor

import enjean.cabinlog.cabin.CabinEntity
import jakarta.persistence.*

@Entity
@Table(name = "visitors")
class VisitorEntity(
    var name: String,
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "cabin_id")
    var cabin: CabinEntity
) {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0
}
